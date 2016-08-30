package com.maxq.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 店铺信息
 */
@Table(name = "StoreInfo")
public class StoreInfo {
	@Column(
            name = "ID",
            isId = true,
            autoGen = true,
            property = "NOT NULL"
    )
	private int sid;
	@Column(name = "ID",property = "NOT NULL")
	protected String Id;
	@Column(name="NAME",property = "NOT NULL")
	protected String name;
	@Column(name="ISCHOOSED",property = "NOT NULL")
	protected boolean isChoosed;
	@Column(name="ISEDTOR",property = "NOT NULL")
	private boolean isEdtor;

	public int getSid() {
		return sid;
	}

	public StoreInfo setSid(int sid) {
		this.sid = sid;
		return this;
	}

	public StoreInfo setEdtor(boolean isEdtor) {
		this.isEdtor = isEdtor;
		return this;
	}

	public boolean isEdtor() {
		return isEdtor;
	}

	public StoreInfo setIsEdtor(boolean isEdtor) {
		this.isEdtor = isEdtor;
		return this;
	}

	public StoreInfo(String id, String name) {
		Id = id;
		this.name = name;
	}

	public String getId() {
		return Id;
	}

	public StoreInfo setId(String id) {
		Id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public StoreInfo setName(String name) {
		this.name = name;
		return this;
	}

	public boolean isChoosed() {
		return isChoosed;
	}

	public StoreInfo setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
		return this;
	}
}
