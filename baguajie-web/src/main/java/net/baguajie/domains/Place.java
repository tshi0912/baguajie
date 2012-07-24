package net.baguajie.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import net.baguajie.constants.ApplicationConstants;
import net.baguajie.vo.PlaceCreationVo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document
public class Place implements Serializable {
	
	@Id
	private String id;
	private String name;
	private String nation;
	private String province;
	private String city;
	private String district;
	private String street;
	private String zipCode;
	private String fullAddr;
	@GeoSpatialIndexed
	private Double[] lngLat;
	@NotNull
	private Date createdAt;
	@NotNull
	@DBRef
	private User createdBy;
	private Date updatedAt;
	@DBRef
	private User updatedBy;
	
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
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getFullAddr() {
		return fullAddr;
	}
	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Double[] getLngLat() {
		return lngLat;
	}
	public void setLngLat(Double[] lngLat) {
		this.lngLat = lngLat;
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
	public User getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
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
		}else if(!(obj instanceof Place)){
			return false;
		}
		return new EqualsBuilder()
				.append(id, ((Place)obj).getId())
				.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(fullAddr)
				.toString();
	}
	
	public static Place from(PlaceCreationVo vo, User signInUser){
		if(vo==null || signInUser == null) return null;
		Place place = new Place();
		if(vo.getAddress()!=null ||
				(vo.getLngLat()!=null &&
				 vo.getLngLat().length>1)){
			
			place.setCreatedAt(new Date());
			place.setCreatedBy(signInUser);
			Map<String, String> addr = vo.getAddress();
			place.setFullAddr(vo.getFullAddr());
			if(addr!=null){
				place.setNation(addr.get(ApplicationConstants.NATION));
				place.setProvince(addr.get(ApplicationConstants.PROVINCE));
				place.setCity(addr.get(ApplicationConstants.CITY));
				place.setDistrict(addr.get(ApplicationConstants.DISTRICT));
				place.setStreet(addr.get(ApplicationConstants.STREET));
				place.setZipCode(addr.get(ApplicationConstants.ZIP_CODE));
				if(place.getFullAddr()==null){
					place.setFullAddr(addr.get(ApplicationConstants.FULL_ADDR));
				}
			}
			if(vo.getLngLat()!=null &&
				vo.getLngLat().length>1){
				place.setLngLat(vo.getLngLat());
			}
		}
		return place;
	}
}
