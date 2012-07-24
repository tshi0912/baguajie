package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.domains.Forward;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.repositories.ForwardRepository;
import net.baguajie.web.utils.AjaxUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ops")
public class ViewSingleForwardController {

	@Autowired
	ForwardRepository forwadRepository;
	@Autowired
	private AjaxUtil ajaxUtil;
	
	@RequestMapping(value="/fwd/{id}/view/{type}", method=RequestMethod.GET)
	public String view(@PathVariable String id, 
			@PathVariable String type, Model model, 
			HttpServletRequest request, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		Forward fwd = forwadRepository.findOne(id);
		model.addAttribute("fwd", fwd);
		return "ops/fwd."+ type;
	}
	
}