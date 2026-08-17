package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "THERAPEUTIC_PLAN_EDITORS_HISTORY" )
public class TherapeuticPlanEditorHistory {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_THERAPEUTIC_PLAN_EDITOR_HISTORY" )
	private Long id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_THERAPEUTIC_PLAN" )
	private TherapeuticPlan therapeuticPlan;

	@Column( name = "DT_THERAPEUTIC_PLAN_EDITOR_HISTORY_STARTED", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()" )
	private LocalDateTime startedDate;

	@Column( name = "DT_THERAPEUTIC_PLAN_EDITOR_HISTORY_ENDED", columnDefinition = "TIMESTAMP" )
	private LocalDateTime endedDate;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_USER_EDITOR" )
	private User editor;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_USER_STARTED" )
	private User startedUser;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_USER_ENDED" )
	private User endedUser;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "therapeuticPlanEditorHistory" )
	private List<TherapeuticPlanEditorHistoryTextVersion> versions;

}
