package net.baguajie.web.mvc.interceptors;

import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.web.utils.AjaxUtil;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SignInTsInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SessionUtil sessionUtil;
	@Autowired
	private AjaxUtil ajaxUtil;
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		    throws Exception {
		HttpSession session = request.getSession(true);
		if(!ajaxUtil.isAjaxRequest(request) &&
				sessionUtil.getSignInUser(session)!=null){
			Calendar c = Calendar.getInstance();
			c.setTimeZone(TimeZone.getTimeZone("UTC"));
			request.setAttribute("signin_ts", c.getTimeInMillis()+"");
		}
		return true;
	}
	
}
