package net.baguajie.web.mvc.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.AjaxResultCode;
import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Comment;
import net.baguajie.domains.Spot;
import net.baguajie.domains.Activity;
import net.baguajie.exceptions.ResourceNotFoundException;
import net.baguajie.repositories.ActivityRepository;
import net.baguajie.repositories.CityMetaRepository;
import net.baguajie.repositories.CommentRepository;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.AjaxResult;
import net.baguajie.vo.MarkerVo;
import net.baguajie.vo.PinVo;
import net.baguajie.web.utils.AjaxUtil;
import net.baguajie.web.utils.MapUtil;
import net.baguajie.web.utils.SessionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spots")
public class SearchSpotsController {
	private static final Logger logger = 
			LoggerFactory.getLogger(SearchSpotsController.class);
	
	@Autowired
	private CityMetaRepository cityMetaRepository;
	@Autowired
	private SpotRepository spotRepository;
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private AjaxUtil ajaxUtil;
	@Autowired
	MapUtil mapUtil;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/search/list", method=RequestMethod.GET)
	public String search(Model model, HttpServletRequest request){
		Iterable<Spot> spots = doSearch(request, false);
		Collection<PinVo> pins = new ArrayList<PinVo>();
		if(spots!=null){
			Pageable pageable = new PageRequest(0, 
					ApplicationConfig.pinCmtPageSize, 
					new Sort(new Order(Direction.DESC, "createdAt")));
			for(Spot spot : spots){
				Activity act = activityRepository.getByOwnerAndTargetSpotAndType(
						spot.getCreatedBy().getId(), spot.getId(), ActivityType.SPOT.name());
				Page<Comment> cmts = null;
				if(act!=null){
					 cmts = commentRepository.findByAct(
							act.getId(), pageable);
				}
				if(spot.getPlace()==null){
					pins.add(PinVo.from(spot,
							cityMetaRepository.getByPinyin(StringUtils.hasText(spot.getCity())?
									spot.getCity():ApplicationConfig.defaultCityPinyin), act, cmts));
				}else{
					pins.add(PinVo.from(spot,null, act, cmts));
				}
			}
		}
		model.addAttribute("pins", pins);
		return "spots/list";
	}
	
	private Iterable<Spot> doSearch(HttpServletRequest request, boolean isMarker){
		String city = request.getParameter("city");
		String category = request.getParameter("category");
		String summaryLike = request.getParameter("keyword");
		String no = request.getParameter("no");
		int pageNo = 0;
		try{
			pageNo = Integer.parseInt(no);
		}catch(NumberFormatException nfe){
			logger.info("paramater page no is invalid, will use 0 as default.");
		}
		Pageable pageable = new PageRequest(Math.max(pageNo, 0), 
				ApplicationConfig.masonryPageSize, 
				new Sort(new Order(Direction.DESC, "createdAt")));
		if(ApplicationConfig.defaultCityPinyin.equals(city)){
			city = "";
		}
		
		Iterable<Spot> spots = null;
		if(!isMarker){
			spots = spotRepository.search(StringUtils.trimWhitespace(city), 
					StringUtils.trimWhitespace(category), 
						StringUtils.trimWhitespace(summaryLike), pageable);
		}else{
			spots = spotRepository.searchMarker( StringUtils.trimWhitespace(city), 
					StringUtils.trimWhitespace(category), 
						StringUtils.trimWhitespace(summaryLike), pageable);
		}
		return spots;
	}
	
	@RequestMapping(value="/search/marker", method=RequestMethod.GET)
	public @ResponseBody AjaxResult marker(Model model, HttpServletRequest request){
		if(!ajaxUtil.isAjaxRequest(request)){
			throw new ResourceNotFoundException();
		}
		Iterable<Spot> spots = doSearch(request, true);
		Collection<MarkerVo> markers = new ArrayList<MarkerVo>();
		if(spots!=null){
			for(Spot spot : spots){
				MarkerVo vo = MarkerVo.from(spot);
				if(vo!=null){
					markers.add(MarkerVo.from(spot));
				}
			}
		}
		return new AjaxResult(AjaxResultCode.SUCCESS, markers);
	}
}