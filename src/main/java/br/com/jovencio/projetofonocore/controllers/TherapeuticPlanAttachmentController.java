package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.AttachmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanAttachmentDTO;
import br.com.jovencio.projetofonocore.services.TherapeuticPlanAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/therapeutic-plan-attachment" )
@RestController
@RequiredArgsConstructor
public class TherapeuticPlanAttachmentController {

	private final TherapeuticPlanAttachmentService service;

	@GetMapping( "/therapeutic-plan/{therapeuticPlanId}" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.view')" )
	public ResponseEntity<List<TherapeuticPlanAttachmentDTO>> getAllBy( @PathVariable long therapeuticPlanId ) {
		return ResponseEntity.ok( service.findAllBy( therapeuticPlanId ) );
	}

	@PostMapping( "/therapeutic-plan/{therapeuticPlanId}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.plano-terapeutico.form', 'atendimento.plano-terapeutico.form.edit', 'atendimento.plano-terapeutico.form.new')" )
	public ResponseEntity<TherapeuticPlanAttachmentDTO> saveNewForTherapeuticPlan( @PathVariable long therapeuticPlanId, @RequestBody AttachmentDTO attachment ) {
		return ResponseEntity.ok( service.saveNewForTherapeuticPlan( therapeuticPlanId, attachment ) );
	}

	@DeleteMapping( "/attachment/{id}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.plano-terapeutico.form', 'atendimento.plano-terapeutico.form.edit', 'atendimento.plano-terapeutico.form.new')" )
	public ResponseEntity<?> deleteByAttachment( @PathVariable long id ) {
		service.deleteByAttachment( id );
		return ResponseEntity.ok().build();
	}

}
