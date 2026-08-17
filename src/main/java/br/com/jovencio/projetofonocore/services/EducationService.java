package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IEducationRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IGenderRepository;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

	private final IEducationRepository repository;

	public List<SelectOptionExtraAttributeDTO<Long, String, Boolean>> getSelectOptions() {
		return repository.queryAllForSelectOptions();}
}
