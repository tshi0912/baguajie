package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppsController {

	@RequestMapping(value="/apps", method=RequestMethod.GET)
	public String view(Model model, 
			HttpServletRequest request, HttpSession session){
		return "apps";
	}
}
