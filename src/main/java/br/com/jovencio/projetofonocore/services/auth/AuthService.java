package br.com.jovencio.projetofonocore.services.auth;

import br.com.jovencio.projetofonocore.dao.repositories.IRoleRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserRepository;
import br.com.jovencio.projetofonocore.model.auth.dtos.*;
import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import br.com.jovencio.projetofonocore.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final IUserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final AuthComponent authComponent;
	private final IRoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public TokenResponse token( LoginUserDTO input ) {
		authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( input.getLogin(), input.getPassword() ) );
		var userEntity = userRepository.findByLogin( input.getLogin() ).orElseThrow();
		var roles = userEntity.getRoles().stream().filter( role -> !role.isPatient() ).toList();
		if( !roles.isEmpty() ) {
			userEntity.setRoles( roles );
		}
		var user = ModelMapperUtils.map( userEntity );
		if( !user.isEnabled() ) {
			throw new IllegalStateException( "User not enable" );
		}
		var accessToken = jwtService.generateAccessToken( user );
		var refreshToken = jwtService.generateRefreshToken( user );
		return TokenResponse.builder().access_token( accessToken ).expires_in( jwtService.getExpirationTime() ).refresh_token( refreshToken ).refresh_expires_in( jwtService.getRefreshTokenExpirationTime() ).build();
	}

	public TokenResponse viewModeToken( ChooseableUserRoleDTO chooseableUserRole ) {
		var userEntity = userRepository.findById( authComponent.getUserDTO().getId() ).orElseThrow();
		if( userEntity.getInactivationDate() != null ) {
			throw new IllegalStateException( "User not enable" );
		}
		if( chooseableUserRole.getCode().equalsIgnoreCase( "patient" ) ) {
			userEntity.setRoles( userEntity.getRoles().stream().filter( Role::isPatient ).toList() );
		} else {
			userEntity.setRoles( userEntity.getRoles().stream().filter( role -> !role.isPatient() && ( role.getName().equals( chooseableUserRole.getRoleName() ) || chooseableUserRole.getOtherRoles().contains( role.getName() ) ) ).toList() );
		}
		var user = ModelMapperUtils.map( userEntity );
		authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( user.getUsername(), chooseableUserRole.getPassword(), user.getAuthorities() ) );
		var accessToken = jwtService.generateAccessToken( user );
		var refreshToken = jwtService.generateRefreshToken( user );
		return TokenResponse.builder().access_token( accessToken ).expires_in( jwtService.getExpirationTime() ).refresh_token( refreshToken ).refresh_expires_in( jwtService.getRefreshTokenExpirationTime() ).build();
	}

	public TokenResponse checkToken( String token ) {
		var subject = jwtService.extractSubject( token );
		var userEntity = userRepository.findById( UUID.fromString( subject ) ).orElseThrow();
		if( jwtService.isTokenExpired( token ) ) {
			throw new IllegalStateException( "Token is expired" );
		} else if( userEntity.getInactivationDate() != null ) {
			throw new IllegalStateException( "User not enable" );
		}
		var extractedRoles = jwtService.extractRoles( token );
		var roles = userEntity.getRoles().stream().filter( role -> extractedRoles.contains( role.getName() ) ).toList();
		userEntity.setRoles( roles );
		var user = ModelMapperUtils.map( userEntity );
		var accessToken = jwtService.generateAccessToken( user );
		var refreshToken = jwtService.generateRefreshToken( user );
		return TokenResponse.builder().access_token( accessToken ).expires_in( jwtService.getExpirationTime() ).refresh_token( refreshToken ).refresh_expires_in( jwtService.getRefreshTokenExpirationTime() ).build();
	}

	public TokenResponse refreshToken( AuthInputRefreshTokenDTO authInputRefreshTokenDTO ) {
		return checkToken( authInputRefreshTokenDTO.getRefreshToken() );
	}

	public void changePassword( ChangePasswordDTO changePasswordDTO ) {
		var user = userRepository.findByLogin( changePasswordDTO.getLogin() ).orElseThrow();
		if( user.getInactivationDate() != null ) {
			throw new IllegalStateException( "Usuário está desativado" );
		}
		if( !user.getBirthDate().equals( changePasswordDTO.getBirthDate() ) ) {
			throw new IllegalStateException( "Data de nascimento inválida" );
		}
		if( !ValidationUtils.password( changePasswordDTO.getPassword() ) ) {
			throw new IllegalStateException( "Senha inválida" );
		}
		user.setPassword( passwordEncoder.encode( changePasswordDTO.getPassword() ) );
		userRepository.save( user );
	}
}
