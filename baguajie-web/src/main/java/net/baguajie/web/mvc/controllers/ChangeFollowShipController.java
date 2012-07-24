package net.baguajie.web.mvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.domains.Activity;
import net.baguajie.domains.FollowShip;
import net.baguajie.domains.User;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.exceptions.UnauthorizedOperationException;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.FollowShipRepository;
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
public class ChangeFollowShipController {
	
	@Autowired
	FollowShipRepository followShipRepository;
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	private AjaxUtil ajaxUtil;
	
	@RequestMapping(value="/follow/{targetId}", method=RequestMethod.GET)
	public @ResponseBody AjaxResult follow(@PathVariable String targetId,
			@ModelAttribute(ApplicationConstants.SESSION_SIGNIN_USER) User signInUser,
			Model model, HttpServletRequest request, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		User target = userRepository.findOne(targetId);
		if(target==null){
			throw new RuntimeException("Invalid user id : " + targetId);
		}
		FollowShip fs = followShipRepository.getByTargetAndFollowed(targetId, signInUser.getId());
		boolean followed = false;
		if(fs!=null && fs.getStatus() == ApplicationConstants.FOLLOWSHIP_DISABLED){
			followed = true;
			fs.setStatus(ApplicationConstants.FOLLOWSHIP_NORMAL);
			fs.setUpdatedAt(new Date());
			userRepository.inc(signInUser.getId(), "followCount", 1);
			userRepository.inc(target.getId(), "fansCount", 1);
		}else if(fs==null){
			followed = true;
			fs = new FollowShip();
			fs.setCreatedAt(new Date());
			fs.setUpdatedAt(fs.getCreatedAt());
			fs.setTarget(target);
			fs.setFollowed(signInUser);
			fs.setStatus(ApplicationConstants.FOLLOWSHIP_NORMAL);
			userRepository.inc(signInUser.getId(), "followCount", 1);
			userRepository.inc(target.getId(), "fansCount", 1);
		}
		if(followed){
			followShipRepository.save(fs);
			// save activity
			Activity activity = new Activity();
			activity.setOwner(signInUser.getId());
			activity.setCreatedAt(new Date());
			activity.setTargetUser(target.getId());
			activity.setType(ActivityType.FOLLOW);
			activity.setBy(sessionUtil.getBy(session));
			activityRepository.save(activity);
		}
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}
	
	@RequestMapping(value="/defollow/{targetId}", method=RequestMethod.GET)
	public @ResponseBody AjaxResult defollow(@PathVariable String targetId,
			@ModelAttribute(ApplicationConstants.SESSION_SIGNIN_USER) User signInUser,
			Model model, HttpServletRequest request, 
			HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		if(signInUser==null){
			throw new UnauthorizedOperationException();
		}
		User target = userRepository.findOne(targetId);
		if(target==null){
			throw new RuntimeException("Invalid user id : " + targetId);
		}
		FollowShip fs = followShipRepository.getByTargetAndFollowed(targetId, signInUser.getId());
		if(fs!=null && fs.getStatus() == ApplicationConstants.FOLLOWSHIP_NORMAL){
			fs.setStatus(ApplicationConstants.FOLLOWSHIP_DISABLED);
			fs.setUpdatedAt(new Date());
			userRepository.inc(signInUser.getId(), "followCount", -1);
			userRepository.inc(target.getId(), "fansCount", -1);
			followShipRepository.save(fs);
		}
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}
}
