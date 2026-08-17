package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectOptionExtraAttributeDTO<ID, CODE, EXTRA> {

	private ID id;
	private CODE name;
	private EXTRA extraAttribute;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.IRoleRepository.queryAllForSelectOptions
	 */
	public SelectOptionExtraAttributeDTO( ID id, CODE name, EXTRA extraAttribute ) {
		this.id = id;
		this.name = name;
		this.extraAttribute = extraAttribute;
	}

}
