package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SelectOptionDTO<ID, CODE> {

	private ID id;
	private CODE name;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.IRoleRepository.queryAllForSelectOptions
	 * br.com.jovencio.projetofonocore.dao.repositories.IPermissionRepository.queryAllForSelectOptions
	 */
	public SelectOptionDTO( ID id, CODE name ) {
		this.id = id;
		this.name = name;
	}

	public static Class getClassOfUuidAndString() {
		return new SelectOptionDTO<UUID, String>( ( UUID ) null, ( String ) null ).getClass();
	}
}
