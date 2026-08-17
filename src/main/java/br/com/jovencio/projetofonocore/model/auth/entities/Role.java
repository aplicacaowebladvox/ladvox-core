package br.com.jovencio.projetofonocore.model.auth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "ROLES" )
public class Role {

	@Id
	@GeneratedValue( strategy = GenerationType.UUID )
	@Column( name = "ID_ROLE", columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false )
	private UUID id;

	@Column( name = "NM_ROLE", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "BL_ROLE_SHOW_ON_LANDING_PAGE", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean showOnLandingPage;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_ROLES_PERMISIONS",
			joinColumns = @JoinColumn( name = "ID_ROLE" ),
			inverseJoinColumns = @JoinColumn( name = "ID_PERMISSION" ) )
	private List<Permission> permissions;

	@ManyToMany( fetch = FetchType.LAZY, mappedBy = "roles" )
	private List<User> users;

	public Role( UUID id ) {
		this.id = id;
	}

	public boolean isPatient() {
		return this.name.equalsIgnoreCase( "Paciente" );
	}
}
