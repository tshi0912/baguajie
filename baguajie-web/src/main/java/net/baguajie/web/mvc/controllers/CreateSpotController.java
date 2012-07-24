package net.baguajie.web.mvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Activity;
import net.baguajie.domains.CityMeta;
import net.baguajie.domains.Place;
import net.baguajie.domains.Resource;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.repositories.PlaceRepository;
import net.baguajie.repositories.ResourceRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.services.ImageService;
import net.baguajie.vo.AjaxResult;
import net.baguajie.vo.BindingErrors;
import net.baguajie.vo.ImageReadyVo;
import net.baguajie.vo.SpotCreationVo;
import net.baguajie.web.utils.SessionUtil;
import net.baguajie.web.utils.WebImageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/spots")
public class CreateSpotController {
	
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	CityMetaRepository cityMetaRepository;
	@Autowired
	PlaceRepository placeRepository;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ImageService imageService;
	@Autowired
	WebImageUtil webImageUtil;
	@Autowired
	SessionUtil sessionUtil;
	
	@ModelAttribute("spotCreationVo")
	public SpotCreationVo creatSpotCreationVo() {
		SpotCreationVo vo = new SpotCreationVo();
		return vo;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String create(Model model, HttpSession session){
		if (sessionUtil.getSignInUser(session) == null) {
			return "redirect:/signin";
		}
		return "spots/create";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody AjaxResult create(@Valid SpotCreationVo vo, 
			BindingResult result, ModelAndView mav, HttpSession session){
		User signInUser = sessionUtil.getSignInUser(session);
		if(signInUser==null){
			return new AjaxResult(AjaxResultCode.NEED_SIGNIN);
		}
		if(result.hasErrors()){
			return new AjaxResult(AjaxResultCode.INVALID, 
					BindingErrors.from(result));
		}
		// save spot
		Spot spot = Spot.from(vo, signInUser);
		try {
			// get image
			ImageReadyVo ir = webImageUtil
					.prepareImageFromUrl(vo.getImageUrl());
			String resId = imageService.put(ir.getFile());
			Resource res = new Resource();
			res.setOrgSize(ir.getOrgSize());
			res.setResId(resId);
			res.setExt(ir.getExt());
			resourceRepository.save(res);
			spot.setImage(res);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if(StringUtils.hasText(vo.getPlaceId())){
			Place place = placeRepository.findOne(vo.getPlaceId());
			if(place!=null){
				CityMeta city = cityMetaRepository.getByName(place.getCity());
				if(city!=null){
					spot.setCity(city.getPinyin());
				}
				spot.setLngLat(place.getLngLat());
				spot.setPlace(place);
			}
		}else if(!StringUtils.hasText(spot.getCity())){
			spot.setCity(ApplicationConfig.defaultCityPinyin);
		}
		spot = spotRepository.save(spot);
		// increase spot count
		signInUser.setSpotCount(signInUser.getSpotCount()+1);
		userRepository.save(signInUser);
		// save activity
		Activity activity = new Activity();
		activity.setOwner(signInUser.getId());
		activity.setCreatedAt(new Date());
		activity.setTargetSpot(spot.getId());
		activity.setType(ActivityType.SPOT);
		activity.setBy(sessionUtil.getBy(session));
		activityRepository.save(activity);
		
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}
}
