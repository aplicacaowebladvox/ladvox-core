package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanBaseDataDTO;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanDTO;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticTextPlanDTO;
import br.com.jovencio.projetofonocore.model.grid.TherapeuticPlanGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TherapeuticPlanGridOptions;
import br.com.jovencio.projetofonocore.services.TherapeuticPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping( "/therapeutic-plan" )
@RestController
@RequiredArgsConstructor
public class TherapeuticPlanController {

	private final TherapeuticPlanService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.view')" )
	public ResponseEntity<SearchReturnDTO<TherapeuticPlanGrid>> search( @RequestBody TherapeuticPlanGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@PatchMapping( "/take-over-as-editor" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.assumir-como-editor')" )
	public ResponseEntity<TherapeuticPlanDTO> takeOverAsEditor( @RequestParam( required = false ) Long therapeuticPlanId, @RequestParam Long medicalAppointmentId ) {
		return ResponseEntity.ok( service.takeOverAsEditor( therapeuticPlanId, medicalAppointmentId ) );
	}

	@PatchMapping( "/{id}/resign-as-editor" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.assumir-como-editor')" )
	public ResponseEntity<?> resignAsEditor( @PathVariable long id ) {
		service.resignAsEditor( id );
		return ResponseEntity.ok().build();
	}

	@PatchMapping( "/change-editor" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.alterar-editor')" )
	public ResponseEntity<?> changeCurrentEditor( @RequestParam( required = false ) Long therapeuticPlanId, @RequestParam Long medicalAppointmentId, @RequestParam UUID newUserEditorId ) {
		service.changeCurrentEditor( therapeuticPlanId, medicalAppointmentId, newUserEditorId );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "/therapists" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.alterar-editor')" )
	public ResponseEntity<?> loadTherapists( @RequestParam( required = false ) Long therapeuticPlanId, @RequestParam Long medicalAppointmentId ) {
		return ResponseEntity.ok( service.loadTherapists( therapeuticPlanId, medicalAppointmentId ) );
	}

	@PatchMapping( "/find-generate" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.view')" )
	public ResponseEntity<TherapeuticPlanDTO> findOrGenerate( @RequestParam( required = false ) Long therapeuticPlanId, @RequestParam Long medicalAppointmentId ) {
		return ResponseEntity.ok( service.findOrGenerate( therapeuticPlanId, medicalAppointmentId ) );
	}

	@GetMapping( "/{id}/text" )
	@PostAuthorize( "hasAnyAuthority('atendimento.plano-terapeutico.view')" )
	public ResponseEntity<TherapeuticTextPlanDTO> getTherapeuticTextPlan( @PathVariable long id ) {
		return ResponseEntity.ok( service.getTherapeuticTextPlan( id ) );
	}

	@PatchMapping( "/{id}/text" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.form')" )
	public ResponseEntity<?> updateTherapeuticTextPlan( @PathVariable long id, @RequestBody TherapeuticTextPlanDTO dto ) {
		service.updateTherapeuticTextPlan( id, dto );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "/{id}/base-data" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.view')" )
	public ResponseEntity<TherapeuticPlanBaseDataDTO> getTherapeuticPlanBaseData( @PathVariable long id ) {
		return ResponseEntity.ok( service.getTherapeuticPlanBaseData( id ) );
	}

	//	@PostMapping( "/" )
	//	@PostAuthorize( "hasAuthority('configuracao-sistema.projetos.form.new')" )
	//	public ResponseEntity<TherapeuticPlanDTO> insert( @RequestBody TherapeuticPlanDTO dto ) {
	//		return ResponseEntity.ok( service.save( dto ) );
	//	}
	//
	//	@PutMapping( "/{id}" )
	//	@PostAuthorize( "hasAuthority('configuracao-sistema.projetos.form.edit')" )
	//	public ResponseEntity<TherapeuticPlanDTO> update( @PathVariable long id, @RequestBody TherapeuticPlanDTO dto ) {
	//		return ResponseEntity.ok( service.save( id, dto ) );
	//	}
}
