package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChangePasswordDTO {

	private String login;
	private String password;
	private LocalDate birthDate;

}
