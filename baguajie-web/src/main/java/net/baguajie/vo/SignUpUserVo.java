package net.baguajie.vo;

import java.io.Serializable;

import net.baguajie.web.mvc.validator.FieldMatch;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@FieldMatch(first = "password", second = "passwordRe", 
	message = "两次密码输入不一致")
public class SignUpUserVo implements Serializable {
	
	@NotBlank
	@Length(min=3, max=12)
	private String name;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Length(min=6, max=30)
	private String password;
	private String passwordRe;
	
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
	public String getPasswordRe() {
		return passwordRe;
	}
	public void setPasswordRe(String passwordRe) {
		this.passwordRe = passwordRe;
	}
}
