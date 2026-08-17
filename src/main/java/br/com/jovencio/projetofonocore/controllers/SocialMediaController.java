package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.SocialMediaDTO;
import br.com.jovencio.projetofonocore.model.grid.options.SocialMediaGridOptions;
import br.com.jovencio.projetofonocore.services.SocialMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/social-media" )
@RestController
@RequiredArgsConstructor
public class SocialMediaController {

	private final SocialMediaService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.redes-sociais.view')" )
	public ResponseEntity<SearchReturnDTO<SocialMediaDTO>> search( @RequestBody SocialMediaGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.redes-sociais.view')" )
	public ResponseEntity<SocialMediaDTO> getById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.get( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.redes-sociais.form.new')" )
	public ResponseEntity<SocialMediaDTO> insert( @RequestBody SocialMediaDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.redes-sociais.form.edit')" )
	public ResponseEntity<SocialMediaDTO> update( @PathVariable long id, @RequestBody SocialMediaDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.redes-sociais.delete')" )
	public ResponseEntity<?> deleteById( @PathVariable Long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

}
