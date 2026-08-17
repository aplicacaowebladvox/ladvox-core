package br.com.jovencio.projetofonocore.model.grid.options;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermGridOptions extends BaseGridOptions {

	private Long id;
	private String term;

}
