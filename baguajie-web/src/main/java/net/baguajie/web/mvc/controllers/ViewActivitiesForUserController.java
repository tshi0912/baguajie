package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Activity;
import net.baguajie.domains.FollowShip;
import net.baguajie.domains.Spot;
import net.baguajie.domains.TrackShip;
import net.baguajie.domains.User;
import net.baguajie.exceptions.UnauthorizedOperationException;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.FollowShipRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.TrackShipRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.ActivityVo;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/activities")
public class ViewActivitiesForUserController {
	
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	FollowShipRepository followShipRepository;
	@Autowired
	TrackShipRepository trackShipRepository;
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/{id}/follow/{no}", method=RequestMethod.GET)
	public String follow(@PathVariable String id, 
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		Iterable<FollowShip> fss = followShipRepository
				.findByFollowedAndStatus(id, 0);
		List<String> ids = new ArrayList<String>();
		for (FollowShip fs: fss){
			if(fs.getTarget()!=null){
				ids.add(fs.getTarget().getId());
			}
		}
		List<String> types = new ArrayList<String>();
		types.add(ActivityType.SPOT.name());
		types.add(ActivityType.FORWARD.name());
		Pageable pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.listPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Iterable<Activity> acts = activityRepository
				.findByOwnerInAndTypeIn(ids, types, pageable);
		model.addAttribute("activities", toActVos(acts));
		return "dashboard/activities";
	}
	
	@RequestMapping(value="/{id}/track/{no}", method=RequestMethod.GET)
	public String track(@PathVariable String id, 
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		Iterable<TrackShip> tss = trackShipRepository.findByTrackedAndStatus(id, 0);
		List<String> ids = new ArrayList<String>();
		for (TrackShip ts: tss){
			if(ts.getTarget()!=null){
				ids.add(ts.getTarget().getId());
			}
		}
		List<String> types = new ArrayList<String>();
		types.add(ActivityType.SPOT.name());
		types.add(ActivityType.FORWARD.name());
		types.add(ActivityType.COMMENT.name());
		Pageable pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.listPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Iterable<Activity> acts = activityRepository
				.findByTragetSpotInAndTypeIn(ids, types, pageable);
		model.addAttribute("activities", toActVos(acts));
		return "dashboard/activities";
	}
	
	@RequestMapping(value="/{id}/cmtbased/{no}", method=RequestMethod.GET)
	public String cmtBased(@PathVariable String id, 
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		List<String> types = new ArrayList<String>();
		types.add(ActivityType.SPOT.name());
		types.add(ActivityType.FORWARD.name());
		Iterable<Activity> as = activityRepository.findByOwnerAndTypeIn(id, types);
		List<String> ids = new ArrayList<String>();
		for (Activity a: as){
			ids.add(a.getId());
		}
		Pageable pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.listPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		types.clear();
		types.add(ActivityType.COMMENT.name());
		User signInUser = sessionUtil.getSignInUser(session);
		if(signInUser==null){
			throw new UnauthorizedOperationException();
		}
		Iterable<Activity> acts = activityRepository
				.findByBasedOnInAndTypeInAndOwnerNot(ids, types, 
						signInUser.getId(),pageable);
		model.addAttribute("activities", toActVos(acts));
		return "dashboard/activities";
	}
	
	@RequestMapping(value="/{id}/cmtfrom/{no}", method=RequestMethod.GET)
	public String cmtFrom(@PathVariable String id, 
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		Pageable pageable = new PageRequest(Math.max(no, 0), 
				ApplicationConfig.listPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Iterable<Activity> acts = activityRepository.findByOwnerAndType(id, 
				ActivityType.COMMENT, pageable);
		model.addAttribute("activities", toActVos(acts));
		return "dashboard/activities";
	}

	private Collection<ActivityVo> toActVos(Iterable<Activity> acts){
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
			if(StringUtils.hasText(act.getBasedOn())){
				Activity basedOnAct = activityRepository.findOne(act.getBasedOn());
				ActivityVo basedOn = ActivityVo.from(basedOnAct);
				basedOn.setOwner(userRepository.findOne(basedOnAct.getOwner()));
				vo.setBasedOn(basedOn);
			}
			activities.add(vo);
		}
		return activities;
	}
}
