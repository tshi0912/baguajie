package net.baguajie.web.mvc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConstants;
import net.baguajie.web.utils.AjaxUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LastVisitedUrlInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	AjaxUtil ajaxUtil;

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object o, ModelAndView modelAndView) throws Exception {
		if (!ajaxUtil.isAjaxRequest(req)
				&& RequestMethod.GET.name().equals(req.getMethod())) {
			HttpSession session = req.getSession();
			String referer = StringUtils.trimWhitespace(req
					.getHeader("referer"));
			if (StringUtils.hasText(referer) && !referer.endsWith(".js")
					&& !referer.endsWith(".css")) {
				session.setAttribute(
						ApplicationConstants.SESSION_LAST_VISITED_URL,
						req.getHeader("referer"));
			}
		}
	}
}
