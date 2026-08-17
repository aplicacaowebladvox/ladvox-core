package br.com.jovencio.projetofonocore.configs.hibernate.customarraytype;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;

public class CustomStringArrayType  implements UserType<String[]> {

	@Override
	public int getSqlType() {
		return Types.ARRAY;
	}

	@Override
	public Class<String[]> returnedClass() {
		return String[].class;
	}

	@Override
	public boolean equals( String[] strings, String[] j1 ) {
		if( strings == null && j1 == null )
			return true;
		if( strings == null || j1 == null )
			return false;
		if( strings.length != j1.length )
			return false;
		for( int i = 0; i < strings.length; i++ ) {
			if( !strings[i].equals( j1[i] ) )
				return false;
		}
		return true;
	}

	@Override
	public int hashCode( String[] strings ) {
		return Arrays.hashCode( strings );
	}

	@Override
	public String[] nullSafeGet( ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o ) throws SQLException {
		Array array = resultSet.getArray( i );
		return array != null ? ( String[] ) array.getArray() : null;
	}

	@Override
	public void nullSafeSet( PreparedStatement preparedStatement, String[] strings, int i, SharedSessionContractImplementor sharedSessionContractImplementor ) throws SQLException {
		if( preparedStatement != null ) {
			if( strings != null ) {
				Array array = sharedSessionContractImplementor.getJdbcConnectionAccess().obtainConnection().createArrayOf( "text", strings );
				preparedStatement.setArray( i, array );
			} else {
				preparedStatement.setNull( i, Types.ARRAY );
			}
		}
	}

	@Override
	public String[] deepCopy( String[] strings ) {
		return Arrays.copyOf( strings, strings.length );
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble( String[] strings ) {
		return null;
	}

	@Override
	public String[] assemble( Serializable serializable, Object o ) {
		return new String[0];
	}
}
