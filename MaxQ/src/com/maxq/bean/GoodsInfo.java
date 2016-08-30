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

	public GoodsInfo setGid(int gid) {
		this.gid = gid;
		 return this;
	}

	public double getDiscountPrice() {
        return discountPrice;
    }

    public GoodsInfo setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }

    public int getGoodsImg() {
        return goodsImg;
    }

    public GoodsInfo setGoodsImg(int goodsImg) {
        this.goodsImg = goodsImg;
        return this;
    }

    public String getColor() {
        return color;
    }

    public GoodsInfo setColor(String color) {
        this.color = color;
        return this;
    }

    public String getSize() {
        return size;
    }

    public GoodsInfo setSize(String size) {
        this.size = size;
        return this;
    }
    public GoodsInfo(){}
    public GoodsInfo(int gid,String id, String name, String desc, double price, int count, String color,
             String size, int goodsImg,double discountPrice) {
    	this.gid=gid;
    	this.Id = id;
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

    public GoodsInfo setId(String id) {
        Id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GoodsInfo setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public GoodsInfo setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public GoodsInfo setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public GoodsInfo setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getCount() {
        return count;
    }

    public GoodsInfo setCount(int count) {
        this.count = count;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public GoodsInfo setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public GoodsInfo setPosition(int position) {
        this.position = position;
        return this;
    }

}
