package br.com.jovencio.projetofonocore.model.auth.dtos;

import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChooseableUserRoleDTO {

	private String code;
	private String roleName;
	private List<String> otherRoles;
	private List<String> notifications;
	private String password;

	public ChooseableUserRoleDTO( Role role ) {
		this.code = role.isPatient() ? "patient" : "general";
		this.roleName = role.getName();
		this.otherRoles = new ArrayList<>();
		this.notifications = new ArrayList<>();
	}
}
