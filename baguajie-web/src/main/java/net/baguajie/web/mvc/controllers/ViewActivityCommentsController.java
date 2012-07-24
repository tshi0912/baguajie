package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Comment;
import net.baguajie.repositories.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/activities")
public class ViewActivityCommentsController {
	
	@Autowired
	CommentRepository commentRepository;

	@RequestMapping(value="/{id}/cmts/{no}", method=RequestMethod.GET)
	public String dashboard(@PathVariable String id, 
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		Pageable pageable = new PageRequest(0, 
				ApplicationConfig.pinCmtPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Page<Comment> page = commentRepository.findByAct(id, pageable);
		List<Comment> cmts = null;
		if(page!=null && page.hasContent()){
			cmts = new ArrayList<Comment>();
			for(Comment cmt: page){
				cmts.add(cmt);
			}
		}
		model.addAttribute("cmts", cmts);
		model.addAttribute("totalCmtCount", page.getTotalElements());
		return "dashboard/cmts";
	}
}
