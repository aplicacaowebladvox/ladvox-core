package br.com.jovencio.projetofonocore.model.grid.options;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MyProtocolGridOptions extends BaseGridOptions {
	private UUID userId;
	private boolean notAnswered;
	private boolean answered;
}
