package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "THERAPEUTIC_PLAN_ATTACHMENTS" )
public class TherapeuticPlanAttachment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_THERAPEUTIC_PLAN_ATTACHMENT" )
	private Long id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_THERAPEUTIC_PLAN" )
	private TherapeuticPlan therapeuticPlan;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_ATTACHMENT" )
	private Attachment attachment;

}
