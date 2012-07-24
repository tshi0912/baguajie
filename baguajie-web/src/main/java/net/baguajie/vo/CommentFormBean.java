package net.baguajie.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
public class CommentFormBean implements Serializable {

	@NotEmpty
	private String actId;
	@NotEmpty
	private String content;
	
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
