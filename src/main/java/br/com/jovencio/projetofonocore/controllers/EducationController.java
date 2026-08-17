package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.services.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping( "/education" )
@RestController
@RequiredArgsConstructor
public class EducationController {

	private final EducationService service;

	@GetMapping( "/select-options" )
	public ResponseEntity<List<SelectOptionExtraAttributeDTO<Long, String, Boolean>>> getSelectOptions() {
		return ResponseEntity.ok( service.getSelectOptions() );
	}
}
