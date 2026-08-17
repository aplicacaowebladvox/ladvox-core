package br.com.jovencio.projetofonocore.dao.repositories;

public interface ISearchersRepository {

	//	default SearchReturnDTO<SocialMediaDTO> searchSocialMedia( SocialMediaGridOptions options ) {
	//		Long count = searchCount( options );
	//		if( Optional.ofNullable( count ).orElse( 0L ).compareTo( 0L ) > 0 ) {
	//			return new SearchReturnDTO<>( search( options ), count );
	//		}
	//		return new SearchReturnDTO<>();
	//	}
	//
	//	private Long searchCount( SocialMediaGridOptions options ) {
	//		var hql = new StringBuilder();
	//		Map<String, Object> params = new HashMap<>();
	//
	//		hql.append( "select count(1)" );
	//		buildSearchParams( options, hql, params );
	//
	//		var query = mountQuerySearchCount( hql.toString(), Long.class );
	//		params.forEach( query::setParameter );
	//
	//		return ( Long ) query.getSingleResult();
	//	}
	//
	//	default List<SocialMediaDTO> search( SocialMediaGridOptions options ) {
	//		var hql = new StringBuilder();
	//		Map<String, Object> params = new HashMap<>();
	//
	//		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.dtos.SocialMediaDTO( " );
	//		hql.append( "   socialMedia.id AS id , " );
	//		hql.append( "   socialMedia.iconClass AS iconClass, " );
	//		hql.append( "   socialMedia.iconColor AS iconColor, " );
	//		hql.append( "   socialMedia.title AS title, " );
	//		hql.append( "   socialMedia.url AS url " );
	//		hql.append( " ) " );
	//		buildSearchParams( options, hql, params );
	//
	//		var query = mountQuerySearch( hql.toString(), options, SocialMediaDTO.class );
	//
	//		params.forEach( query::setParameter );
	//
	//		return Optional.ofNullable( ( List<SocialMediaDTO> ) query.getResultList() ).orElse( Collections.emptyList() );
	//	}
	//
	//	private void buildSearchParams( SocialMediaGridOptions options, StringBuilder hql, Map<String, Object> params ) {
	//		hql.append( " FROM SocialMedia socialMedia " );
	//		hql.append( " WHERE 1=1 " );
	//		if( Objects.nonNull( options.getId() ) ) {
	//			hql.append( "   AND socialMedia.id = :id " );
	//			params.put( "id", options.getId() );
	//		}
	//		if( Objects.nonNull( options.getIconClass() ) ) {
	//			hql.append( "   AND socialMedia.iconClass = :iconClass " );
	//			params.put( "iconClass", options.getIconClass() );
	//		}
	//		if( Objects.nonNull( options.getTitle() ) && !options.getTitle().isEmpty() && !options.getTitle().isBlank() ) {
	//			hql.append( "   AND isAlikeCharVarying(socialMedia.title, :title) " );
	//			params.put( "title", options.getTitle() );
	//		}
	//	}
}
