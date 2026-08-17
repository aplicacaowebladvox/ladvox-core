package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.AnamnesisPatientAttachmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.AttachmentDTO;
import br.com.jovencio.projetofonocore.services.AnamnesisPatientAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/anamnesis-patient-attachment" )
@RestController
@RequiredArgsConstructor
public class AnamnesisPatientAttachmentController {

	private final AnamnesisPatientAttachmentService service;

	@GetMapping( "/anamnesis-patient/{anamnesisPatientId}" )
	@PostAuthorize( "hasAuthority('paciente.anamnese.view')" )
	public ResponseEntity<List<AnamnesisPatientAttachmentDTO>> getAllBy( @PathVariable Long anamnesisPatientId ) {
		return ResponseEntity.ok( service.findAllBy( anamnesisPatientId ) );
	}

	@PostMapping( "/anamnesis-patient/{anamnesisPatientId}" )
	@PostAuthorize( "hasAnyAuthority('paciente.anamnese.form.edit', 'paciente.anamnese.form.new')" )
	public ResponseEntity<AnamnesisPatientAttachmentDTO> saveNewForAnamnesisPatient( @PathVariable long anamnesisPatientId, @RequestBody AttachmentDTO attachment ) {
		return ResponseEntity.ok( service.saveNewForAnamnesisPatient( anamnesisPatientId, attachment ) );
	}

	@PostMapping( "/patient/{patientId}" )
	@PostAuthorize( "hasAnyAuthority('paciente.anamnese.form.edit', 'paciente.anamnese.form.new')" )
	public ResponseEntity<AnamnesisPatientAttachmentDTO> saveNewForPatient( @PathVariable long patientId, @RequestBody AttachmentDTO attachment ) {
		return ResponseEntity.ok( service.saveNewForPatient( patientId, attachment ) );
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAnyAuthority('paciente.anamnese.form.edit', 'paciente.anamnese.form.new')" )
	public ResponseEntity<?> delete( @PathVariable long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

}
