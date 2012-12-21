package net.baguajie.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

@SuppressWarnings({"serial","rawtypes"})
public class PageVo implements Serializable {
	
	private int number;
	private int size;
	private int totalPages;
	private List content;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List getContent() {
		return content;
	}
	public void setContent(List content) {
		this.content = content;
	}
	
	public static PageVo from(Page page){
		PageVo vo = new PageVo();
		vo.setContent(page.getContent());
		vo.setNumber(page.getNumber());
		vo.setSize(page.getSize());
		vo.setTotalPages(page.getTotalPages());
		return vo;
	}
}
