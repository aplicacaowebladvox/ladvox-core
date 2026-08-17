package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

	private String patientName;
	private String sessionHour;
	private String sessionRoom;
	private String therapistsTeamDescription;
}
