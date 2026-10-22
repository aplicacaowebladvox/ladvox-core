package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Getter
@Setter
public class PermissionDTO implements GrantedAuthority {

	private UUID id;
	private String name;
	private String code;

	public static PermissionDTO fromRole( RoleDTO role ) {
		var permission = new PermissionDTO();
		permission.setId( role.getId() );
		permission.setCode( role.getName() );
		permission.setName( role.getName() );
		return permission;
	}

	public String getAuthority() {
		return this.code;
	}

}
