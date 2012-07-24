package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Comment;
import net.baguajie.domains.Forward;
import net.baguajie.repositories.CommentRepository;
import net.baguajie.repositories.ForwardRepository;

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
public class ViewActivityForwardsController {
	
	@Autowired
	ForwardRepository forwardRepository;

	@RequestMapping(value="/{id}/fwds/{no}", method=RequestMethod.GET)
	public String dashboard(@PathVariable String id, 
			@PathVariable int no, Model model, 
			HttpServletRequest request, HttpSession session){
		Pageable pageable = new PageRequest(0, 
				ApplicationConfig.pinCmtPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		Page<Forward> page = forwardRepository.findByAct(id, pageable);
		List<Forward> fwds = null;
		if(page!=null && page.hasContent()){
			fwds = new ArrayList<Forward>();
			for(Forward fwd: page){
				fwds.add(fwd);
			}
		}
		model.addAttribute("fwds", fwds);
		model.addAttribute("totalFwdCount", page.getTotalElements());
		return "dashboard/fwds";
	}
}