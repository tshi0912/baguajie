package net.baguajie.web.mvc.controllers;

import java.io.File;
import java.io.IOException;

import net.baguajie.constants.ApplicationConfig;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController implements ApplicationContextAware {
	
	private ApplicationContext ac;
	
	@ModelAttribute("tempRepositories")
	public String getImageRepositoriesPath(){
		return ApplicationConfig.uploadTempRepository;
	}
	
	@RequestMapping(value="/spots/upload", method=RequestMethod.POST)
	public @ResponseBody String process(@RequestParam MultipartFile imageFile, 
			@ModelAttribute("tempRepositories") String tempRepositories, 
			Model model) throws IOException {
		String orgName = imageFile.getOriginalFilename();
		String newName = new StringBuilder()
				.append(System.currentTimeMillis())
				.append(orgName.substring(orgName.lastIndexOf('.')))
				.toString();
//		File newFile = new File(imageRepositories + 
//				File.separator + newName);
		Resource res = ac.getResource(tempRepositories);
		File file = res.getFile();
		if(file.isDirectory()){
			file = new File(file.getPath() + File.separator + newName);
		}
		imageFile.transferTo(file);
		return ApplicationConfig.base + ApplicationConfig.uploadTempRefer + "/" + newName;
	}

	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac = ac;
	}
}
