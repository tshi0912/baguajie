package net.baguajie.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ValidationEngineError {
	
	private String field;
	private boolean status;
	private String message;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static ValidationEngineError[] from(BindingResult result){
		List<ValidationEngineError> errors = new ArrayList<ValidationEngineError>();
		if(result.hasFieldErrors()){
			List<FieldError> fes = result.getFieldErrors();
			for(FieldError fe : fes){
				ValidationEngineError vee = new ValidationEngineError();
				vee.setField(fe.getField());
				vee.setMessage(fe.getDefaultMessage());
				vee.setStatus(false);
				errors.add(vee);
			}
			
		}
		if(result.hasGlobalErrors()){
			List<ObjectError> ges = result.getGlobalErrors();
			for(ObjectError ge : ges){
				ValidationEngineError vee = new ValidationEngineError();
				vee.setField(ge.getObjectName());
				vee.setMessage(ge.getDefaultMessage());
				vee.setStatus(false);
				errors.add(vee);
			}
		}
		return errors.toArray(new ValidationEngineError[]{});
	}
	
	public static Object[] normalize(ValidationEngineError[] errors){
		List<Object> objs = new ArrayList<Object>();
		for (int i=0; i<errors.length; i++)
		{
			ValidationEngineError vee = errors[i];
			Object[] obj = new Object[3];
			obj[0] = vee.getField();
			obj[1] = vee.getStatus();
			obj[2] = vee.getMessage();
			objs.add(obj);
		}
		return objs.toArray();
	}
}
