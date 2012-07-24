package net.baguajie.web.mvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.domains.Activity;
import net.baguajie.domains.Spot;
import net.baguajie.domains.TrackShip;
import net.baguajie.domains.User;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.TrackShipRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.AjaxResult;
import net.baguajie.web.utils.AjaxUtil;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/ops")
@SessionAttributes(ApplicationConstants.SESSION_SIGNIN_USER)
public class ChangeTrackShipController {
	
	@Autowired
	TrackShipRepository trackShipRepository;
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	private AjaxUtil ajaxUtil;
	
	@RequestMapping(value="/track/{targetId}", method=RequestMethod.GET)
	public @ResponseBody AjaxResult follow(@PathVariable String targetId,
			@ModelAttribute(ApplicationConstants.SESSION_SIGNIN_USER) User signInUser,
			Model model, HttpServletRequest request, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		Spot target = spotRepository.findOne(targetId);
		if(target==null){
			throw new RuntimeException("Invalid spot id : " + targetId);
		}
		TrackShip ts = trackShipRepository.getByTargetAndTracked(targetId, signInUser.getId());
		boolean tracked = false;
		if(ts!=null && ts.getStatus() == ApplicationConstants.TRACKSHIP_DISABLED){
			tracked = true;
			ts.setStatus(ApplicationConstants.TRACKSHIP_NORMAL);
			ts.setUpdatedAt(new Date());
			userRepository.inc(signInUser.getId(), "trackCount", 1);
			spotRepository.inc(target.getId(), "trackedCount", 1);
			trackShipRepository.save(ts);
		}else if(ts==null){
			tracked = true;
			ts = new TrackShip();
			ts.setCreatedAt(new Date());
			ts.setUpdatedAt(ts.getCreatedAt());
			ts.setTarget(target);
			ts.setTracked(signInUser);
			ts.setStatus(ApplicationConstants.FOLLOWSHIP_NORMAL);
			userRepository.inc(signInUser.getId(), "trackCount", 1);
			spotRepository.inc(target.getId(), "trackedCount", 1);
			trackShipRepository.save(ts);
		}
		if(tracked){
			trackShipRepository.save(ts);
			// save activity
			Activity activity = new Activity();
			activity.setOwner(signInUser.getId());
			activity.setCreatedAt(new Date());
			activity.setTargetSpot(target.getId());
			activity.setType(ActivityType.TRACK);
			activity.setBy(sessionUtil.getBy(session));
			activityRepository.save(activity);
		}
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}
	
	@RequestMapping(value="/detrack/{targetId}", method=RequestMethod.GET)
	public @ResponseBody AjaxResult defollow(@PathVariable String targetId,
			@ModelAttribute(ApplicationConstants.SESSION_SIGNIN_USER) User signInUser,
			Model model, HttpServletRequest request, 
			HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		Spot target = spotRepository.findOne(targetId);
		if(target==null){
			throw new RuntimeException("Invalid spot id : " + targetId);
		}
		TrackShip ts = trackShipRepository.getByTargetAndTracked(targetId, signInUser.getId());
		if(ts!=null && ts.getStatus() == ApplicationConstants.TRACKSHIP_NORMAL){
			ts.setStatus(ApplicationConstants.TRACKSHIP_DISABLED);
			ts.setUpdatedAt(new Date());
			userRepository.inc(signInUser.getId(), "trackCount", -1);
			spotRepository.inc(target.getId(), "trackedCount", -1);
			trackShipRepository.save(ts);
		}
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}
	
}
