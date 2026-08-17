package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.entities.Attachment;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TherapeuticPlanCommentChatViewDTO {

	private Long id;
	private Long therapeuticPlanId;
	private UUID userId;
	private String userName;
	private AttachmentDTO userProfileImage;
	private LocalDateTime createdDate;
	private String textComment;

	public TherapeuticPlanCommentChatViewDTO( Long id, Long therapeuticPlanId, UUID userId, String userName, Attachment userProfileImage, LocalDateTime createdDate, String textComment ) {
		this.id = id;
		this.therapeuticPlanId = therapeuticPlanId;
		this.userId = userId;
		this.userName = userName;
		this.userProfileImage = ModelMapperUtils.map( userProfileImage );
		this.createdDate = createdDate;
		this.textComment = textComment;
	}
}
