package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IPermissionRepository;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionService {

	private final IPermissionRepository repository;

	public List<SelectOptionDTO<UUID, String>> getSelectOptions() {
		return repository.queryAllForSelectOptions();
	}
}
