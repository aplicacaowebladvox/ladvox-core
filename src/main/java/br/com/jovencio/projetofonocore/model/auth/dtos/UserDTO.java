package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class UserDTO implements UserDetails {

	private UUID id;

	private String name;

	private String login;

	private String password;

	private String document;

	private boolean showOnLandingPage;

	private List<RoleDTO> roles;

	private LocalDateTime inactivationDate;

	private UUID inacinactivationUserId;

	@Override
	public List<PermissionDTO> getAuthorities() {
		return roles.stream().flatMap( role -> Stream.concat( Stream.of( PermissionDTO.fromRole( role ) ), role.getPermissions().stream() ) ).collect( Collectors.toSet() ).stream().toList();
	}

	@Override
	public String getUsername() {
		return Optional.ofNullable( login ).orElse( document );
	}

	@Override
	public boolean isEnabled() {
		return this.inactivationDate == null;
	}

}
