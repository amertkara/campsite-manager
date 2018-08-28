package com.amertkara.campsitemanager.controller.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class CampsiteDTO {
	private boolean isAvailable;
	private Long totalSpace;
	private Long emptySpace;
}
