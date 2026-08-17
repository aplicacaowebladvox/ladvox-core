package br.com.jovencio.projetofonocore.model.grid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyProtocolGrid {

	private Long id;
	private Long protocolId;
	private String protocolAbbreviation;
	private String protocolName;
	private LocalDateTime requestDate;
	private LocalDateTime answeredDate;

	public MyProtocolGrid(
			Long id,
			Long protocolId,
			String protocolAbbreviation,
			String protocolName,
			LocalDateTime requestDate,
			LocalDateTime answeredDate
	) {
		this.id = id;
		this.protocolId = protocolId;
		this.protocolAbbreviation = protocolAbbreviation;
		this.protocolName = protocolName;
		this.requestDate = requestDate;
		this.answeredDate = answeredDate;
	}
}
