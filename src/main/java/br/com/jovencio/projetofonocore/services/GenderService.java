package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IGenderRepository;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderService {

	private final IGenderRepository repository;

	public List<SelectOptionExtraAttributeDTO<Long, String, Boolean>> getSelectOptions() {
		return repository.queryAllForSelectOptions();
	}
}
