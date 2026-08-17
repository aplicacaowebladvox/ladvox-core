package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserResumedDTO {

	private UUID id;
	private String name;
	private String document;
	private LocalDate birthDate;
	private boolean showOnLandingPage;
	private List<UUID> roleIds;
	private LocalDateTime inactivationDate;
	private UUID inacinactivationUserId;

}
