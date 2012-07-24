package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.Gender;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.PinUserVo;
import net.baguajie.web.utils.SessionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/profiles")
public class SearchProfilesController {
	private static final Logger logger = 
		LoggerFactory.getLogger(SearchProfilesController.class);

	@Autowired
	private SpotRepository spotRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model, HttpServletRequest request, 
			HttpSession session){
		String city = request.getParameter("city");
		String gender = request.getParameter("gender");
		String nameLike = request.getParameter("keyword");
		int no = Integer.parseInt(request.getParameter("no"));
		Pageable pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.masonryPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		if(ApplicationConfig.defaultCityPinyin.equals(city)){
			city = "";
		}
		if(StringUtils.hasText(gender)){
			gender = gender.toUpperCase();
			try{
				Gender g = Gender.valueOf(gender);
				gender = g.name();
			}catch(RuntimeException re){
				logger.error(re.getMessage());
			}
		}
		Collection<PinUserVo> pins = new ArrayList<PinUserVo>();
		Page<User> users = null;
//		if(StringUtils.hasText(city) && g != null && StringUtils.hasText(keyword)){
//			users = userRepository.findByCityAndGenderAndNameLike(city, g, keyword, pageable);
//		}else if(StringUtils.hasText(city) && g!=null && !StringUtils.hasText(keyword)){
//			users = userRepository.findByCityAndGender(city, g, pageable);
//		}else if(StringUtils.hasText(city) && g==null && StringUtils.hasText(keyword)){
//			users = userRepository.findByCityAndNameLike(city, keyword, pageable);
//		}else if(!StringUtils.hasText(city) && g!=null && StringUtils.hasText(keyword)){
//			users = userRepository.findByGenderAndNameLike(g, keyword, pageable);
//		}else if(!StringUtils.hasText(city) && g==null && StringUtils.hasText(keyword)){
//			users = userRepository.findByNameLike(keyword, pageable);
//		}else if(!StringUtils.hasText(city) && g!=null && !StringUtils.hasText(keyword)){
//			users = userRepository.findByGender(g, pageable);
//		}else if(StringUtils.hasText(city) && g==null && !StringUtils.hasText(keyword)){
//			users = userRepository.findByCity(city, pageable);
//		}else{
//			users = userRepository.findAll(pageable);
//		}
		users = userRepository.search(StringUtils.trimWhitespace(city), 
				StringUtils.trimWhitespace(gender), 
				StringUtils.trimWhitespace(nameLike), pageable);
		
		pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.masonryThumbPageSize, 
				new Sort(new Order(Direction.DESC, "updatedAt")));
		if(users!=null){
			for(User user : users){
				Page<Spot> spots = spotRepository.findByCreatedBy(user.getId(), pageable);
				pins.add(PinUserVo.from(user, spots.getContent()));
			}
		}
		model.addAttribute("pins", pins);
		return "profiles/peoples";
	}
	
}