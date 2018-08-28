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
	private String fullName;
	private String email;
	private Date arrivalDate;
	private Date departureDate;
}
