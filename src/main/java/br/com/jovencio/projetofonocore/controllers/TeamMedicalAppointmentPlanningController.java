package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.services.TeamMedicalAppointmentPlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping( "/team-medical-appointment-planning" )
@RestController
@RequiredArgsConstructor
public class TeamMedicalAppointmentPlanningController {

	private final TeamMedicalAppointmentPlanningService service;

	@GetMapping( "/select-options" )
	public ResponseEntity<List<SelectOptionExtraAttributeDTO<Long, String, String>>> getSelectOptions( @RequestParam Long medicalAppointmentPlanningId ) {
		return ResponseEntity.ok( service.getSelectOptions( medicalAppointmentPlanningId ) );
	}

}
