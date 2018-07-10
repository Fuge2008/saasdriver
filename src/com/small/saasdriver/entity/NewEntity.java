package com.small.saasdriver.entity;

import java.util.List;

/**
 * 封装新闻实体
 */
public class NewEntity {
	private String author_name;
	private String category;
	private String date;
	private String thumbnail_pic_s;
	// private String thumbnail_pic_s02;
	// private String thumbnail_pic_s03;
	private String title;
	private String uniquekey;
	private String url;

	public NewEntity() {
		super();
	}

	public NewEntity(String author_name, String category, String date, String thumbnail_pic_s, String title,
			String uniquekey, String url) {
		super();
		this.author_name = author_name;
		this.category = category;
		this.date = date;
		this.thumbnail_pic_s = thumbnail_pic_s;
		// this.thumbnail_pic_s02 = thumbnail_pic_s02;
		// this.thumbnail_pic_s03 = thumbnail_pic_s03;
		this.title = title;
		this.uniquekey = uniquekey;
		this.url = url;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getThumbnail_pic_s() {
		return thumbnail_pic_s;
	}

	public void setThumbnail_pic_s(String thumbnail_pic_s) {
		this.thumbnail_pic_s = thumbnail_pic_s;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "NewEntity [author_name=" + author_name + ", category=" + category + ", date=" + date
				+ ", thumbnail_pic_s=" + thumbnail_pic_s + ", title=" + title + ", uniquekey=" + uniquekey + ", url="
				+ url + "]";
	}

}
