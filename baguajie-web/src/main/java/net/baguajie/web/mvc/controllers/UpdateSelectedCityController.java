package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpSession;

import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.domains.CityMeta;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.vo.AjaxResult;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UpdateSelectedCityController {

	@Autowired
	private CityMetaRepository cityMetaRepository;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/geocity/{city}", method=RequestMethod.GET)	
	public @ResponseBody AjaxResult update(@PathVariable String city, 
			HttpSession session){
		CityMeta cityMeta = cityMetaRepository.getByPinyin(city);
		if(cityMeta !=null){
			session.setAttribute(ApplicationConstants.SESSION_SELECTED_CITY_META, 
				cityMeta);
		}else{
			cityMeta = sessionUtil.getGeoCityMeta(session);
		}
		return new AjaxResult(AjaxResultCode.SUCCESS, cityMeta);
	}
}
