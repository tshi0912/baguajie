package net.baguajie.domains;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.Gender;
import net.baguajie.vo.SignUpUserVo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document
public class User implements Serializable {
	
	@Id
	private String id;
	@NotEmpty
	@Indexed
	private String name;
	@NotEmpty
	@Indexed
	private String email;
	@NotEmpty
	private String password;
	private Gender gender;
	private String city;
	private Date birthDay;
	private String summary;
	private Resource avatar;
	// follow-ship statistic
	private int fansCount;
	private int followCount;
	// spot statistic
	private int spotCount;
	private int trackCount;
	private int forwardCount;
	private int commentCount;
	@NotNull
	private Date createdAt;
	private Date updatedAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Resource getAvatar() {
		return avatar;
	}
	public void setAvatar(Resource avatar) {
		this.avatar = avatar;
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
	public int getTrackCount() {
		return trackCount;
	}
	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}
	public int getForwardCount() {
		return forwardCount;
	}
	public void setForwardCount(int forwardCount) {
		this.forwardCount = forwardCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}else if(!(obj instanceof User)){
			return false;
		}
		return new EqualsBuilder()
				.append(id, ((User)obj).getId())
				.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(name)
				.toString();
	}
	
	public static User from(SignUpUserVo vo){
		User user = new User();
		user.setName(vo.getName());
		user.setEmail(vo.getEmail());
		user.setPassword(vo.getPassword());
		user.setCreatedAt(new Date());
		user.setGender(Gender.UNKNOWN);
		user.setCity(ApplicationConfig.defaultCityPinyin);
		return user;
	}
}
