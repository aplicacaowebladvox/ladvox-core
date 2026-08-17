package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.ProtocolTherapeuticPlan;
import br.com.jovencio.projetofonocore.model.grid.MyProtocolGrid;
import br.com.jovencio.projetofonocore.model.grid.ProtocolTherapeuticPlanGrid;
import br.com.jovencio.projetofonocore.model.grid.options.MyProtocolGridOptions;
import br.com.jovencio.projetofonocore.model.grid.options.ProtocolTherapeuticPlanGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IProtocolTherapeuticPlanRepository extends JpaRepository<ProtocolTherapeuticPlan, Long>, CustomSearchRepository {

	default Long searchCount( ProtocolTherapeuticPlanGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<ProtocolTherapeuticPlanGrid> search( ProtocolTherapeuticPlanGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.ProtocolTherapeuticPlanGrid( " );
		hql.append( "     ptp.id, " );
		hql.append( "     p.id, " );
		hql.append( "     CONCAT( " );
		hql.append( "         CASE " );
		hql.append( "             WHEN p.abbreviation is not null then concat('(', p.abbreviation, ') - ') " );
		hql.append( "             ELSE CONCAT('(', p.id, ') - ') " );
		hql.append( "         END, " );
		hql.append( "         p.name, " );
		hql.append( "         ' válido de ', " );
		hql.append( "             TO_CHAR(p.initialValidity, 'dd/mm/yyyy'), " );
		hql.append( "             CASE " );
		hql.append( "                 WHEN p.finalValidity IS NULL THEN ' em diante' " );
		hql.append( "                 ELSE CONCAT(' até ', to_char(p.finalValidity, 'dd/MM/yyyy')) " );
		hql.append( "             END " );
		hql.append( "     ), " );
		hql.append( "     ptp.requestDate, " );
		hql.append( "     ptp.answeredDate, " );
		hql.append( "     tp.id " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, ProtocolTherapeuticPlanGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, ProtocolTherapeuticPlanGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM ProtocolTherapeuticPlan ptp " );
		hql.append( "JOIN ptp.protocol p " );
		hql.append( "JOIN ptp.therapeuticPlan tp " );
		hql.append( "JOIN tp.medicalAppointment ma " );
		hql.append( "JOIN ma.patient patient " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND ptp.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getTherapeuticPlanId() != null ) {
			hql.append( "   AND tp.id = :therapeuticPlanId " );
			params.put( "therapeuticPlanId", options.getTherapeuticPlanId() );
		}
		if( options.getPatientId() != null ) {
			hql.append( "   AND patient.id = :patientId " );
			params.put( "patientId", options.getPatientId() );
		}
		if( options.getProtocolId() != null ) {
			hql.append( "   AND p.id = :protocolId " );
			params.put( "protocolId", options.getProtocolId() );
		}
		if( options.getRequestDateStarts() != null ) {
			hql.append( "   AND CAST(ptp.requestDate AS DATE) >= :requestDateStarts " );
			params.put( "requestDateStarts", options.getRequestDateStarts() );
		}
		if( options.getRequestDateEnds() != null ) {
			hql.append( "   AND CAST(ptp.requestDate AS DATE) <= :requestDateEnds " );
			params.put( "requestDateEnds", options.getRequestDateEnds() );
		}
		if( options.getAnsweredDateStarts() != null ) {
			hql.append( "   AND CAST(ptp.answeredDate AS DATE) >= :answeredDateStarts " );
			params.put( "answeredDateStarts", options.getAnsweredDateStarts() );
		}
		if( options.getAnsweredDateEnds() != null ) {
			hql.append( "   AND CAST(ptp.answeredDate AS DATE) <= :answeredDateEnds " );
			params.put( "answeredDateEnds", options.getAnsweredDateEnds() );
		}

	}

	default Long searchMyProtocolsCount( MyProtocolGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchMyProtocolsFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<MyProtocolGrid> searchMyProtocols( MyProtocolGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.MyProtocolGrid( " );
		hql.append( "     ptp.id, " );
		hql.append( "     p.id AS protocolId, " );
		hql.append( "     p.abbreviation AS protocolAbbreviation, " );
		hql.append( "     p.name AS protocolName, " );
		hql.append( "     ptp.requestDate, " );
		hql.append( "     ptp.answeredDate " );
		hql.append( " ) " );
		mountSearchMyProtocolsFromClause( hql, options, params );

		var query = mountSearch( hql, options, MyProtocolGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchMyProtocolsFromClause( StringBuilder hql, MyProtocolGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM ProtocolTherapeuticPlan ptp " );
		hql.append( "JOIN ptp.protocol p " );
		hql.append( "JOIN ptp.therapeuticPlan tp " );
		hql.append( "JOIN tp.medicalAppointment ma " );
		hql.append( "JOIN ma.patient patient " );
		hql.append( "JOIN patient.user user " );
		hql.append( "WHERE user.id = :userId " );
		params.put( "userId", options.getUserId() );
		hql.append( "  AND (1<>1 " );
		if( options.isNotAnswered() ) {
			hql.append( "   OR ptp.answeredDate IS NULL " );
		}
		if( options.isAnswered() ) {
			hql.append( "   OR ptp.answeredDate IS NOT NULL " );
		}
		hql.append( "  ) " );
	}
}
