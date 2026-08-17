package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserMyDataDTO {

	private Long id;
	private String name;
	private String document;
	private String nationalHealthCard;
	private Long genderId;
	private String otherGenderDefinition;
	private Long educationId;
	private String otherEducationDefinition;
	private String profession;
	private String medicalRecord;
	private LocalDate birthDate;
	private String mainPhone;
	private String phoneSecundary;
	private String phoneObservation;
	private String mainMail;
	private String mailSecundary;
	private String mailObservation;
	private AddressDTO address;
	private UUID userId;
	private AttachmentDTO profileImage;

}
