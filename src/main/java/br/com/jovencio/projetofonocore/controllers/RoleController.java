package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.RoleResumedDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.grid.RoleGrid;
import br.com.jovencio.projetofonocore.model.grid.options.RoleGridOptions;
import br.com.jovencio.projetofonocore.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping( "/role" )
@RestController
@RequiredArgsConstructor
public class RoleController {

	private final RoleService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAnyAuthority('controle-acesso.papeis.view')" )
	public ResponseEntity<SearchReturnDTO<RoleGrid>> search( @RequestBody RoleGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('controle-acesso.papeis.view')" )
	public ResponseEntity<RoleResumedDTO> getById( @PathVariable UUID id ) {
		return ResponseEntity.ok( service.get( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('controle-acesso.papeis.form.new')" )
	public ResponseEntity<RoleResumedDTO> insert( @RequestBody RoleResumedDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('controle-acesso.papeis.form.edit')" )
	public ResponseEntity<RoleResumedDTO> update( @PathVariable UUID id, @RequestBody RoleResumedDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('controle-acesso.papeis.delete')" )
	public ResponseEntity<?> deleteById( @PathVariable UUID id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "/select-options" )
	@PostAuthorize( "hasAnyAuthority('controle-acesso.usuarios.view', 'controle-acesso.usuarios.form.new', 'controle-acesso.usuarios.form.edit')" )
	public ResponseEntity<List<SelectOptionDTO<UUID, String>>> getSelectOptions() {
		return ResponseEntity.ok( service.getSelectOptions() );
	}

}
