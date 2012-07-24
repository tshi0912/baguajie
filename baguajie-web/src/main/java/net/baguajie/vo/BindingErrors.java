package net.baguajie.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@SuppressWarnings("serial")
public class BindingErrors implements Serializable {
	
	private Map<String, List<String>> fieldErrors;
	private Map<String, List<String>> globalErrors;

	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(Map<String, List<String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	public Map<String, List<String>> getGlobalErrors() {
		return globalErrors;
	}
	public void setGlobalErrors(Map<String, List<String>> globalErrors) {
		this.globalErrors = globalErrors;
	}
	
	public static BindingErrors from(BindingResult result){
		BindingErrors error = new BindingErrors();
		if(result.hasFieldErrors()){
			error.fieldErrors = new HashMap<String, List<String>>();
			List<FieldError> fes = result.getFieldErrors();
			for(FieldError fe : fes){
				List<String> msgs = error.fieldErrors.get(fe.getField());
				if(msgs == null){
					msgs = new ArrayList<String>();
					error.fieldErrors.put(fe.getField(), msgs);
				}
				msgs.add(fe.getDefaultMessage());
			}
			
		}
		if(result.hasGlobalErrors()){
			error.globalErrors = new HashMap<String, List<String>>();
			List<ObjectError> ges = result.getGlobalErrors();
			for(ObjectError ge : ges){
				List<String> msgs = error.globalErrors.get(ge.getObjectName());
				if(msgs == null){
					msgs = new ArrayList<String>();
					error.globalErrors.put(ge.getObjectName(), msgs);
				}
				msgs.add(ge.getDefaultMessage());
			}
		}
		return error;
	}
}
