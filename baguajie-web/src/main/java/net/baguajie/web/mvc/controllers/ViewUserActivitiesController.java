package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Activity;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.ActivityVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/profiles")
public class ViewUserActivitiesController {
	
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/activity/{id}/{no}", method=RequestMethod.GET)
	public String view(@PathVariable String id, 
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		Pageable pageable = new PageRequest(no >= 0 ? no : 0, 
				ApplicationConfig.listPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Iterable<Activity> acts = activityRepository.findByOwner(id, pageable);
		Collection<ActivityVo> activities = new ArrayList<ActivityVo>();
		for(Activity act : acts){
			ActivityVo vo = ActivityVo.from(act);
			if(act.getOwner()!=null){
				User owner = userRepository.findOne(act.getOwner());
				vo.setOwner(owner);
			}
			if(act.getTargetSpot()!=null){
				Spot spot = spotRepository.findOne(act.getTargetSpot());
				vo.setTargetSpot(spot);
			}
			if(act.getTargetUser()!=null){
				User targetUser = userRepository.findOne(act.getTargetUser());
				vo.setTargetUser(targetUser);
			}
			activities.add(vo);
		}
		model.addAttribute("activities", activities);
		return "profiles/activities";
	}
	
}
