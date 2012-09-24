package net.baguajie.vo.formbean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
public class UserAvatarFormBean implements Serializable {

	@NotEmpty
	private String imageURI;
	@NotNull
	private Integer viewHeigth;
	@NotNull
	private Integer viewWidth;
	@NotNull
	private Integer[] topLeft;
	@NotNull
	private Integer[] bottomRight;
	
	public String getImageURI() {
		return imageURI;
	}
	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}
	public Integer getViewHeigth() {
		return viewHeigth;
	}
	public void setViewHeigth(Integer viewHeigth) {
		this.viewHeigth = viewHeigth;
	}
	public Integer getViewWidth() {
		return viewWidth;
	}
	public void setViewWidth(Integer viewWidth) {
		this.viewWidth = viewWidth;
	}
	public Integer[] getTopLeft() {
		return topLeft;
	}
	public void setTopLeft(Integer[] topLeft) {
		this.topLeft = topLeft;
	}
	public Integer[] getBottomRight() {
		return bottomRight;
	}
	public void setBottomRight(Integer[] bottomRight) {
		this.bottomRight = bottomRight;
	}
	
}
