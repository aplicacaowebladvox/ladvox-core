package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.model.grid.ProtocolGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import br.com.jovencio.projetofonocore.services.ProtocolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/protocol" )
@RestController
@RequiredArgsConstructor
public class ProtocolController {

	private final ProtocolService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('protocolo.protocolo.view')" )
	public ResponseEntity<SearchReturnDTO<ProtocolGrid>> search( @RequestBody TermGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('protocolo.protocolo.view')" )
	public ResponseEntity<?> getById( @PathVariable Long id, @RequestParam Integer step ) {
		return ResponseEntity.ok( service.get( id, step ) );
	}

	@GetMapping( "/{id}/my-data" )
	@PostAuthorize( "hasAnyAuthority('paciente-view.paciente', 'protocolo.protocolo.form.new', 'protocolo.protocolo.form.edit')" )
	public ResponseEntity<MyProtocolDTO> getMyDataById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.getMyDataById( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('protocolo.protocolo.form.new')" )
	public ResponseEntity<ProtocolFirstStepDTO> insert( @RequestBody ProtocolFirstStepDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAnyAuthority('protocolo.protocolo.form.new', 'protocolo.protocolo.form.edit')" )
	public ResponseEntity<ProtocolFirstStepDTO> update( @PathVariable long id, @RequestBody ProtocolFirstStepDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@PutMapping( "/{id}/second" )
	@PostAuthorize( "hasAnyAuthority('protocolo.protocolo.form.new', 'protocolo.protocolo.form.edit')" )
	public ResponseEntity<ProtocolSecondStepDTO> updateSecond( @PathVariable long id, @RequestBody ProtocolSecondStepDTO dto ) {
		return ResponseEntity.ok( service.saveSecond( id, dto ) );
	}

	@PutMapping( "/{id}/third" )
	@PostAuthorize( "hasAnyAuthority('protocolo.protocolo.form.new', 'protocolo.protocolo.form.edit')" )
	public ResponseEntity<ProtocolThirdStepDTO> updateThird( @PathVariable long id, @RequestBody ProtocolThirdStepDTO dto ) {
		return ResponseEntity.ok( service.saveThird( id, dto ) );
	}

	@PutMapping( "/{id}/fourth" )
	@PostAuthorize( "hasAnyAuthority('protocolo.protocolo.form.new', 'protocolo.protocolo.form.edit')" )
	public ResponseEntity<ProtocolFourthStepDTO> updateFourth( @PathVariable long id, @RequestBody ProtocolFourthStepDTO dto ) {
		return ResponseEntity.ok( service.saveFourth( id, dto ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('protocolo.protocolo.delete')" )
	public ResponseEntity<?> deleteById( @PathVariable Long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "/select-options" )
	@PostAuthorize( "hasAnyAuthority('protocolo.protocolo.view', 'paciente.historico-protocolo.view')" )
	public ResponseEntity<List<SelectOptionDTO<Long, String>>> getSelectOptions() {
		return ResponseEntity.ok( service.getSelectOptions() );
	}

	@PostMapping( "/{id}/calculate" )
	public ResponseEntity<?> calculate( @PathVariable long id, @RequestBody List<AnswerQuestionProtocolDTO> answers ) {
		return ResponseEntity.ok( service.calculate( id, answers ) );
	}
}
