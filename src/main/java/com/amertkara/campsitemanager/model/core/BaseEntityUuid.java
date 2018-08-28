package com.amertkara.campsitemanager.model.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntityUuid extends BaseEntity {
	@Column(unique = true, updatable = false)
	private String uuid;

	@PrePersist
	public void onPrePersist() {
		super.onPrePersist();
		if (uuid == null) {
			uuid = UUID.randomUUID().toString();
		}
	}
}
