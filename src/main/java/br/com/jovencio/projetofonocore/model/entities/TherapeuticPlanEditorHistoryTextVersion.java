package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table( name = "THERAPEUTIC_PLAN_EDITOR_HISTORY_TEXT_VERSIONS" )
public class TherapeuticPlanEditorHistoryTextVersion {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_THERAPEUTIC_PLAN_EDITOR_HISTORY_TEXT_VERSION" )
	private Long id;

	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_THERAPEUTIC_PLAN_EDITOR_HISTORY" )
	private TherapeuticPlanEditorHistory therapeuticPlanEditorHistory;

	@Column( name = "TX_THERAPEUTIC_PLAN", columnDefinition = "TEXT" )
	private String therapeuticTextPlan;

	@Column( name = "DT_THERAPEUTIC_PLAN_EDITOR_HISTORY_TEXT_VERSION_CREATED", columnDefinition = "TIMESTAMP DEFAULT now()", nullable = false )
	private LocalDateTime createdDate;

}
