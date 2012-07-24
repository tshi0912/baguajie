package net.baguajie.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FilterElementVo implements Serializable {
	
	private String type;
	private String typeLabel;
	private Object value;
	private String label;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeLabel() {
		return typeLabel;
	}
	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
