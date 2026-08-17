package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.AnamnesisPatientDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.grid.AnamnesisPatientGrid;
import br.com.jovencio.projetofonocore.model.grid.options.AnamnesisPatientGridOptions;
import br.com.jovencio.projetofonocore.services.AnamnesisPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/anamnesis-patient" )
@RestController
@RequiredArgsConstructor
public class AnamnesisPatientController {

	private final AnamnesisPatientService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('paciente.anamnese.view')" )
	public ResponseEntity<SearchReturnDTO<AnamnesisPatientGrid>> search( @RequestBody AnamnesisPatientGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('paciente.anamnese.view')" )
	public ResponseEntity<AnamnesisPatientDTO> getById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.get( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('paciente.anamnese.form.new')" )
	public ResponseEntity<AnamnesisPatientDTO> insert( @RequestBody AnamnesisPatientDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('paciente.anamnese.form.edit')" )
	public ResponseEntity<AnamnesisPatientDTO> update( @PathVariable long id, @RequestBody AnamnesisPatientDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

}
