package com.amertkara.campsitemanager.model.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
	@Column(updatable = false)
	private Date creationDate;
	@Column(name = "update_date")
	private Date lastUpdateDate;

	@PrePersist
	public void onPrePersist() {
		this.creationDate = new Date();
	}

	@PreUpdate
	public void onPreUpdate() {
		this.lastUpdateDate = new Date();
	}
}
