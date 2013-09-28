package net.baguajie.web.mvc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.domains.CityMeta;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.web.utils.SessionUtil;

public class GeoCityInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	CityMetaRepository cityMetaRepository;
	@Autowired
	private SessionUtil sessionUtil;
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		    throws Exception {
		HttpSession session = request.getSession();
		
		CityMeta cityMeta = sessionUtil.getGeoCityMeta(session);
		if(cityMeta == null){
			String city = request.getHeader(ApplicationConstants.HEADER_CITY);
			if(city == null){
				city = ApplicationConfig.defaultCityPinyin;
			}
			cityMeta = cityMetaRepository.getByPinyin(city);
			if(cityMeta != null && cityMeta.getPinyin().equals(ApplicationConfig.defaultCityPinyin) &&
					(cityMeta.getLngLat()==null || cityMeta.getLngLat().length==0)){
				Double[] lngLat = new Double[]{118.270087125, 33.504408779};
				cityMeta.setLngLat(lngLat);
			}
			session.setAttribute(ApplicationConstants.SESSION_SELECTED_CITY_META, 
					cityMeta);
		}
		return true;
	}
	
}
