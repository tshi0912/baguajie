package net.baguajie.web.ros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ROResult implements Serializable {

	private List<String> errors;

	private Object result;

	private ErrorCode errorCode;

	public ROResult() {
	}

	public ROResult(Object res) {
		result = res;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public void addErrorMsg(String msg) {
		if (errors == null){
			errors = new ArrayList<String>();
		}
		errors.add(msg);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}
