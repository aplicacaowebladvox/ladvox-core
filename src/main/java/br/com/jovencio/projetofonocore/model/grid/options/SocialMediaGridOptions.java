package br.com.jovencio.projetofonocore.model.grid.options;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialMediaGridOptions extends BaseGridOptions {

	private Long id;
	private String iconClass;
	private String title;

}
