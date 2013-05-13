package com.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.rif.util.SessionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-4-14
 * Time: 下午4:38
 * desc:
 */
public class AuthorityInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 1358600090729208361L;
    private static Logger log = LoggerFactory.getLogger(AuthorityInterceptor.class);

    //拦截Action处理的拦截方法
    public String intercept(ActionInvocation invocation) throws Exception {
        // 取得请求相关的ActionContext实例
        String namespace = invocation.getProxy().getNamespace();
        String actionname = invocation.getProxy().getActionName();
        ActionContext ctx = invocation.getInvocationContext();

        Map session = ctx.getSession();
//        UserInfoVo userInfo = WebUtils.getSessionContainer(request).getUserInfo();
        SessionContainer sessionContainer = ((SessionContainer) session.get("SessionContainer"));

        if (sessionContainer != null && sessionContainer.getUserInfo() != null) {
            return invocation.invoke();
        } else {
            log.warn("登陆超时或未登录");

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            if (request.getHeader("x-requested-with") != null
                    && (request.getHeader("x-requested-with").equalsIgnoreCase(
                    "XMLHttpRequest") || request.getHeader("x-requested-with").equalsIgnoreCase(    //ajax超时处理
                    "Ext.basex"))) {
                response.setHeader("sessionstatus", "timeout");
                return null;
            } else {//http超时的处理
                return Action.LOGIN;
            }

        }
    }
}
