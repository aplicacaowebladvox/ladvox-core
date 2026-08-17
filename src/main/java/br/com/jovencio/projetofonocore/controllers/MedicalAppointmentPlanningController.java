package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.model.grid.MedicalAppointmentPlanningGrid;
import br.com.jovencio.projetofonocore.model.grid.options.MedicalAppointmentPlanningGridOptions;
import br.com.jovencio.projetofonocore.services.MedicalAppointmentPlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/medical-appointment-planning" )
@RestController
@RequiredArgsConstructor
public class MedicalAppointmentPlanningController {

	private final MedicalAppointmentPlanningService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('atendimento.planejamento-atendimento.view')" )
	public ResponseEntity<SearchReturnDTO<MedicalAppointmentPlanningGrid>> search( @RequestBody MedicalAppointmentPlanningGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('atendimento.planejamento-atendimento.view')" )
	public ResponseEntity<?> getById( @PathVariable Long id, @RequestParam Integer step ) {
		return ResponseEntity.ok( service.get( id, step ) );
	}

	@DeleteMapping( "/{id}/team-medical-appointment-planning/{teamMedicalAppointmentPlanningId}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<?> deleteTeamMedicalAppointmentPlanning( @PathVariable long id, @PathVariable long teamMedicalAppointmentPlanningId ) {
		service.deleteTeamMedicalAppointmentPlanning( id, teamMedicalAppointmentPlanningId );
		return ResponseEntity.ok().build();
	}

	@DeleteMapping( "/{id}/team-medical-appointment-planning/{medicalAppointmentId}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<?> deleteMedicalAppointment( @PathVariable long id, @PathVariable long medicalAppointmentId ) {
		service.deleteMedicalAppointment( id, medicalAppointmentId );
		return ResponseEntity.ok().build();
	}

	@PostMapping( "/" )
	@PostAuthorize( "hasAuthority('atendimento.planejamento-atendimento.form.new')" )
	public ResponseEntity<MedicalAppointmentPlanningFirstStepDTO> insert( @RequestBody MedicalAppointmentPlanningFirstStepDTO dto ) {
		return ResponseEntity.ok( service.save( dto ) );
	}

	@PutMapping( "/{id}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<MedicalAppointmentPlanningFirstStepDTO> update( @PathVariable long id, @RequestBody MedicalAppointmentPlanningFirstStepDTO dto ) {
		return ResponseEntity.ok( service.save( id, dto ) );
	}

	@PutMapping( "/{id}/second" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<MedicalAppointmentPlanningSecondStepDTO> updateSecond( @PathVariable long id, @RequestBody MedicalAppointmentPlanningSecondStepDTO dto ) {
		return ResponseEntity.ok( service.saveSecond( id, dto ) );
	}

	@PutMapping( "/{id}/third" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<MedicalAppointmentPlanningThirdStepDTO> updateThird( @PathVariable long id, @RequestBody MedicalAppointmentPlanningThirdStepDTO dto ) {
		return ResponseEntity.ok( service.saveThird( id, dto ) );
	}

	@GetMapping( "/{id}/medical-appointment-planning-attachment/attachment" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.view', 'atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<List<AttachmentDTO>> findAllAttachmentsOfMedicalAppointmentPlanning( @PathVariable long id ) {
		return ResponseEntity.ok( service.findAllAttachmentsOfMedicalAppointmentPlanning( id ) );
	}

	@PostMapping( "/{id}/medical-appointment-planning-attachment/attachment" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<AttachmentDTO> saveAttachmentForMedicalAppointmentPlanning( @PathVariable long id, @RequestBody AttachmentDTO dto ) {
		return ResponseEntity.ok( service.saveAttachmentForMedicalAppointmentPlanning( id, dto ) );
	}

	@DeleteMapping( "/medical-appointment-planning-attachment/attachment/{attachmentId}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.planejamento-atendimento.form.new', 'atendimento.planejamento-atendimento.form.edit')" )
	public ResponseEntity<?> deleteMedicalAppointment( @PathVariable long attachmentId ) {
		service.deleteAttachment( attachmentId );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "/select-options" )
	public ResponseEntity<List<SelectOptionDTO<Long, String>>> getSelectOptions() {
		return ResponseEntity.ok( service.getSelectOptions() );
	}
}
