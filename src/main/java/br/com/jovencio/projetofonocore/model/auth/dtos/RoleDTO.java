package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoleDTO {

	private UUID id;
	private String name;
	private boolean showOnLandingPage;
	private List<PermissionDTO> permissions;

}
