package net.baguajie.web.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import net.baguajie.constants.ApplicationConstants;
import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.ByType;
import net.baguajie.domains.CityMeta;
import net.baguajie.domains.User;
import net.baguajie.domains.UserPreference;

@Component
public class SessionUtil {
	
	public String getLastVisitedUrl(HttpSession session){
		String lastVisitedUrl = (String) session
				.getAttribute(ApplicationConstants.SESSION_LAST_VISITED_URL);
		return lastVisitedUrl != null ? lastVisitedUrl : "/";
	}
	
	public User getSignInUser(HttpSession session){
		return (User)session.getAttribute(
				ApplicationConstants.SESSION_SIGNIN_USER);
	}
	
	public UserPreference getSignInUserPrefer(HttpSession session){
		return (UserPreference)session.getAttribute(
				ApplicationConstants.SESSION_SIGNIN_USER_PREFER);
	}
	
	public void setSignInUserPrefer(UserPreference up, HttpSession session){
		if(up==null){
			up = getDefaultPrefer();
		}else{
			enrichUserPrefer(up);
		}
		session.setAttribute(ApplicationConstants.SESSION_SIGNIN_USER_PREFER, 
				up);
	}
	
	private void enrichUserPrefer(UserPreference up){
		if(up==null) return;
		if(up.getCategories()==null){
			up.setCategories(new ArrayList<String>());
		}
		if(up.getCategories().isEmpty()){
			up.getCategories().addAll(
				Arrays.asList(ApplicationConfig.defaultCategories));
		}
	}
	
	public UserPreference getDefaultPrefer(){
		UserPreference up = new UserPreference();
		up.setId(ApplicationConfig.defaultPreferenceId);
		List<String> categories = new ArrayList<String>();
		Collections.addAll(categories, ApplicationConfig.defaultCategories);
		up.setCategories(categories);
		return up;
	}
	
	public CityMeta getGeoCityMeta(HttpSession session){
		return (CityMeta) session.getAttribute(
				ApplicationConstants.SESSION_SELECTED_CITY_META);
	}
	
	public ByType getBy(HttpSession session){
		return (ByType) session.getAttribute(
				ApplicationConstants.SESSION_BY);
	}
	
}
