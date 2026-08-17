package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoleResumedDTO {

	private UUID id;
	private String name;
	private boolean showOnLandingPage;
	private List<UUID> permissionsIds;
}
