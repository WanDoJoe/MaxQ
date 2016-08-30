package com.maxq.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 商品信息
 */
@Table(name="GoodsInfo")
public class GoodsInfo {
	@Column(name="GID",isId=true,autoGen=true,property = "NOT NULL")
	private int gid;
	@Column(name="ID",property = "NOT NULL")
    protected String Id;
	@Column(name="NAME",property = "NOT NULL")
    protected String name;
	@Column(name="ISCHOOSED",property = "NOT NULL")
    protected boolean isChoosed;
	@Column(name="IMAGEURL",property = "NOT NULL")
    private String imageUrl;
	@Column(name="DESC",property = "NOT NULL")
    private String desc;
	@Column(name="PRICE",property = "NOT NULL")
    private double price;
	@Column(name="COUNT",property = "NOT NULL")
    private int count;
	@Column(name="POSITION",property = "NOT NULL")
    private int position;// 绝对位置，只在ListView构造的购物车中，在删除时有效
	@Column(name="COLOR",property = "NOT NULL")
    private String color;
	@Column(name="SIZE",property = "NOT NULL")
    private String size;
	@Column(name="GOODSIMG",property = "NOT NULL")
    private int goodsImg;
	@Column(name="DISCOUNTPRICE",property = "NOT NULL")
    private double discountPrice;

    public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(int goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public GoodsInfo(String id, String name, String desc, double price, int count, String color,
             String size, int goodsImg,double discountPrice) {
        Id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.count = count;
        this.color=color;
        this.size=size;
        this.goodsImg=goodsImg;
        this.discountPrice=discountPrice;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
