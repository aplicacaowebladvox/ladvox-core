package br.com.jovencio.projetofonocore.model.grid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PatientGrid {

	private Long id;
	private String name;
	private String document;
	private LocalDate birthDate;
	private String age;

	public PatientGrid( Long id, String name, String document, LocalDate birthDate, String age ) {
		this.id = id;
		this.name = name;
		this.document = document;
		this.birthDate = birthDate;
		this.age = age;

	}
}
