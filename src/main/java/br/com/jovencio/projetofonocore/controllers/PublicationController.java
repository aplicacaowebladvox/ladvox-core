package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.PublicationDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.grid.PublicationGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import br.com.jovencio.projetofonocore.services.ProjectService;
import br.com.jovencio.projetofonocore.services.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/publication" )
@RestController
@RequiredArgsConstructor
public class PublicationController {

	private final PublicationService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.publicacoes.view')" )
	public ResponseEntity<SearchReturnDTO<PublicationGrid>> search( @RequestBody TermGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.publicacoes.view')" )
	public ResponseEntity<PublicationDTO> getById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.get( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.publicacoes.form.new')" )
	public ResponseEntity<PublicationDTO> insert( @RequestBody PublicationDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.publicacoes.form.edit')" )
	public ResponseEntity<PublicationDTO> update( @PathVariable long id, @RequestBody PublicationDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.publicacoes.delete')" )
	public ResponseEntity<?> deleteById( @PathVariable Long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}
}
