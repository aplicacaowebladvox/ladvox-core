package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.auth.dtos.ChooseableUserRoleDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserResumedDTO;
import br.com.jovencio.projetofonocore.model.dtos.AttachmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.dtos.UserMyDataDTO;
import br.com.jovencio.projetofonocore.model.grid.UserGrid;
import br.com.jovencio.projetofonocore.model.grid.options.UserGridOptions;
import br.com.jovencio.projetofonocore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping( "/user" )
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService service;

	@GetMapping( "/chooseable-user-roles" )
	public ResponseEntity<List<ChooseableUserRoleDTO>> mountChooseableRole() {
		return ResponseEntity.ok( service.mountChooseableRole() );
	}

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('controle-acesso.usuarios.view')" )
	public ResponseEntity<SearchReturnDTO<UserGrid>> search( @RequestBody UserGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('controle-acesso.usuarios.view')" )
	public ResponseEntity<UserResumedDTO> getById( @PathVariable UUID id ) {
		return ResponseEntity.ok( service.getBy( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('controle-acesso.usuarios.form.new')" )
	public ResponseEntity<UserResumedDTO> insert( @RequestBody UserResumedDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('controle-acesso.usuarios.form.edit')" )
	public ResponseEntity<UserResumedDTO> update( @PathVariable UUID id, @RequestBody UserResumedDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('controle-acesso.usuarios.delete')" )
	public ResponseEntity<?> deleteById( @PathVariable UUID id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "/select-options" )
	@PostAuthorize( "hasAnyAuthority('paciente.anamnese.view')" )
	public ResponseEntity<List<SelectOptionDTO<UUID, String>>> getSelectOptions( @RequestParam( required = false ) boolean therapists ) {
		return ResponseEntity.ok( service.getSelectOptions( therapists ) );
	}

	@GetMapping( "/data" )
	public ResponseEntity<UserMyDataDTO> getDataOfLoggedUser() {
		return ResponseEntity.ok( service.getDataOfLoggedUser() );
	}

	@PatchMapping( "{id}/data" )
	public ResponseEntity<UserMyDataDTO> updateBaseData(@PathVariable UUID id, @RequestBody UserResumedDTO dto ) {
		return ResponseEntity.ok( service.updateBaseData(id, dto) );
	}

	@PostMapping( "/{id}/profile-image" )
	public ResponseEntity<AttachmentDTO> saveProfileImage( @PathVariable UUID id, @RequestBody AttachmentDTO dto ) {
		return ResponseEntity.ok( service.saveProfileImage( id, dto ) );
	}

	@GetMapping( "/profile-image" )
	public ResponseEntity<AttachmentDTO> getProfileImage() {
		return ResponseEntity.ok( service.getProfileImage() );
	}

}
