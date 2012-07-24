package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Activity;
import net.baguajie.domains.Comment;
import net.baguajie.domains.Spot;
import net.baguajie.domains.TrackShip;
import net.baguajie.domains.User;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.repositories.CommentRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.TrackShipRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.PinVo;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/profiles")
public class ViewUserSpotsController {
	
	@Autowired
	CityMetaRepository cityMetaRepository;
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	TrackShipRepository trackShipRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/{id}/spots/{no}", method=RequestMethod.GET)
	public String spots(@PathVariable String id,
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		Pageable pageable = new PageRequest(no >= 0 ? no : 0, 
				ApplicationConfig.masonryPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Iterable<Spot> spots = spotRepository.findByCreatedBy(id, pageable);
		Collection<PinVo> pins = new ArrayList<PinVo>();
		if(spots!=null){
			pageable = new PageRequest(0, 
					ApplicationConfig.pinCmtPageSize, 
					new Sort(new Order(Direction.DESC, "createdAt")));
			for(Spot spot : spots){
				Activity act = activityRepository.getByOwnerAndTargetSpotAndType(
						spot.getCreatedBy().getId(), spot.getId(), ActivityType.SPOT.name());
				Page<Comment> cmts = null;
				if(act!=null){
					 cmts = commentRepository.findByAct(
							act.getId(), pageable);
				}
				if(spot.getPlace()==null){
					pins.add(PinVo.from(spot,
							cityMetaRepository.getByPinyin(StringUtils.hasText(spot.getCity())?
									spot.getCity():ApplicationConfig.defaultCityPinyin), act, cmts));
				}else{
					pins.add(PinVo.from(spot,null,act,cmts));
				}
			}
		}
		model.addAttribute("pins", pins);
		return "spots/list";
	}
	
	@RequestMapping(value="/{id}/tracks/{no}", method=RequestMethod.GET)
	public String tracks(@PathVariable String id,
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		Pageable pageable = new PageRequest(no >= 0 ? no : 0, 
				ApplicationConfig.masonryPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Iterable<TrackShip> tss = trackShipRepository.findByTrackedAndStatus(id, 0, pageable);
		Collection<PinVo> pins = new ArrayList<PinVo>();
		if(tss!=null){
			pageable = new PageRequest(0, 
					ApplicationConfig.pinCmtPageSize, 
					new Sort(new Order(Direction.DESC, "createdAt")));
			for(TrackShip ts : tss){
				if(ts.getTarget()==null) continue;
				Spot spot = ts.getTarget();
				Activity act = activityRepository.getByOwnerAndTargetSpotAndType(
						spot.getCreatedBy().getId(), spot.getId(), ActivityType.SPOT.name());
				Page<Comment> cmts = null;
				if(act!=null){
					 cmts = commentRepository.findByAct(
							act.getId(), pageable);
				}
				if(spot.getPlace()==null){
					pins.add(PinVo.from(spot,
							cityMetaRepository.getByPinyin(StringUtils.hasText(spot.getCity())?
									spot.getCity():ApplicationConfig.defaultCityPinyin), act, cmts));
				}else{
					pins.add(PinVo.from(spot,null,act, cmts));
				}
			}
		}
		model.addAttribute("pins", pins);
		return "spots/list";
	}
	
}
