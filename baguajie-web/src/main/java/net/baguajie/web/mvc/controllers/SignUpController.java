package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.baguajie.constants.ApplicationConstants;
import net.baguajie.domains.User;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.SignUpUserVo;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	SessionUtil sessionUtil;
	
	@ModelAttribute("signUpUserVo")
	public SignUpUserVo createSignUpUserVo() {
		SignUpUserVo vo = new SignUpUserVo();
		return vo;
	}

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signUp(Model model, HttpSession session){
		if(session.getAttribute(ApplicationConstants.SESSION_SIGNIN_USER)!=null){
			return "redirect:/";
		}
		model.addAttribute(ApplicationConstants.SESSION_LAST_VISITED_URL, 
				sessionUtil.getLastVisitedUrl(session));
		return "sign.up";
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signUp(@Valid SignUpUserVo signUpUserVo,
			BindingResult result,
			Model model, HttpSession session){
		User existed = null;
		if(!result.hasFieldErrors("email")){
			existed = userRepository.getByEmail(signUpUserVo.getEmail());
			if(existed !=null){
				result.addError(new FieldError("signUpUserVo", "email", "这个邮箱太受欢迎了,换一个吧"));
			}
		}
		if(!result.hasFieldErrors("name")){
			existed = userRepository.getByName(signUpUserVo.getName());
			if(existed != null){
				result.addError(new FieldError("signUpUserVo", "name", "这个昵称太抢手了,换一个吧"));
			}
		}
		if(result.hasErrors()){
			return "sign.up";
		}
		session.setAttribute(ApplicationConstants.SESSION_SIGNIN_USER, 
				userRepository.save(User.from(signUpUserVo)));
		return "redirect:" + sessionUtil.getLastVisitedUrl(session);
	}
}
