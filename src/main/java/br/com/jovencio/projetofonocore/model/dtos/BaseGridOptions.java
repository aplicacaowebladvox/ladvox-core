package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BaseGridOptions {

	private int page = 1;
	private int pageSize = 10;
	private List<BaseFilterGridOptions> orderByFilters = new ArrayList<>();

	public String getOrders() {
		return String.join( ", ", orderByFilters.stream().sorted( ( o1, o2 ) -> Integer.compare( o1.getOrderPriority(), o2.getOrderPriority() ) ).map( o -> o.getField() + " " + o.getTypeOrderSql() ).toList() );
	}

	public int getOffset() {
		return ( ( page <= 0 ? 1 : page ) - 1 ) * ( pageSize <= 0 ? 10 : pageSize );
	}

}
