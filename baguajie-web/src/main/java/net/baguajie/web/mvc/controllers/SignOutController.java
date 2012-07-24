package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConstants;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignOutController {

	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String signOut(HttpSession session){
		session.removeAttribute(ApplicationConstants.SESSION_SIGNIN_USER);
		return "redirect:/signin";
	}
}
