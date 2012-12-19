package net.baguajie.web.ros;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ErrorCode implements Serializable{
	
	public static final String PERSISTENCE_ERROR = "001";
	public static final String BUSINESS_ERROR = "002";
	
	public static final int ERROR = 1;
	public static final int WARN = 2;
	
	private String code;
	private int severity;
	private String message;
	private String description;
	
	public ErrorCode(){}
	
	public ErrorCode(String msg, int severity, String code)
	{
		this.message = msg;
		this.severity = severity;
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
