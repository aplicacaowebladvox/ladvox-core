package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping( "/gender" )
@RestController
@RequiredArgsConstructor
public class GenderController {

	private final GenderService service;

	@GetMapping( "/select-options" )
	public ResponseEntity<List<SelectOptionExtraAttributeDTO<Long, String, Boolean>>> getSelectOptions() {
		return ResponseEntity.ok( service.getSelectOptions() );
	}
}
