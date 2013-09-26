package net.baguajie.vo;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ImageReadyVo implements Serializable {
	
	private Integer[] orgSize;
	private String ext;
	private File file;
	
	public Integer[] getOrgSize() {
		return orgSize;
	}
	public void setOrgSize(Integer[] orgSize) {
		this.orgSize = orgSize;
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
	public String toString(){
		StringBuilder sb = new StringBuilder()
			.append("path="+(file != null ? file.getPath() : "[empty file]"))
			.append("ext=" + ext)
			.append("orgSize=" + orgSize);
		return sb.toString();
	}
}
