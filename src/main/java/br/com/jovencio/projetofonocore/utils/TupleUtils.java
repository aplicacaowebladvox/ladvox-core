package br.com.jovencio.projetofonocore.utils;

import jakarta.persistence.Tuple;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class TupleUtils {

	public static Long getLong( Tuple tuple, String alias ) {
		try {
			var value = ( BigInteger ) tuple.get( alias );
			return value != null ? value.longValue() : null;
		} catch ( Exception e ) {
			return ( Long ) tuple.get( alias );
		}
	}

	public static String getString( Tuple tuple, String alias ) {
		return ( String ) tuple.get( alias );
	}

	public static LocalDateTime getLocalDateTime( Tuple tuple, String alias ) {
		var dateStr = ( Timestamp ) tuple.get( alias );
		if( dateStr == null ) {
			return null;
		}
		return dateStr.toLocalDateTime();
	}

	public static LocalDate getLocalDate( Tuple tuple, String alias ) {
		var value = ( Date ) tuple.get( alias );
		return value == null ? null : value.toLocalDate();
	}

	public static boolean getBoolean( Tuple tuple, String alias ) {
		return ( boolean ) tuple.get( alias );
	}

	public static Boolean getBooleanClass( Tuple tuple, String alias ) {
		return ( Boolean ) tuple.get( alias );
	}

	public static LocalTime getLocalTime( Tuple tuple, String alias ) {
		var value = ( Time ) tuple.get( alias );
		return value != null ? value.toLocalTime() : null;
	}
}
