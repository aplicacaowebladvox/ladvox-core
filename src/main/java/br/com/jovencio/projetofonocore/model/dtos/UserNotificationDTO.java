package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDTO {

	private Long id;
	private UUID userId;
	private String notification;
	private LocalDateTime createdDate;
	private boolean markAsRead;
	private boolean forPatient;
}
