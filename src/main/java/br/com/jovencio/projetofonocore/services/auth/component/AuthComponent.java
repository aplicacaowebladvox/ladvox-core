package br.com.jovencio.projetofonocore.services.auth.component;

import br.com.jovencio.projetofonocore.model.auth.dtos.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component( "authComponent" )
@RequiredArgsConstructor
public class AuthComponent {

	public Optional<Authentication> getOptionalAuthentication() {
		return Optional.ofNullable( SecurityContextHolder.getContext().getAuthentication() );
	}

	public Authentication getAuthentication() {
		return Optional.ofNullable( SecurityContextHolder.getContext().getAuthentication() ).orElseThrow();
	}

	public Optional<UserDTO> getOptionalUserDTO() {
		return getOptionalAuthentication().map( Authentication::getPrincipal ).map( p -> ( UserDTO ) p );
	}

	public UserDTO getUserDTO() {
		return getOptionalUserDTO().orElse( null );
	}

}
