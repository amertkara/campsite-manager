package com.amertkara.campsitemanager.model;

import com.amertkara.campsitemanager.model.core.BaseEntityUuid;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntityUuid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String fullName;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private Date arrivalDate;
	@Column(nullable = false)
	private Date departureDate;
	@Column(nullable = false)
	private Long duration;
}
