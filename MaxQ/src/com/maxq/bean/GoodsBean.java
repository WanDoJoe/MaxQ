package com.maxq.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private List<ImageBean> imageBeans;
	private int id;
	private boolean isExpand;
	
	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ImageBean> getImageBeans() {
		return imageBeans;
	}

	public void setImageBeans(List<ImageBean> imageBeans) {
		this.imageBeans = imageBeans;
	}

}
