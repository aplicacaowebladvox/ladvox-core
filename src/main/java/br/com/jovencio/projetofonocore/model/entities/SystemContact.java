package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table( name = "SYSTEM_CONTACTS" )
public class SystemContact {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_SYSTEM_CONTACT" )
	private Long id;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_SYSTEMCONTACTS_ADDRESSES",
			joinColumns = @JoinColumn( name = "ID_SYSTEM_CONTACT" ),
			inverseJoinColumns = @JoinColumn( name = "ID_ADDRESS" ) )
	private List<Address> addresses;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_SYSTEMCONTACTS_EMAILSCONTACT",
			joinColumns = @JoinColumn( name = "ID_SYSTEM_CONTACT" ),
			inverseJoinColumns = @JoinColumn( name = "ID_EMAIL_CONTACT" ) )
	private List<EmailContact> emails;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_SYSTEMCONTACTS_PHONESCONTACT",
			joinColumns = @JoinColumn( name = "ID_SYSTEM_CONTACT" ),
			inverseJoinColumns = @JoinColumn( name = "ID_PHONE_CONTACT" ) )
	private List<PhoneContact> phones;

}
