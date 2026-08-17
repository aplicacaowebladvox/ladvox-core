package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseFilterGridOptions {

	private String field;
	private String typeOrder;
	private int orderPriority = 0;

	public String getTypeOrderSql() {
		return "DESC".equalsIgnoreCase( typeOrder ) ? "DESC" : "ASC";
	}
}
