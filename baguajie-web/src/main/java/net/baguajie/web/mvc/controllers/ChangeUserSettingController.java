package net.baguajie.web.mvc.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConstants;
import net.baguajie.constants.Gender;
import net.baguajie.domains.Resource;
import net.baguajie.domains.User;
import net.baguajie.repositories.ResourceRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.services.ImageService;
import net.baguajie.vo.AjaxResult;
import net.baguajie.vo.BindingErrors;
import net.baguajie.vo.ValidationEngineError;
import net.baguajie.vo.formbean.UserAvatarFormBean;
import net.baguajie.vo.formbean.UserBasicInfoFormBean;
import net.baguajie.vo.formbean.UserPwdChangeFormBean;
import net.baguajie.web.utils.SessionUtil;
import net.baguajie.web.utils.WebImageUtil;

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
	@Autowired
	private ImageService imageService;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private WebImageUtil webImageUtil;

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

	@RequestMapping(value = "/setting/basic/validate", method = RequestMethod.POST)
	public @ResponseBody
	Object[] validateSubmitBasic(@Valid UserBasicInfoFormBean formBean,
			BindingResult result, ModelAndView mav, HttpSession session) {
		String name = formBean.getName();
		User signInUser = sessionUtil.getSignInUser(session);
		if (!signInUser.getName().equals(name)) {
			User user = userRepository.getByName(name);
			if (user != null) {
				result.addError(new FieldError("formBean", "name",
						"这个昵称太抢手了,换一个吧"));
			}
		}
		if (result.hasErrors()) {
			return ValidationEngineError.normalize(ValidationEngineError
					.from(result));
		} else {
			return new ValidationEngineError[] {};
		}
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

	@RequestMapping(value = "/setting/changepwd/validate", method = RequestMethod.POST)
	public @ResponseBody
	Object[] validateChangePwd(@Valid UserPwdChangeFormBean formBean,
			BindingResult result, ModelAndView mav, HttpSession session) {
		User signInUser = sessionUtil.getSignInUser(session);
		if (formBean.getOldPwd() != null
				&& !formBean.getOldPwd().equals(signInUser.getPassword())) {
			result.addError(new FieldError("formBean", "oldPwd", "密码不正确"));
		}
		if (result.hasErrors()) {
			return ValidationEngineError.normalize(ValidationEngineError
					.from(result));
		} else {
			return new ValidationEngineError[] {};
		}
	}

	@RequestMapping(value = "/setting/avatar", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResult changeAvatar(@Valid UserAvatarFormBean formBean,
			BindingResult result, ModelAndView mav, HttpSession session) {
		User signInUser = sessionUtil.getSignInUser(session);
		if (signInUser == null) {
			return new AjaxResult(AjaxResultCode.NEED_SIGNIN);
		}
		if (result.hasErrors()) {
			return new AjaxResult(AjaxResultCode.INVALID,
					BindingErrors.from(result));
		}
		try {
			// get image from GridFS
			String[] tokens = formBean.getImageUrl().split("/");
			Resource res = resourceRepository.getByResId(tokens[tokens.length-1]);
			if(res == null){
				return new AjaxResult(AjaxResultCode.INVALID,
						"resource " + formBean.getImageUrl() + " is invalid.");
			}
			File org = webImageUtil.getFile((new Date()).getTime()+"."+res.getExt());
			org.createNewFile();
			imageService.get(res.getResId(), new FileOutputStream(org));
			BufferedImage orgImg = ImageIO.read(org);
			signInUser.setAvatarOrg(res);
			// save avatar file
			BufferedImage avatarImg = orgImg.getSubimage(formBean.getX(),
					formBean.getY(), formBean.getW(), formBean.getH());
			ImageIO.write(avatarImg, res.getExt(), org);
			String resId = imageService.put(org);
			res = new Resource();
			res.setOrgSize(new Integer[] { avatarImg.getHeight(), avatarImg.getWidth() });
			res.setResId(resId);
			res.setExt(res.getExt());
			resourceRepository.save(res);
			signInUser.setAvatar(res);
			userRepository.save(signInUser);
		} catch (Exception e) {
			return new AjaxResult(AjaxResultCode.EXCEPTION, e.getMessage());
		}
		return new AjaxResult(AjaxResultCode.SUCCESS);
	}

}
