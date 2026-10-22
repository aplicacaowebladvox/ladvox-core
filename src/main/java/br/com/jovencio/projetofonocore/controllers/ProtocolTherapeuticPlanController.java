package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.ProtocolTherapeuticPlanDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.grid.MyProtocolGrid;
import br.com.jovencio.projetofonocore.model.grid.ProtocolTherapeuticPlanGrid;
import br.com.jovencio.projetofonocore.model.grid.options.MyProtocolGridOptions;
import br.com.jovencio.projetofonocore.model.grid.options.ProtocolTherapeuticPlanGridOptions;
import br.com.jovencio.projetofonocore.services.ProtocolTherapeuticPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/protocol-therapeutic-plan" )
@RestController
@RequiredArgsConstructor
public class ProtocolTherapeuticPlanController {

	private final ProtocolTherapeuticPlanService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAnyAuthority('protocolo.protocolo.view', 'paciente.historico-protocolo.view')" )
	public ResponseEntity<SearchReturnDTO<ProtocolTherapeuticPlanGrid>> search( @RequestBody ProtocolTherapeuticPlanGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@PostMapping( "/search-my-protocols" )
	@PostAuthorize( "hasAnyAuthority('Paciente', 'paciente-view.paciente')" )
	public ResponseEntity<SearchReturnDTO<MyProtocolGrid>> searchMyProtocols( @RequestBody MyProtocolGridOptions options ) {
		return ResponseEntity.ok( service.searchMyProtocols( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAnyAuthority('Paciente', 'paciente-view.paciente', 'paciente.historico-protocolo.view', 'atendimento.plano-terapeutico.protocolo-repondido-view')" )
	public ResponseEntity<ProtocolTherapeuticPlanDTO> getById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.get( id ) );
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.form.novo-protocolo')" )
	public ResponseEntity<ProtocolTherapeuticPlanDTO> insert( @RequestBody ProtocolTherapeuticPlanDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAnyAuthority('paciente.historico-protocolo.form.novo-protocolo')" )
	public ResponseEntity<ProtocolTherapeuticPlanDTO> delete( @PathVariable Long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

	@PatchMapping( "/{id}/answers" )
	@PostAuthorize( "hasAnyAuthority('Paciente', 'paciente-view.paciente')" )
	public ResponseEntity<?> saveAnswers( @PathVariable Long id, @RequestBody ProtocolTherapeuticPlanDTO dto ) {
		service.saveAnswers( id, dto );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "{id}/result" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.protocolo-repondido-view')" )
	public ResponseEntity<?> findResult( @PathVariable long id ) {
		return ResponseEntity.ok( service.findResults( id ) );
	}

}
