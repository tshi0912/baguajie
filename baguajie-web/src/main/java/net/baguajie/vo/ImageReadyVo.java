package net.baguajie.vo;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ImageReadyVo implements Serializable {
	
	private Integer[] OrgSize;
	private String ext;
	private File file;
	
	public Integer[] getOrgSize() {
		return OrgSize;
	}
	public void setOrgSize(Integer[] orgSize) {
		OrgSize = orgSize;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
}
