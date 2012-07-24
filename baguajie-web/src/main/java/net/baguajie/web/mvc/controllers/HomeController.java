package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.CityMeta;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.vo.FilterElementVo;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@Autowired
	private CityMetaRepository cityMetaRepository;
	@Autowired
	private SessionUtil sessionUtil;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, 
			HttpServletRequest request, HttpSession session) {
		CityMeta city = sessionUtil.getGeoCityMeta(session);
		String pinyin = ApplicationConfig.defaultCityPinyin;
		if(city!= null){
			pinyin = city.getPinyin();
		}
		return toHome(pinyin, model, request, session);
	}
	
	private String toHome(String cityPinyin, Model model, 
			HttpServletRequest request,HttpSession session) {
		String city = request.getParameter("city");
		if(!StringUtils.hasText(city)){
			city = cityPinyin;
		}
		String category = request.getParameter("category");
		String keyword = request.getParameter("keyword");
		Collection<FilterElementVo> filters = new ArrayList<FilterElementVo>();
		StringBuilder sb = new StringBuilder();
		FilterElementVo filter = null;
		
		// set city filter
		filter = new FilterElementVo();
		filter.setType("city");
		filter.setTypeLabel("城市");
		filter.setValue("");
		filter.setLabel("全国");
		if(StringUtils.hasText(city)){
			CityMeta cityMeta = cityMetaRepository
				.getByPinyin(city.toLowerCase());
			if(cityMeta==null){
				cityMeta = cityMetaRepository.getByPinyin(
					ApplicationConfig.defaultCityPinyin);
			}
			if(cityMeta!=null){
				filter.setLabel(cityMeta.getName());
				filter.setValue(cityMeta.getPinyin());
			}
		}
		filters.add(filter);
		sb.append("city=").append(filter.getValue()).append("&");
		
		// set category filter
		filter = new FilterElementVo();
		filter.setType("category");
		filter.setTypeLabel("分类");
		filter.setValue("");
		filter.setLabel("全部");
		if(StringUtils.hasText(category)){
			filter.setLabel(category);
			filter.setValue(category);
		}
		filters.add(filter);
		sb.append("category=").append(filter.getValue()).append("&");
		
		// set keyword filter
		filter = new FilterElementVo();
		filter.setType("keyword");
		filter.setTypeLabel("关键词");
		filter.setValue("");
		filter.setLabel("未选择");
		if(StringUtils.hasText(keyword)){
			filter.setLabel(keyword);
			filter.setValue(keyword);
		}
		filters.add(filter);
		sb.append("keyword=").append(filter.getValue()).append("&");
		
		model.addAttribute("filters", filters);
		model.addAttribute("qStr", sb.toString());
		return "home";
	}

}
