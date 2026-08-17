package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthInputRefreshTokenDTO {

	private String refreshToken;

}
