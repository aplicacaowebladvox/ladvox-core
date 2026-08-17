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

	public String getAuthority() {
		return this.code;
	}

}
