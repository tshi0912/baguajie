package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.FollowShip;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.repositories.FollowShipRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.PinUserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class ViewUserRelationsController {

	@Autowired
	FollowShipRepository followShipRepository;
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/{id}/fans/{no}", method=RequestMethod.GET)
	public String fans(@PathVariable String id,
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		User user = userRepository.findOne(id);
		if(user==null){
			throw new RuntimeException("Invalid user id : " + id);
		}
		Pageable pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.masonryPageSize, 
				new Sort(new Order(Direction.DESC, "updatedAt")));
		Page<FollowShip> fss = followShipRepository.findByTargetAndStatus(id, 0, pageable);
		pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.masonryThumbPageSize, 
				new Sort(new Order(Direction.DESC, "updatedAt")));
		List<PinUserVo> pins = new ArrayList<PinUserVo>();
		if(fss!=null){
			for(FollowShip fs : fss){
				if(fs.getFollowed()==null) continue;
				Page<Spot> spots = spotRepository.findByCreatedBy(fs.getFollowed().getId(), pageable);
				pins.add(PinUserVo.from(fs.getFollowed(), spots.getContent()));
			}
		}
		model.addAttribute("pins", pins);
		return "profiles/peoples";
	}
	
	@RequestMapping(value="/{id}/follows/{no}", method=RequestMethod.GET)
	public String follows(@PathVariable String id,
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		User user = userRepository.findOne(id);
		if(user==null){
			throw new RuntimeException("Invalid user id : " + id);
		}
		Pageable pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.masonryPageSize, 
				new Sort(new Order(Direction.DESC, "updatedAt")));
		Page<FollowShip> fss = followShipRepository.findByFollowedAndStatus(id, 0, pageable);
		pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.masonryThumbPageSize, 
				new Sort(new Order(Direction.DESC, "updatedAt")));
		List<PinUserVo> pins = new ArrayList<PinUserVo>();
		if(fss!=null){
			for(FollowShip fs : fss){
				if(fs.getFollowed()==null) continue;
				Page<Spot> spots = spotRepository.findByCreatedBy(fs.getTarget().getId(), pageable);
				pins.add(PinUserVo.from(fs.getTarget(), spots.getContent()));
			}
		}
		model.addAttribute("pins", pins);
		return "profiles/peoples";
	}
}
