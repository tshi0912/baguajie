package net.baguajie.web.mvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.domains.Activity;
import net.baguajie.domains.Comment;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.CommentRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.AjaxResult;
import net.baguajie.vo.BindingErrors;
import net.baguajie.vo.CommentFormBean;
import net.baguajie.web.utils.AjaxUtil;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/ops")
@SessionAttributes(ApplicationConstants.SESSION_SIGNIN_USER)
public class AddCommentController {
	
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	private AjaxUtil ajaxUtil;
	
	@ModelAttribute("cmtFormBean")
	public CommentFormBean creatFormBean() {
		CommentFormBean bean = new CommentFormBean();
		return bean;
	}

	@RequestMapping(value="/cmt/create", method=RequestMethod.POST)
	public @ResponseBody AjaxResult create(@Valid CommentFormBean bean,
			@ModelAttribute(ApplicationConstants.SESSION_SIGNIN_USER) User signInUser,
			BindingResult result,
			Model model, HttpServletRequest request, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		if(result.hasErrors()){
			return new AjaxResult(AjaxResultCode.INVALID, 
					BindingErrors.from(result));
		}
		Activity activity = activityRepository.findOne(bean.getActId());
		if(activity==null){
			throw new RuntimeException("Invalid activity id:" + bean.getActId());
		}
		// save comment
		Comment cmt = Comment.from(bean, signInUser);
		cmt.setAct(activity);
		cmt = commentRepository.save(cmt);
		
		// incr commented count of orignal activity
		activityRepository.inc(activity.getId(), "commentedCount", 1);
		
		// incr comment count of sign in user
		userRepository.inc(signInUser.getId(), "commentCount", 1);
		
		if(activity.getTargetSpot()!=null){
			Spot spot = spotRepository.findOne(activity.getTargetSpot());
			// incr commented count of target spot
			spotRepository.inc(spot.getId(), "commentedCount", 1);
		}
		
		// save cmt activity
		activity = new Activity();
		activity.setOwner(signInUser.getId());
		activity.setCreatedAt(new Date());
		activity.setTargetSpot(activity.getTargetSpot());
		activity.setTargetUser(activity.getOwner());
		activity.setContent(bean.getContent());
		activity.setType(ActivityType.COMMENT);
		activity.setBasedOn(bean.getActId());
		activity.setBy(sessionUtil.getBy(session));
		activityRepository.save(activity);
		
		return new AjaxResult(AjaxResultCode.SUCCESS, cmt);
	}
}
