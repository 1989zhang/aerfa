package com.zhangysh.accumulate.ui.config;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Controller异常处理器，现有UnauthorizedException未授权异常
 *
 * @author zhangysh
 * @date 2020年02月18日
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger= LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private static final Integer UNAUTHORIZED_ERROR_STATUS = 403;

    @ExceptionHandler(value = UnauthorizedException.class)
    public void unauthorizedErrorHandler(HttpServletRequest request, HttpServletResponse response, UnauthorizedException ex) throws IOException {
        logger.info("有非法请求被拦截：{}",request.getRequestURI());
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {// ajax请求
            response.setContentType("application/json;charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            String outStr= JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_OPERATE_ERROR.fillArgs("无权限")));
            printWriter.println(outStr);
        } else { //跳转到403请求reqeust请求
            response.sendRedirect("/"+UNAUTHORIZED_ERROR_STATUS);
        }
    }
}
