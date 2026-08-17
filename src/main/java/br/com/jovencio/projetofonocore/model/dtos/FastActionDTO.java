package br.com.jovencio.projetofonocore.model.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FastActionDTO {

	private List<MenuItemDTO> fixedActions;
	private List<MenuItemDTO> suggestedActions;
}
