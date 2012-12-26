package net.baguajie.domains;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import net.baguajie.constants.CommentStatus;
import net.baguajie.vo.CommentFormBean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document
public class Comment implements Serializable {

	@Id
	private String id;
	@NotEmpty
	private String content;
	@NotNull
	@DBRef
	private Activity act;
	@NotNull
	@DBRef
	private User createdBy;
	@NotNull
	private Date createdAt;
	private CommentStatus status;
	private int agreeCount;
	private int disagreeCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Activity getAct() {
		return act;
	}
	public void setAct(Activity act) {
		this.act = act;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus status) {
		this.status = status;
	}
	public int getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(int agreeCount) {
		this.agreeCount = agreeCount;
	}
	public int getDisagreeCount() {
		return disagreeCount;
	}
	public void setDisagreeCount(int disagreeCount) {
		this.disagreeCount = disagreeCount;
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
		}else if(!(obj instanceof Comment)){
			return false;
		}
		return new EqualsBuilder()
				.append(id, ((Comment)obj).getId())
				.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(act)
				.append(createdBy)
				.append(createdAt)
				.toString();
	}
	
	public static Comment from(CommentFormBean formBean,
			User signInUser){
		if(formBean==null || signInUser==null) return null;
		Comment cmt = new Comment();
		cmt.setContent(formBean.getContent());
		cmt.setCreatedBy(signInUser);
		cmt.setCreatedAt(new Date());
		cmt.setDisagreeCount(0);
		cmt.setAgreeCount(0);
		cmt.setStatus(CommentStatus.VALID);
		return cmt;
	}
	
}
