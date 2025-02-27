package com.lifesupport.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "blog")
public class Blog {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;

	private String shortcontent;

	@Column(columnDefinition = "LONGTEXT")
	private String content;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date createAt;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date updateAt;
	private String imageFileName;
	private Integer count;
	@ManyToOne
	@JoinColumn(name = "categoryId")
	Category category;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "blog_tag_link", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<BlogTag> tags = new ArrayList<>();

	public Blog() {
		super();
	}

	public Blog(Integer id, String title, String shortcontent, String content, Date createAt, Date updateAt,
			String imageFileName, Integer count, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.shortcontent = shortcontent;
		this.content = content;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.imageFileName = imageFileName;
		this.count = count;
		this.category = category;
	}

	
	public List<BlogTag> getTags() {
		return tags;
	}

	public void setTags(List<BlogTag> tags) {
		this.tags = tags;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortcontent() {
		return shortcontent;
	}

	public void setShortcontent(String shortcontent) {
		this.shortcontent = shortcontent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
