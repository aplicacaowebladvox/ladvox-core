package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "THERAPEUTIC_PLAN_COMMENTS" )
public class TherapeuticPlanComment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_THERAPEUTIC_PLAN_COMMENT" )
	private Long id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_THERAPEUTIC_PLAN" )
	private TherapeuticPlan therapeuticPlan;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_USER" )
	private User user;

	@Column( name = "DT_THERAPEUTIC_PLAN_COMMENT_CREATED", columnDefinition = "TIMESTAMP DEFAULT now()", nullable = false )
	private LocalDateTime createdDate;

	@Column( name = "TX_THERAPEUTIC_PLAN_COMMENT", columnDefinition = "VARCHAR(500)", nullable = false )
	private String textComment;

}
