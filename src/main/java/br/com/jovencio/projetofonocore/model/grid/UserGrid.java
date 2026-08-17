package br.com.jovencio.projetofonocore.model.grid;

import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class UserGrid {

	private UUID id;
	private String name;
	private String document;
	private LocalDateTime inactivationDate;
	private UUID inacinactivationUserId;
	private String inacinactivationUserName;
	private List<String> roles;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.IUserRepositoty.search
	 */
	public UserGrid(
			User user
	) {
		this.id = user.getId();
		this.name = user.getName();
		this.document = user.getDocument();
		this.roles = Optional.ofNullable( user.getRoles() ).orElse( Collections.emptyList() ).stream().map( Role::getName ).toList();
		this.inacinactivationUserId = user.getInacinactivationUser() != null ? user.getInacinactivationUser().getId() : null;
		this.inactivationDate = user.getInactivationDate() != null ? user.getInactivationDate() : null;
		this.inacinactivationUserName = user.getInacinactivationUser() != null ? user.getInacinactivationUser().getName() : null;
	}
}
