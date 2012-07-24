package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.domains.Comment;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.repositories.CommentRepository;
import net.baguajie.web.utils.AjaxUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ops")
public class ViewSingleCommentController {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	private AjaxUtil ajaxUtil;
	
	@RequestMapping(value="/cmt/{id}/view/{type}", method=RequestMethod.GET)
	public String view(@PathVariable String id, 
			@PathVariable String type, Model model, 
			HttpServletRequest request, HttpSession session){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		Comment cmt = commentRepository.findOne(id);
		model.addAttribute("cmt", cmt);
		return "ops/cmt."+ type;
	}
	
}
