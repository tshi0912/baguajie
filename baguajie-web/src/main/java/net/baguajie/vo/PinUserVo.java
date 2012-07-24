package net.baguajie.vo;

import java.io.Serializable;
import java.util.List;

import net.baguajie.constants.Gender;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.web.utils.DomainObjectUtil;

@SuppressWarnings("serial")
public class PinUserVo implements Serializable {
	
	private String userId;
	private String city;
	private Gender gender;
	private String name;
	private boolean avatarUnset;
	private String avatarUrl;
	private int fansCount;
	private int followCount;
	private int spotCount;
	private String summary;
	private List<Spot> spots; 
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAvatarUnset() {
		return avatarUnset;
	}
	public void setAvatarUnset(boolean avatarUnset) {
		this.avatarUnset = avatarUnset;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public int getFansCount() {
		return fansCount;
	}
	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}
	public int getFollowCount() {
		return followCount;
	}
	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}
	public int getSpotCount() {
		return spotCount;
	}
	public void setSpotCount(int spotCount) {
		this.spotCount = spotCount;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public List<Spot> getSpots() {
		return spots;
	}
	public void setSpots(List<Spot> spots) {
		this.spots = spots;
	}
	
	public static PinUserVo from(User user, List<Spot> spots){
		if(user==null) return null;
		PinUserVo vo = new PinUserVo();
		vo.setUserId(user.getId());
		vo.setCity(user.getCity());
		vo.setGender(user.getGender());
		vo.setFansCount(user.getFansCount());
		vo.setFollowCount(user.getFollowCount());
		vo.setSpotCount(user.getSpotCount());
		vo.setName(user.getName());
		vo.setSummary(user.getSummary());
		if(user.getAvatar()==null){
			vo.setAvatarUnset(true);
		}
		vo.setAvatarUrl(DomainObjectUtil.getAvatarUrl(
			user.getAvatar(), user.getGender()));
		if(spots!=null){
			vo.setSpots(spots);
		}
		return vo;
	}
}
