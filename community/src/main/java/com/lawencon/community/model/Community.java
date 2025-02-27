package com.lawencon.community.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "comm_community", uniqueConstraints = {
		@UniqueConstraint(
				name = "community_code_bk", columnNames = { "community_code" }
				),
		@UniqueConstraint(
				name = "community_title_bk", columnNames = { "community_title" }
				)
		}
)
public class Community extends BaseEntity {

	private static final long serialVersionUID = 5624571446289080291L;

	@Column(name = "community_title", columnDefinition = "TEXT")
	private String communityTitle;

	@Column(name = "community_provider", columnDefinition = "TEXT")
	private String communityProvider;

	@Column(name = "community_location", columnDefinition = "TEXT")
	private String communityLocation;

	@Column(name = "community_start_at")
	private LocalDateTime communityStartAt;

	@Column(name = "community_end_at")
	private LocalDateTime communityEndAt;

	@Column(name = "community_desc", columnDefinition = "TEXT")
	private String communityDesc;

	@Column(name = "community_code", columnDefinition = "TEXT")
	private String communityCode;

	@Column(name = "community_price")
	private BigDecimal communityPrice;

	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	@OneToOne
	@JoinColumn(name = "category_id")
	private CommunityCategory category;

	@OneToOne
	@JoinColumn(name = "industry_id")
	private Industry industry;

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getCommunityTitle() {
		return communityTitle;
	}

	public void setCommunityTitle(String communityTitle) {
		this.communityTitle = communityTitle;
	}

	public String getCommunityProvider() {
		return communityProvider;
	}

	public void setCommunityProvider(String communityProvider) {
		this.communityProvider = communityProvider;
	}

	public String getCommunityLocation() {
		return communityLocation;
	}

	public void setCommunityLocation(String communityLocation) {
		this.communityLocation = communityLocation;
	}

	public LocalDateTime getCommunityStartAt() {
		return communityStartAt;
	}

	public void setCommunityStartAt(LocalDateTime communityStartAt) {
		this.communityStartAt = communityStartAt;
	}

	public LocalDateTime getCommunityEndAt() {
		return communityEndAt;
	}

	public void setCommunityEndAt(LocalDateTime communityEndAt) {
		this.communityEndAt = communityEndAt;
	}

	public String getCommunityDesc() {
		return communityDesc;
	}

	public void setCommunityDesc(String communityDesc) {
		this.communityDesc = communityDesc;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public BigDecimal getCommunityPrice() {
		return communityPrice;
	}

	public void setCommunityPrice(BigDecimal communityPrice) {
		this.communityPrice = communityPrice;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public CommunityCategory getCategory() {
		return category;
	}

	public void setCategory(CommunityCategory category) {
		this.category = category;
	}

}
