package net.baguajie.vo;

import java.io.Serializable;
import java.util.Date;

import net.baguajie.constants.ActivityType;
import net.baguajie.constants.ByType;
import net.baguajie.domains.Activity;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;

@SuppressWarnings("serial")
public class ActivityVo implements Serializable{
	
	private String id;
	private ActivityType type;
	private ByType by;
	private String content;
	private Spot targetSpot;
	private User targetUser;
	private User owner;
	private ActivityVo basedOn;
	private Date createdAt;
	private int commentedCount;
	private int forwardedCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	public ByType getBy() {
		return by;
	}
	public void setBy(ByType by) {
		this.by = by;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Spot getTargetSpot() {
		return targetSpot;
	}
	public void setTargetSpot(Spot targetSpot) {
		this.targetSpot = targetSpot;
	}
	public User getTargetUser() {
		return targetUser;
	}
	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public ActivityVo getBasedOn() {
		return basedOn;
	}
	public void setBasedOn(ActivityVo basedOn) {
		this.basedOn = basedOn;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public int getCommentedCount() {
		return commentedCount;
	}
	public void setCommentedCount(int commentedCount) {
		this.commentedCount = commentedCount;
	}
	public int getForwardedCount() {
		return forwardedCount;
	}
	public void setForwardedCount(int forwardedCount) {
		this.forwardedCount = forwardedCount;
	}
	
	public static ActivityVo from(Activity activity){
		if(activity==null) return null;
		ActivityVo vo = new ActivityVo();
		vo.setBy(activity.getBy());
		vo.setId(activity.getId());
		vo.setCommentedCount(activity.getCommentedCount());
		vo.setContent(activity.getContent());
		vo.setCreatedAt(activity.getCreatedAt());
		vo.setForwardedCount(activity.getForwardedCount());
		vo.setType(activity.getType());
		return vo;
	}
}
