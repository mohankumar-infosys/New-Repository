package com.infosys.agile.common;

import com.agile.api.APIException;
import com.agile.api.IAttribute;
import com.agile.api.ITable;
import com.agile.api.ITable.ISortBy.Order;

public abstract class Bom {
	/**
	 * To Sort Table
	 * @param table - Table to be sorted
	 * @param attributeName - Attribute Name to set sorting
	 * @param order - Specify sorting order
	 * @return ITable after sorting
	 * @throws APIException
	 */
	public static ITable sortBomTable(ITable table, String attributeName, Order order) throws APIException {
		IAttribute attr = findAttributeByName(table, attributeName);
		table.createSortBy(attr, order);
		return table;
	}
	
	public static IAttribute findAttributeByName(ITable bomTable, String attributeName) throws APIException {
		IAttribute[] attribute = bomTable.getAttributes();
		// IAttribute iAttribute =
		// bomTable.getTableDescriptor().getAttribute(AttributeName);
		for (IAttribute iAttribute : attribute) {
			if (iAttribute.getName().equals(attributeName) || iAttribute.getAPIName().equals(attributeName)) {
				return iAttribute;
			}
		}
		return null;
	}
}
