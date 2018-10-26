package com.infosys.agile.common;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IAttribute;
import com.agile.api.IItem;
import com.agile.api.ITable;
import com.agile.api.ITable.ISortBy.Order;
import com.agile.api.IUser;
import com.agile.api.ItemConstants;
import com.agile.api.UserConstants;

public class User {

	public IUser getLoggedinUser(IAgileSession session) throws APIException{
		IUser user = session.getCurrentUser();
		user.getValue(UserConstants.ATT_PREFERENCES_TIME_ZONE);
		IItem item = null;
		ITable bomTable = item.getTable(ItemConstants.TABLE_BOM);
		bomTable.add(item);
		bomTable.remove(item);
		
		IAttribute []attribute = bomTable.getAttributes();
		IAttribute attr = null;
		for (IAttribute iAttribute : attribute) {
			if(iAttribute.getName().equals("")) {
				attr = iAttribute;
				break;
			}
		}
		bomTable.createSortBy(attr, Order.ASCENDING);
		return null;
	}
}
