package br.com.jovencio.projetofonocore.model.grid.options;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserGridOptions extends BaseGridOptions {

	private UUID id;
	private String name;
	private String document;
	private List<UUID> roleIds;

}
