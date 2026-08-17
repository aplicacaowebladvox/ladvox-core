package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDTO {

	private String login;
	private String password;
}
