package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "GENDERS" )
public class Gender {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_GENDER" )
	private Long id;

	@Column( name = "NM_GENDER", columnDefinition = "VARCHAR(50)", nullable = false )
	private String name;

	@Column( name = "BL_GENDER_HAS_OTHER_DEFINITION", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean hasOtherDefinition;

	public Gender( Long id ) {
		this.id = id;
	}
}
