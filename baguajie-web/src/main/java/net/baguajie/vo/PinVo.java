package net.baguajie.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Activity;
import net.baguajie.domains.CityMeta;
import net.baguajie.domains.Comment;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.web.utils.DomainObjectUtil;

@SuppressWarnings("serial")
public class PinVo implements Serializable {
	
	private String spotId;
	private String actId;
	private String name;
	private String summary;
	private String placeId;
	private String placeAddr;
	private String city;
	private String imageUrl;
	private int imageHeight;
	private Date createdAt;
	private String createdById;
	private String createdByName;
	private String createdByAvatarUrl;
	private String category;
	private Double[] lngLat;
	private List<Comment> cmts;
	private long totalCmtCount;
	
	public String getSpotId() {
		return spotId;
	}
	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceAddr() {
		return placeAddr;
	}
	public void setPlaceAddr(String placeAddr) {
		this.placeAddr = placeAddr;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getCreatedByAvatarUrl() {
		return createdByAvatarUrl;
	}
	public void setCreatedByAvatarUrl(String createdByAvatarUrl) {
		this.createdByAvatarUrl = createdByAvatarUrl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double[] getLngLat() {
		return lngLat;
	}
	public void setLngLat(Double[] lngLat) {
		this.lngLat = lngLat;
	}
	public List<Comment> getCmts() {
		return cmts;
	}
	public void setCmts(List<Comment> cmts) {
		this.cmts = cmts;
	}
	public long getTotalCmtCount() {
		return totalCmtCount;
	}
	public void setTotalCmtCount(long totalCmtCount) {
		this.totalCmtCount = totalCmtCount;
	}
	
	public static PinVo from(Spot spot, CityMeta cityMeta,
			Activity act, Page<Comment> cmts){
		if(spot == null) return null;
		PinVo vo = new PinVo();
		vo.setSpotId(spot.getId());
		if(act != null){
			vo.setActId(act.getId());
		}
		vo.setName(spot.getName());
		vo.setSummary(spot.getSummary());
		vo.setCategory(spot.getCategory());
		vo.setLngLat(spot.getLngLat());
		vo.setCreatedAt(spot.getCreatedAt());
		vo.setCity(spot.getCity());
		if(spot.getPlace()!=null){
			vo.setPlaceId(spot.getPlace().getId());
			vo.setPlaceAddr(new StringBuilder()
				.append(spot.getPlace().getCity())
				.append(spot.getPlace().getDistrict())
				.append(spot.getPlace().getStreet())
				.toString());
		}else if(cityMeta!=null){
			vo.setPlaceAddr(cityMeta.getName());
			vo.setCity(cityMeta.getPinyin());
		}
		if(spot.getImage()!=null){
			vo.setImageUrl(new StringBuilder()
				.append(ApplicationConfig.base)
				.append(ApplicationConfig.imageRefer)
				.append("/")
				.append(spot.getImage().getResId())
				.toString());
			if(spot.getImage().getOrgSize()!=null &&
					spot.getImage().getOrgSize().length==2){
				vo.setImageHeight(Math.round(192*spot.getImage().getOrgSize()[0]/
						spot.getImage().getOrgSize()[1]));
			}
			
		}
		if(spot.getCreatedBy()!=null){
			User user = spot.getCreatedBy();
			vo.setCreatedById(user.getId());
			vo.setCreatedByName(user.getName());
			vo.setCreatedByAvatarUrl(
				DomainObjectUtil.getAvatarUrl(user.getAvatar(), 
					user.getGender()));
		}
		if(cmts != null){
			List<Comment> comments = new ArrayList<Comment>();
			for(Comment cmt : cmts){
				comments.add(cmt);
			}
			vo.setCmts(comments);
			vo.setTotalCmtCount(cmts.getTotalElements());
		}
		return vo;
	}
}
