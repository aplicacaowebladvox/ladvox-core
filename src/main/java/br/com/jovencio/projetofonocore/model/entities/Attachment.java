package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "ATTACHMENTS" )
public class Attachment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ATTACHMENT" )
	private Long id;

	@Column( name = "DT_ATTACHMENT_CREATED_DATE", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false )
	private LocalDateTime createdDate;

	@Column( name = "NM_ATTACHMENT", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "NR_ATTACHMENT_BYTEs_SIZE", columnDefinition = "INT", nullable = false )
	private int bytesSize;

	@Column( name = "TP_ATTACHMENT", columnDefinition = "VARCHAR(120)", nullable = false )
	private String type;

	@Column( name = "DT_ATTACHMENT_LAST_MODIFIED_DATE", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false )
	private LocalDateTime lastModifiedDate;

	@Column( name = "TX_ATTACHMENT_BASE64", columnDefinition = "TEXT", nullable = false )
	private String base64;

}
