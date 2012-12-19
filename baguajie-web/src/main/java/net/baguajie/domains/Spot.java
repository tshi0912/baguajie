package net.baguajie.domains;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import net.baguajie.constants.SpotStatus;
import net.baguajie.vo.SpotCreationVo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document
public class Spot implements Serializable {
	
	@Id
	private String id;
	@NotNull
	@DBRef
	private Resource image;
	@Indexed
	private String name;
	private String summary;
	@DBRef
	@Indexed
	private Place place;
	@GeoSpatialIndexed
	private Double[] lngLat;
	@NotNull
	private Date createdAt;
	@NotNull
	@DBRef
	@Indexed
	private User createdBy;
	private Date updatedAt;
	private String city;
	private String category;
	private int trackedCount;
	private int forwardedCount;
	private int commentedCount;
	private int sharedCount;
	private SpotStatus status;
	
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
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public Resource getImage() {
		return image;
	}
	public void setImage(Resource image) {
		this.image = image;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	} 
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public int getTrackedCount() {
		return trackedCount;
	}
	public void setTrackedCount(int trackedCount) {
		this.trackedCount = trackedCount;
	}
	public int getForwardedCount() {
		return forwardedCount;
	}
	public void setForwardedCount(int forwardedCount) {
		this.forwardedCount = forwardedCount;
	}
	public int getCommentedCount() {
		return commentedCount;
	}
	public void setCommentedCount(int commentedCount) {
		this.commentedCount = commentedCount;
	}
	public int getSharedCount() {
		return sharedCount;
	}
	public void setSharedCount(int sharedCount) {
		this.sharedCount = sharedCount;
	}
	public SpotStatus getStatus() {
		return status;
	}
	public void setStatus(SpotStatus status) {
		this.status = status;
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
		}else if(!(obj instanceof Spot)){
			return false;
		}
		return new EqualsBuilder()
				.append(id, ((Spot)obj).getId())
				.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(name)
				.toString();
	}
	
	public static Spot from(SpotCreationVo vo, User signInUser){
		if(vo == null || signInUser == null) return null;
		Spot spot = new Spot();
		spot.setName(vo.getName());
		spot.setSummary(vo.getSummary());
		spot.setCreatedAt(new Date());
		spot.setUpdatedAt(spot.getCreatedAt());
		spot.setCreatedBy(signInUser);
		spot.setCategory(vo.getCategory());
		spot.setCity(vo.getCity());
		spot.setStatus(SpotStatus.VALID);
		return spot;
	}
}
