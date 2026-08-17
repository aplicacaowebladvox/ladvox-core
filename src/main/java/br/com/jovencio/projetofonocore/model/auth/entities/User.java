package br.com.jovencio.projetofonocore.model.auth.entities;

import br.com.jovencio.projetofonocore.model.entities.Attachment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "USERS" )
public class User {

	@Id
	@GeneratedValue( strategy = GenerationType.UUID )
	@Column( name = "ID_USER", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false )
	private UUID id;

	@Column( name = "NM_USER", columnDefinition = "VARCHAR(255)", nullable = false )
	private String name;

	@Column( name = "CD_USER_LOGIN", columnDefinition = "VARCHAR(25)" )
	private String login;

	@Column( name = "CD_USER_PASSWORD", columnDefinition = "VARCHAR(255)" )
	private String password;

	@Column( name = "CD_USER_DOCUMENT", columnDefinition = "VARCHAR(25)", nullable = false )
	private String document;

	@Column( name = "DT_USER_BIRH_DATE", columnDefinition = "DATE", nullable = false )
	private LocalDate birthDate;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_ATTACHMENT_PROFILE_IMAGE", nullable = true )
	private Attachment profileImage;

	@Column( name = "BL_USER_SHOW_ON_LANDING_PAGE", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean showOnLandingPage;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_USERS_ROLES",
			joinColumns = @JoinColumn( name = "ID_USER" ),
			inverseJoinColumns = @JoinColumn( name = "ID_ROLE" ) )
	private List<Role> roles;

	@Column( name = "DT_USER_INACTIVATION", columnDefinition = "TIMESTAMP" )
	private LocalDateTime inactivationDate;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "ID_USER_INACTIVATION" )
	private User inacinactivationUser;

	public User( UUID id ) {
		this.id = id;
	}

}
