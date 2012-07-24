package net.baguajie.web.mvc.controllers;

import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.domains.User;
import net.baguajie.domains.UserPreference;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.repositories.UserPreferenceRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.AjaxResult;
import net.baguajie.vo.SignInCredentialVo;
import net.baguajie.web.utils.AjaxUtil;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {
	
	@Autowired
	UserRepository userPepository;
	@Autowired
	UserPreferenceRepository userPreferenceRepository;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	AjaxUtil ajaxUtil;
	
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String signIn(HttpSession session){
		if(session.getAttribute(ApplicationConstants.SESSION_SIGNIN_USER)!=null){
			return "redirect:/";
		}
		return "sign.in";
	}
	
	
	@RequestMapping(value="/checksignin", method=RequestMethod.GET)
	public @ResponseBody AjaxResult checkSignIn(HttpServletRequest request, 
			ModelAndView mav, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		if(sessionUtil.getSignInUser(session)!=null){
			Calendar c = Calendar.getInstance();
			c.setTimeZone(TimeZone.getTimeZone("UTC"));
			return new AjaxResult(AjaxResultCode.SUCCESS, c.getTimeInMillis());
		}else{
			return new AjaxResult(AjaxResultCode.NEED_SIGNIN);
		}
	}
	
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public String signIn(@Valid SignInCredentialVo signInCredentialVo, 
			BindingResult result,
			Model model, HttpSession session){
		User existed = null;
		if(!result.hasFieldErrors("signInName")){
			existed = userPepository
					.getByEmail(signInCredentialVo.getSignInName());
			if(existed == null){
				result.addError(new FieldError("signInCredentialVo", "signInName", 
						"注册邮箱不存在"));
			}else{
				if(!signInCredentialVo.getSignInPassword()
						.equals(existed.getPassword())){
					result.addError(new FieldError("signInCredentialVo", "signInPassword", 
							"密码不正确"));
				}
			}
		}
		
		if(result.hasErrors()){
			return "sign.in";
		}
		
		if(existed!=null){
			session.setAttribute(
					ApplicationConstants.SESSION_SIGNIN_USER, existed);
			UserPreference up = userPreferenceRepository.getByUser(existed);
			if(up!=null){
				sessionUtil.setSignInUserPrefer(up, session);
			}
		}
		
		return "redirect:/";
	}
	
}
