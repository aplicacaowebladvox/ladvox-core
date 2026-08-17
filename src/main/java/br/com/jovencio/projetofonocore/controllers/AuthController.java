package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.auth.dtos.*;
import br.com.jovencio.projetofonocore.services.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/auth" )
@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController( AuthService authenticationService ) {
		this.authService = authenticationService;
	}

	@PostMapping( "/token" )
	public ResponseEntity<TokenResponse> token( @RequestBody LoginUserDTO loginUserDto ) {
		return ResponseEntity.ok( authService.token( loginUserDto ) );
	}

	@PostMapping( "/view-mode-token" )
	public ResponseEntity<TokenResponse> viewModeToken( @RequestBody ChooseableUserRoleDTO chooseableUserRole ) {
		return ResponseEntity.ok( authService.viewModeToken( chooseableUserRole ) );
	}

	@PostMapping( "/check-token" )
	public ResponseEntity<TokenResponse> checkToken( @RequestBody AuthInputRefreshTokenDTO authInputRefreshTokenDTO ) {
		return ResponseEntity.ok( authService.checkToken( authInputRefreshTokenDTO.getRefreshToken() ) );
	}

	@GetMapping( "/check-token" )
	public ResponseEntity<TokenResponse> getCheckToken( @RequestHeader( "Authorization" ) String token ) {
		token = token.startsWith( "Bearer " ) ? token.substring( 7 ) : token;
		return ResponseEntity.ok( authService.checkToken( token ) );
	}

	@PostMapping( "/refresh-token" )
	public ResponseEntity<TokenResponse> refreshToken( @RequestBody AuthInputRefreshTokenDTO authInputRefreshTokenDTO ) {
		return ResponseEntity.ok( authService.refreshToken( authInputRefreshTokenDTO ) );
	}

	@PostMapping( "/change-password" )
	public ResponseEntity<?> changePassword( @RequestBody ChangePasswordDTO changePasswordDTO ) {
		authService.changePassword( changePasswordDTO );
		return ResponseEntity.ok().build();
	}

}
