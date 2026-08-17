package br.com.jovencio.projetofonocore.utils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class ConvertUtils {

	public static final String DELIMITTER = ";";

	public static List<String> getListStringFromString( String listString ) {
		if( listString == null || listString.isEmpty() || listString.isBlank() ) {
			return Collections.emptyList();
		}
		if( listString.contains( DELIMITTER ) ) {
			return List.of( listString.split( DELIMITTER ) );
		} else {
			return List.of( listString );
		}
	}

	public static String removeOperators( UUID key ) {
		String OPERATORS = "[+\\-*/%^(){}\\[\\]]";
		return key.toString().replaceAll( OPERATORS, "" );
	}
}
