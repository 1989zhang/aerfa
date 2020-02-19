package com.zhangysh.accumulate.ui.plugins.shiro;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.common.util.IpUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysLoginDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.ui.sys.util.ServletUtil;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author zhangysh
 * @date 2020年02月17日
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger= LoggerFactory.getLogger(UserRealm.class);

    @Resource
    private ILoginService loginService;

    /**
     * 资源角色授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //给资源进行授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        String backToken=ShiroUtils.getToken();
        String sessionInfoStr=loginService.getSessionByToken(backToken);
        TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
        String roleListObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_ROLE)+"";
        List<JSONObject> roleList=JSON.parseObject(roleListObjectJson, List.class);
        String resourceListObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_RESOURCE)+"";
        List<JSONObject> resourceList=JSON.parseObject(resourceListObjectJson, List.class);

        // 角色加入AuthorizationInfo认证对象
        Set<String> roleCode=new HashSet<String>();
        for (JSONObject roleJson:roleList){
            AefsysRole role=JSONObject.toJavaObject(roleJson,AefsysRole.class);
            if(SysDefineConstant.DB_USEABLE_STATUS_VALID.equals(role.getStatus()) && StringUtil.isNotEmpty(role.getRoleCode())) {
                roleCode.add(role.getRoleCode());
            }
        }
        info.setRoles(roleCode);

        // 权限加入AuthorizationInfo认证对象
        Set<String> stringPermissions=new HashSet<String>();
        for(JSONObject resourceJson:resourceList){
            AefsysResource resource=JSONObject.toJavaObject(resourceJson,AefsysResource.class);
            if(SysDefineConstant.DB_USEABLE_STATUS_VALID.equals(resource.getStatus())&& StringUtil.isNotEmpty(resource.getIdentify())){
                stringPermissions.add(resource.getIdentify());
            }
        }

        info.setStringPermissions(stringPermissions);
        return info;
    }

    /**
     * 登录认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePassword = (UsernamePasswordToken) authenticationToken;
        String username = usernamePassword.getUsername();
        String password = "";
        if (usernamePassword.getPassword() != null) {
            password = new String(usernamePassword.getPassword());
        }
        AefsysLoginDto loginDto=new AefsysLoginDto();
        loginDto.setAccount(username);
        loginDto.setPassword(password);
        loginDto.setClientIp(ServletUtil.getClientIpAddr());
        loginDto.setServerIp(IpUtil.getHostIp());
        loginDto.setBrowserType(ServletUtil.getClientBrowser());
        loginDto.setOsType(ServletUtil.getClientOperatingSystem());

        String loginInfoResultStr=loginService.checkLoginInfo(loginDto);
        JSONObject loginInfoJson=JSON.parseObject(loginInfoResultStr);
        if(!MarkConstant.MARK_RESULT_VO_SUCESS.equals(loginInfoJson.getInteger(MarkConstant.MARK_RESULT_VO_CODE))) {
            logger.info("对用户[" + username + "]进行登录验证.验证未通过.");
            throw new AuthenticationException("用户登录验证未通过");
        }
        //验证通过返回后台系统生成的token，前台系统并不保持用户对象，只保存其token
        String backToken=loginInfoJson.getString(MarkConstant.MARK_RESULT_VO_DATA);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(backToken, password, getName());
        return info;
    }
}
