package net.baguajie.web.mvc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConstants;
import net.baguajie.constants.UserStatus;
import net.baguajie.domains.User;
import net.baguajie.domains.UserPreference;
import net.baguajie.repositories.UserPreferenceRepository;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PreferenceInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	UserPreferenceRepository userPreferenceRepository;
	@Autowired
	private SessionUtil sessionUtil;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);
		User signInUser = sessionUtil.getSignInUser(session);
		UserPreference up = sessionUtil.getSignInUserPrefer(session);
		if (signInUser != null && signInUser.getStatus() == UserStatus.INVALID) {
			session.removeAttribute(ApplicationConstants.SESSION_SIGNIN_USER);
		} else {
			if (signInUser != null && up == null) {
				up = userPreferenceRepository.getByUser(signInUser);
				sessionUtil.setSignInUserPrefer(up, session);
			} else if (signInUser == null && up == null) {
				up = sessionUtil.getDefaultPrefer();
				sessionUtil.setSignInUserPrefer(up, session);
			} else {
				;// nothing
			}
		}
		return true;
	}
}
