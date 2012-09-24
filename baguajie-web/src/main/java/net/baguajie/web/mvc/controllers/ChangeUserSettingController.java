package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.constants.Gender;
import net.baguajie.domains.User;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.AjaxResult;
import net.baguajie.vo.BindingErrors;
import net.baguajie.vo.formbean.UserBasicInfoFormBean;
import net.baguajie.vo.formbean.UserPwdChangeFormBean;
import net.baguajie.web.utils.SessionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(ApplicationConstants.SESSION_SIGNIN_USER)
public class ChangeUserSettingController {

	private Logger logger = LoggerFactory
			.getLogger(ChangeUserSettingController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SessionUtil sessionUtil;

	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting(Model model, HttpSession session) {
		if (sessionUtil.getSignInUser(session) == null) {
			return "redirect:/signin";
		}
		return "profiles/setting";
	}

	@RequestMapping(value = "/setting/basic", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResult submitBasic(@Valid UserBasicInfoFormBean formBean,
			BindingResult result, ModelAndView mav, HttpSession session) {
		User signInUser = sessionUtil.getSignInUser(session);
		if (signInUser == null) {
			return new AjaxResult(AjaxResultCode.NEED_SIGNIN);
		}
		if (result.hasErrors()) {
			return new AjaxResult(AjaxResultCode.INVALID,
					BindingErrors.from(result));
		}
		signInUser.setName(formBean.getName());
		signInUser.setCity(formBean.getCity());
		Gender gender = null;
		try {
			gender = Gender.valueOf(formBean.getGender());
		} catch (IllegalArgumentException iae) {
			logger.warn("Get illegal gender \"" + formBean.getGender()
					+ "\" for user" + signInUser.getName());
		}
		signInUser.setGender(gender);
		signInUser.setSummary(formBean.getSummary());
		userRepository.save(signInUser);
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}

	@RequestMapping(value = "/setting/changepwd", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResult changePwd(@Valid UserPwdChangeFormBean formBean,
			BindingResult result, ModelAndView mav, HttpSession session) {
		User signInUser = sessionUtil.getSignInUser(session);
		if (signInUser == null) {
			return new AjaxResult(AjaxResultCode.NEED_SIGNIN);
		}
		if (formBean.getOldPwd() != null
				&& !formBean.getOldPwd().equals(signInUser.getPassword())) {
			result.addError(new FieldError("formBean", "oldPwd", "密码不正确"));
		}
		if (result.hasErrors()) {
			return new AjaxResult(AjaxResultCode.INVALID,
					BindingErrors.from(result));
		}
		signInUser.setPassword(formBean.getNewPwd());
		userRepository.save(signInUser);
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}
}
