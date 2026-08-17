package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserMemberLandingPageDTO {

	private UUID id;
	private String name;
	private AttachmentDTO profileImage;
	private List<String> roles;

	public UserMemberLandingPageDTO( User user ) {
		this.id = user.getId();
		this.name = user.getName();
		this.profileImage = ModelMapperUtils.map( user.getProfileImage() );
		this.roles = user.getRoles().stream().filter( Role::isShowOnLandingPage ).map( Role::getName ).toList();
	}
}
