package net.baguajie.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
public class SignInCredentialVo implements Serializable {
	
	@NotBlank
	@Email
	private String signInName;
	@NotBlank
	private String signInPassword;
	
	public String getSignInName() {
		return signInName;
	}
	public void setSignInName(String signInName) {
		this.signInName = signInName;
	}
	public String getSignInPassword() {
		return signInPassword;
	}
	public void setSignInPassword(String signInPassword) {
		this.signInPassword = signInPassword;
	}
}
