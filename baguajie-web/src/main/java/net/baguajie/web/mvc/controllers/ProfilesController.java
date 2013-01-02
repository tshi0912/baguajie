package net.baguajie.web.mvc.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.constants.Gender;
import net.baguajie.domains.CityMeta;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.vo.FilterElementVo;
import net.baguajie.web.utils.DomainObjectUtil;
import net.baguajie.web.utils.SessionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ ApplicationConstants.SESSION_SIGNIN_USER,
		ApplicationConstants.SESSION_SIGNIN_USER_PREFER })
public class ProfilesController {
	private static Logger logger = LoggerFactory
			.getLogger(ProfilesController.class);
	@Autowired
	private CityMetaRepository cityMetaRepository;
	@Autowired
	private SessionUtil sessionUtil;

	@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	public String profiles(Model model, HttpServletRequest request,
			HttpSession session) {
		CityMeta city = sessionUtil.getGeoCityMeta(session);
		String pinyin = ApplicationConfig.defaultCityPinyin;
		if (city != null) {
			pinyin = city.getPinyin();
		}
		return toProfiles(pinyin, model, request, session);
	}

	private String toProfiles(String cityPinyin, Model model,
			HttpServletRequest request, HttpSession session) {
		String city = request.getParameter("city");
		if (!StringUtils.hasText(city)) {
			city = cityPinyin;
		}
		String gender = null;
		try {
			if (request.getParameter("gender") != null) {
				gender = new String(request.getParameter("gender").getBytes(
						"ISO-8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage(), e);
		}
		String keyword = null;
		try {
			if (request.getParameter("keyword") != null) {
				keyword = new String(request.getParameter("keyword").getBytes(
						"ISO-8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage(), e);
		}
		Collection<FilterElementVo> filters = new ArrayList<FilterElementVo>();
		StringBuilder sb = new StringBuilder();
		FilterElementVo filter = null;
		// add city filter
		filter = new FilterElementVo();
		filter.setType("city");
		filter.setTypeLabel("城市");
		filter.setValue("");
		filter.setLabel("全国");
		if (StringUtils.hasText(city)) {
			CityMeta cityMeta = cityMetaRepository.getByPinyin(city
					.toLowerCase());
			if (cityMeta == null) {
				cityMeta = cityMetaRepository
						.getByPinyin(ApplicationConfig.defaultCityPinyin);
			}
			if (cityMeta != null) {
				filter.setLabel(cityMeta.getName());
				filter.setValue(cityMeta.getPinyin());
			}
		}
		filters.add(filter);
		sb.append(filter.getType()).append("=").append(filter.getValue())
				.append("&");

		// add gender filter
		filter = new FilterElementVo();
		filter.setType("gender");
		filter.setTypeLabel("性别");
		filter.setValue("");
		filter.setLabel("无所谓");
		if (StringUtils.hasText(gender)) {
			try {
				Gender g = Gender.valueOf(gender.toUpperCase());
				filter.setLabel(DomainObjectUtil.getGender(g));
				filter.setValue(gender);
			} catch (RuntimeException re) {

			}
		}
		filters.add(filter);
		sb.append(filter.getType()).append("=").append(filter.getValue())
				.append("&");

		// add keyword filter
		filter = new FilterElementVo();
		filter.setType("keyword");
		filter.setTypeLabel("关键词");
		filter.setValue("");
		filter.setLabel("未选择");
		if (StringUtils.hasText(keyword)) {
			filter.setLabel(keyword);
			filter.setValue(keyword);
		}
		filters.add(filter);
		sb.append(filter.getType()).append("=").append(filter.getValue())
				.append("&");

		model.addAttribute("filters", filters);
		model.addAttribute("qStr", sb.toString());
		return "profiles";
	}
}
