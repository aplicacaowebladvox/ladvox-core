package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenResponse {

	private String access_token;
	private long expires_in;
	private String refresh_token;
	private long refresh_expires_in;

}
