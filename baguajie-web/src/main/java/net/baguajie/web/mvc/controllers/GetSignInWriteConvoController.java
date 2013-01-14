package net.baguajie.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.baguajie.constants.ApplicationConstants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(ApplicationConstants.SESSION_SIGNIN_USER)
public class GetSignInWriteConvoController {
	@RequestMapping(value="/signin/writeconvo", method=RequestMethod.GET)
	public String thumb(Model model, HttpServletRequest request, 
			HttpSession session){
		return "comp/sign.in.write.convo";
	}
}
