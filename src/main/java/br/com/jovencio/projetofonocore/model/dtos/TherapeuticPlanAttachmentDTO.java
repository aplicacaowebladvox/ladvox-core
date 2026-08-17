package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TherapeuticPlanAttachmentDTO {

	private Long id;
	private Long therapeuticPlanId;
	private AttachmentDTO attachment;
}
