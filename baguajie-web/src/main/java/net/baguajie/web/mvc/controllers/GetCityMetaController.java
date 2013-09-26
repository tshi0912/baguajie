package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.CityMeta;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.vo.AjaxResult;
import net.baguajie.web.utils.AjaxUtil;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GetCityMetaController {
	
	@Autowired
	CityMetaRepository cityMetaRepository;
	@Autowired
	AjaxUtil ajaxUtil;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/citymeta", method=RequestMethod.GET)
	public @ResponseBody AjaxResult meta(HttpServletRequest request, 
			ModelAndView mav, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		CityMeta city = sessionUtil.getGeoCityMeta(session);
		return city != null ? new AjaxResult(AjaxResultCode.SUCCESS, city) : 
			meta(null, request, mav, session);
	}
	
	@RequestMapping(value="/citymeta/{pinyin}", method=RequestMethod.GET)
	public @ResponseBody AjaxResult meta(@PathVariable String pinyin,
			HttpServletRequest request, ModelAndView mav, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		String py = pinyin;
		if(!StringUtils.hasText(pinyin)){
			py = ApplicationConfig.defaultCityPinyin;
		}
		CityMeta cityMeta = cityMetaRepository.getByPinyin(py);
		if(cityMeta == null){
			cityMeta = cityMetaRepository
					.getByPinyin(ApplicationConfig.defaultCityPinyin);
			if(cityMeta.getLngLat()==null || cityMeta.getLngLat().length==0)
			{
				Double[] lngLat = new Double[]{118.270087125, 33.504408779};
				cityMeta.setLngLat(lngLat);
			}
		}
		return new AjaxResult(AjaxResultCode.SUCCESS, cityMeta);
	}
	
}
