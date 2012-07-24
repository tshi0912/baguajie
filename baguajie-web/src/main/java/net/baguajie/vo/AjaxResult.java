package net.baguajie.vo;

import java.io.Serializable;

import net.baguajie.constants.AjaxResultCode;

@SuppressWarnings("serial")
public class AjaxResult implements Serializable {
	
	private AjaxResultCode resultCode;
	private Object resultData;
	private String exceptionMsg;
	
	public AjaxResult(){};
	
	public AjaxResult(AjaxResultCode code){
		this.resultCode = code;
	}
	
	public AjaxResult(AjaxResultCode code, 
			String msg){
		this.resultCode = code;
		this.exceptionMsg = msg;
	}
	
	public AjaxResult(AjaxResultCode code, 
			Object data){
		this.resultCode = code;
		this.resultData = data;
	}
	
	public AjaxResultCode getResultCode() {
		return resultCode;
	}
	public void setResultCode(AjaxResultCode resultCode) {
		this.resultCode = resultCode;
	}
	public Object getResultData() {
		return resultData;
	}
	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
}
