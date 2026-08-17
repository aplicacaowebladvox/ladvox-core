package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchReturnDTO<T> {

	private List<T> registers;
	private Long totalRegisters;

	public SearchReturnDTO() {
		this.registers = new ArrayList<>();
		this.totalRegisters = 0L;
	}

}
