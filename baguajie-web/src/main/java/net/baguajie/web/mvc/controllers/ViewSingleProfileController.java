package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.domains.User;
import net.baguajie.repositories.UserRepository;
import net.baguajie.web.utils.SessionUtil;

import org.apache.commons.lang.ArrayUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/profiles")
public class ViewSingleProfileController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String view(@PathVariable String id, Model model, 
			HttpServletRequest request, HttpSession session){
		return view(id, "spot", model, request, session);
	}
	
	@RequestMapping(value="/{id}/{view}", method=RequestMethod.GET)
	public String view(@PathVariable String id, 
			@PathVariable String view, Model model, 
			HttpServletRequest request, HttpSession session){
		String[] validViews = new String[]{"spot", "track", "forward", "fan", "follow"};
		if(!ArrayUtils.contains(validViews, view)){
			return "redirect:/profiles/"+id;
		}
		if(ArrayUtils.indexOf(validViews, view) > 2){
			model.addAttribute("viewUser", true);
		}
		User user = userRepository.findOne(id);
		model.addAttribute("view", view);
		model.addAttribute("user", user);
		return "profiles/single";
	}
}
