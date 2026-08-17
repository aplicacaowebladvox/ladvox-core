package br.com.jovencio.projetofonocore.model.auth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table( name = "PERMISSIONS" )
public class Permission implements GrantedAuthority {

	@Id
	@GeneratedValue( strategy = GenerationType.UUID )
	@Column( name = "ID_PERMISSION", columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false )
	private UUID id;

	@Column( name = "NM_PERMISSION", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "CD_PERMISSION", columnDefinition = "VARCHAR(120)", nullable = false )
	private String code;

	@ManyToMany( fetch = FetchType.LAZY, mappedBy = "permissions" )
	private List<Role> roles;

	@Override
	public String getAuthority() {
		return this.code;
	}

	public Permission( UUID id ) {
		this.id = id;
	}
}
