package br.com.jovencio.projetofonocore.model.grid;

import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class RoleGrid {

	private UUID id;
	private String name;
	private Long permissionsSize;
	private boolean canRemove;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.IRoleRepositoty.search
	 */
	public RoleGrid( Role role ) {
		this.id = role.getId();
		this.name = role.getName();
		this.permissionsSize = ( long ) Optional.ofNullable( role.getPermissions() ).orElse( Collections.emptyList() ).size();
		this.canRemove = role.getUsers() == null || role.getUsers().isEmpty();
	}
}
