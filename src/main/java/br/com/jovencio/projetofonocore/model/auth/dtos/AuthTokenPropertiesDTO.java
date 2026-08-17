package br.com.jovencio.projetofonocore.model.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenPropertiesDTO {

	private String typ;
	private String azp;
	private String session_state;
	private List<String> roles;
	private List<String> permissions;
	private String login;
	private String name;
	private String uid;
	private long exp;
	private String sub;
	private String aud;
}
