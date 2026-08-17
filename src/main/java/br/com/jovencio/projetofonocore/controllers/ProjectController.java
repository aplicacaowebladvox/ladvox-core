package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.ProjectDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.grid.ProjectGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import br.com.jovencio.projetofonocore.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/project" )
@RestController
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.projetos.view')" )
	public ResponseEntity<SearchReturnDTO<ProjectGrid>> search( @RequestBody TermGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.projetos.view')" )
	public ResponseEntity<ProjectDTO> getById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.get( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.projetos.form.new')" )
	public ResponseEntity<ProjectDTO> insert( @RequestBody ProjectDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.projetos.form.edit')" )
	public ResponseEntity<ProjectDTO> update( @PathVariable long id, @RequestBody ProjectDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.projetos.delete')" )
	public ResponseEntity<?> deleteById( @PathVariable Long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

}
