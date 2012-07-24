package net.baguajie.vo;

import java.io.Serializable;
import java.util.Date;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;

@SuppressWarnings("serial")
public class MarkerVo implements Serializable {
	
	private String spotId;
	private String name;
	private String summary;
	private String placeId;
	private String placeAddr;
	private String imageUrl;
	private int imageHeight;
	private Date createdAt;
	private String createdById;
	private String createdByName;
	private String createdByAvatarUrl;
	private String category;
	private Double[] lngLat;
	
	public String getSpotId() {
		return spotId;
	}
	public void setSpotId(String spotId) {
		this.spotId = spotId;
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
	
	public static MarkerVo from(Spot spot){
		if(spot == null || spot.getLngLat()==null ||
				spot.getLngLat().length!=2) return null;
		MarkerVo vo = new MarkerVo();
		vo.setSpotId(spot.getId());
		vo.setName(spot.getName());
		vo.setSummary(spot.getSummary());
		vo.setCategory(spot.getCategory());
		vo.setLngLat(spot.getLngLat());
		vo.setCreatedAt(spot.getCreatedAt());
		if(spot.getPlace()!=null){
			vo.setPlaceId(spot.getPlace().getId());
			vo.setPlaceAddr(new StringBuilder()
				.append(spot.getPlace().getCity())
				.append(spot.getPlace().getDistrict())
				.append(spot.getPlace().getStreet())
				.toString());
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
			if(user.getAvatar()!=null){
				vo.setCreatedByAvatarUrl(new StringBuilder()
					.append(ApplicationConfig.base)
					.append(ApplicationConfig.imageRefer)
					.append("/")
					.append(user.getAvatar().getResId())
					.toString());
			}
		}
		return vo;
	}
}
