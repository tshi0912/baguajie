package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.domains.User;
import net.baguajie.web.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController{
	
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String get(Model model, 
			HttpServletRequest request, HttpSession session){
		User user = sessionUtil.getSignInUser(session);
		if(user == null){
			return "forward:/signin";
		}
		model.addAttribute("user", user);
		return "dashboard";
	}
	
	@RequestMapping(value="/dashboard/cmt", method=RequestMethod.GET)
	public String cmt(Model model, 
			HttpServletRequest request, HttpSession session){
		User user = sessionUtil.getSignInUser(session);
		if(user == null){
			return "forward:/signin";
		}
		model.addAttribute("user", user);
		return "dashboard/dsb.cmt";
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.POST)
	public String post(Model model, 
			HttpServletRequest request, HttpSession session){
		return get(model, request, session);
	}

}
