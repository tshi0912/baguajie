package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.domains.User;
import net.baguajie.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/profiles")
public class ViewNameCardController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value="/{id}/namecard", method=RequestMethod.GET)
	public String view(@PathVariable String id, Model model, 
			HttpServletRequest request, HttpSession session){
		
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "profiles/namecard";
	}
}
