package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.PatientDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.grid.PatientGrid;
import br.com.jovencio.projetofonocore.model.grid.options.PatientGridOptions;
import br.com.jovencio.projetofonocore.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/patient" )
@RestController
@RequiredArgsConstructor
public class PatientController {

	private final PatientService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('paciente.paciente.view')" )
	public ResponseEntity<SearchReturnDTO<PatientGrid>> search( @RequestBody PatientGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('paciente.paciente.view')" )
	public ResponseEntity<PatientDTO> getById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.get( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('paciente.paciente.form.new')" )
	public ResponseEntity<PatientDTO> insert( @RequestBody PatientDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('paciente.paciente.form.edit')" )
	public ResponseEntity<PatientDTO> update( @PathVariable long id, @RequestBody PatientDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@GetMapping( "/select-options" )
	@PostAuthorize( "hasAnyAuthority('paciente.anamnese.view')" )
	public ResponseEntity<List<SelectOptionDTO<Long, String>>> getSelectOptions( @RequestParam( required = false ) Long id ) {
		return ResponseEntity.ok( service.getSelectOptions( id ) );
	}
}
