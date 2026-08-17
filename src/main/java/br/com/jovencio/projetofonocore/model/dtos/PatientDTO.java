package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

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
	private List<Long> anamnesisIds;
	private UUID userId;

}
