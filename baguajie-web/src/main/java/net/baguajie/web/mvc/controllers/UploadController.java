package net.baguajie.web.mvc.controllers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConfig;
import net.baguajie.vo.AjaxResult;
import net.baguajie.web.utils.SessionUtil;
import net.baguajie.web.utils.WebImageUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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

	private Logger logger = Logger.getLogger(UploadController.class);
	
	private ApplicationContext ac;
	@Autowired
	private WebImageUtil webImageUtil;
	@Autowired
	private SessionUtil sessionUtil;

	@ModelAttribute("tempRepositories")
	public String getImageRepositoriesPath() {
		return ApplicationConfig.uploadTempRepository;
	}

	@RequestMapping(value = "/spots/upload", method = RequestMethod.POST)
	public @ResponseBody
	String uploadSpot(@RequestParam MultipartFile imageFile,
			@ModelAttribute("tempRepositories") String tempRepositories,
			Model model) throws IOException {
		logger.info("***get uploaded spot image file***");
		String orgName = imageFile.getOriginalFilename();
		String newName = new StringBuilder().append(System.currentTimeMillis())
				.append(orgName.substring(orgName.lastIndexOf('.'))).toString();
//		Resource res = ac.getResource(tempRepositories);
		logger.info("orgName:" + orgName + ",newName:"+newName);
		File file = webImageUtil.getTempFolder();
		if(!file.exists()){
			logger.info(file.getPath()+" does not exist, create a directory for it");
			file.mkdir();
		}
		if (file.isDirectory()) {
			file = new File(file.getPath() + File.separator + newName);
			logger.info("create a file " + file.getPath());
		}
		imageFile.transferTo(file);
//		return ApplicationConfig.base + ApplicationConfig.uploadTempRefer + "/"
//				+ newName;
		return newName;
	}

	@RequestMapping(value = "/avatar/upload", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResult uploadAvatar(@RequestParam MultipartFile imageFile,
			@ModelAttribute("tempRepositories") String tempRepositories,
			Model model, HttpServletResponse response) throws IOException {
		logger.info("***get uploaded avatar image file***");
		String orgName = imageFile.getOriginalFilename();
		String newName = new StringBuilder().append(System.currentTimeMillis())
				.append(orgName.substring(orgName.lastIndexOf('.'))).toString();
		String ext = orgName.substring(orgName.lastIndexOf('.'));
//		Resource res = ac.getResource(tempRepositories);
		logger.info("orgName:" + orgName + ",newName:"+newName);
		File file = webImageUtil.getTempFolder();
		if(!file.exists()){
			logger.info(file.getPath()+" does not exist, create a directory for it");
			file.mkdir();
		}
		if (file.isDirectory()) {
			file = new File(file.getPath() + File.separator + newName);
			logger.info("create a file " + file.getPath());
		}
		imageFile.transferTo(file);
		BufferedImage orgImg = ImageIO.read(file);
		float h = orgImg.getHeight();
		float w = orgImg.getWidth();
		AjaxResult result = new AjaxResult(AjaxResultCode.SUCCESS);
		if (h < 200 || w < 200) {
			result.setResultCode(AjaxResultCode.EXCEPTION);
			result.setExceptionMsg("头像尺寸不小于200x200");
			logger.info(result.getExceptionMsg());
		} else {
			if (w > 350) {
				w = 350;
				h = h / (orgImg.getWidth() / w);
				int rw = Math.round(w);
				int rh = Math.round(h);
				BufferedImage newImg = new BufferedImage(rw, rh,
						BufferedImage.TYPE_INT_RGB);
				// 创建Graphics2D对象，用于在BufferedImage对象上绘图。
				Graphics2D g = newImg.createGraphics();
				// 设置图形上下文的当前颜色为白色。
				g.setColor(Color.WHITE);
				// 用图形上下文的当前颜色填充指定的矩形区域。
				g.fillRect(0, 0, rw, rh);
				// 按照缩放的大小在BufferedImage对象上绘制原始图像。
				g.drawImage(orgImg, 0, 0, rw, rh, null);
				// 释放图形上下文使用的系统资源。
				g.dispose();
				// 刷新此 Image 对象正在使用的所有可重构的资源.
				orgImg.flush();
				newName = new StringBuilder()
						.append(System.currentTimeMillis()).append(ext)
						.toString();
				file = webImageUtil.getTempFolder();
				if (file.isDirectory()) {
					file = new File(file.getPath() + File.separator + newName);
					logger.info("create a new file " + file.getPath());
				}
				ImageIO.write(newImg, ext.substring(1), file);
			}
//			result.setResultData(ApplicationConfig.base
//					+ ApplicationConfig.uploadTempRefer + "/" + newName);
			result.setResultData(newName);
		}
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		return result;
	}

	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac = ac;
	}
}
