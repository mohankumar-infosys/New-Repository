package com.infosys.agile.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.agile.api.APIException;
import com.agile.api.CommonConstants;
import com.agile.api.IAgileClass;
import com.agile.api.IAgileSession;
import com.agile.api.IAttribute;
import com.agile.api.IAutoNumber;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.api.Money;

public class DataObject
{

    /**
     * Get the next available auto number from the first auto number source associated with the
     * subclass
     * 
     * @param IAgileSession Agile Session
     * @param String Agile subclass name for which we have to fetch the next auto number
     * @return String Next available auto number
     * @throws APIException APIException
     */
    public String getNextAutoNumber(final IAgileSession session,
                                    final String subclassName)
                                        throws APIException
    {

        final IAgileClass agileClass = session.getAdminInstance().getAgileClass(subclassName);
        final IAutoNumber[] autoNumbers = agileClass.getAutoNumberSources();
        if (autoNumbers != null)
        {
            return autoNumbers[0].getNextNumber();
        }
        return "";
    }


    /**
     * Get the next available auto number from the first auto number source associated with the
     * subclass
     * 
     * @param IAgileClass Agile subclass name for which we have to fetch the next auto number
     * @return String Next available auto number
     * @throws APIException APIException
     */
    public String getNextAutoNumber(final IAgileClass agileClass)
        throws APIException
    {

        final IAutoNumber[] autoNumbers = agileClass.getAutoNumberSources();
        if (autoNumbers != null)
        {
            return autoNumbers[0].getNextNumber();
        }
        return "";
    }


    /**
     * Get all the auto number sources associated with a subclass
     * 
     * @param IAgileClass Agile subclass name for which we have to fetch the auto number sources
     * @return IAutoNumber[] All auto number sources
     * @throws APIException APIException
     */
    public IAutoNumber[] getAutonumberSources(final IAgileClass agileClass)
        throws APIException
    {

        return agileClass.getAutoNumberSources();
    }


    /**
     * @param IAgileSession Agile Session
     * @param String Agile subclass name for which we have to fetch the auto numbers
     * @return IAutoNumber[] All auto number sources
     * @throws APIException APIException
     */
    public IAutoNumber[] getAutonumberSources(final IAgileSession session,
                                              final String subclassName)
                                                  throws APIException
    {

        final IAgileClass agileClass = session.getAdminInstance().getAgileClass(subclassName);
        return agileClass.getAutoNumberSources();

    }
    
    
    /**
	 * Get table information as Map
	 * 
	 * @param ITable
	 *            table: table of the Item object
	 * @return Map<IItem, IRow>
	 * @throws APIException
	 *             APIException
	 */
    public Map<IItem, IRow> getItemTableInformationAsMap(final ITable table) throws APIException {

		Map<IItem, IRow> mapOfTableValues = new HashMap<IItem, IRow>();
		Iterator tableIterator = table.iterator();
		while (tableIterator.hasNext()) {
			IRow rowObject = (IRow) tableIterator.next();
			IItem itemObjectOfRow = (IItem) rowObject.getReferent();
			mapOfTableValues.put(itemObjectOfRow, rowObject);
		}
		return mapOfTableValues;

	}

	/**
	 * Get table information as Map
	 * 
	 * @param ITable
	 *            table: table of the Change object
	 * @return Map<IChange, IRow>
	 * @throws APIException
	 *             APIException
	 */
	public Map<IChange, IRow> getChangeTableInformationAsMap(final ITable table) throws APIException {

		Map<IChange, IRow> mapOfTableValues = new HashMap<IChange, IRow>();
		Iterator tableIterator = table.iterator();
		while (tableIterator.hasNext()) {
			IRow rowObject = (IRow) tableIterator.next();
			IChange itemObjectOfRow = (IChange) rowObject.getReferent();
			mapOfTableValues.put(itemObjectOfRow, rowObject);
		}
		return mapOfTableValues;

	}

	/**
	 * To create relationship between the parent Object and reference Object
	 * 
	 * @param IDataObject
	 *            parentObject: parent Object
	 * @param IDataObject
	 *            relationShipObject: reference Object
	 * @return Boolean
	 * @throws APIException
	 *             APIException
	 */
	public Boolean createRelationship(final IDataObject parentObject, final IDataObject relationShipObject)
			throws APIException {

		Map<Object, IDataObject> mapOfTableValues = new HashMap<Object, IDataObject>();
		ITable relationshipTableOfItem = parentObject.getTable(ItemConstants.TABLE_RELATIONSHIPS);

		mapOfTableValues.put(CommonConstants.ATT_RELATIONSHIPS_NAME, relationShipObject);

		IRow relationshipTableRow = relationshipTableOfItem.createRow(mapOfTableValues);

		return true;

	}
    
	/**
	 * Set agile attribute value of any given data type
	 * 
	 * @param IItem
	 *            itemObject for which the attribute has to be fetched
	 * @param String
	 *            attributeMultiText: multitext datatype
	 * @param String
	 *            attributeHeading: heading datatype
	 * @param String
	 *            attributeValueText: text datatype
	 * @param List
	 *            attributeValueMultiList: Multilist datatype
	 * @param List
	 *            attributeValueList: List datatype
	 * @param Integer
	 *            attributeValueNumeric: Numeric datatype
	 * @param Money
	 *            attributeMoney: Money datatype
	 * @param Date
	 *            attributeValueDate: Date datatype
	 * @param String
	 *            itemType: sublclass type of the Item
	 * @param Object
	 *            baseID: base id of the attribute to be set
	 * @return IItem itemObject after setting the value for the attribute
	 * @throws APIException
	 *             APIException
	 */

	public IItem setValuesForAgileAttributes(final String attributeMultiText, final String attributeHeading,
			final String itemType, final IItem itemObject, final Object baseID, final Integer attributeNumeric,
			final List attributeValueList, final List attributeValueMultiList, final Integer attributeValueNumeric,
			final String attributeValueText, final Date attributeValueDate, final Money attributeMoney)
					throws APIException {

		Integer dataTypes = 5;
		
		IAttribute attribute = itemObject.getSession().getAdminInstance().getAgileClass(itemType).getAttribute(baseID);

		Integer dataTypeOfAttribute = attribute.getDataType();

		switch (dataTypes) {

		case 1:
			dataTypeOfAttribute = 2;

			if (attributeMultiText != null || attributeMultiText.equalsIgnoreCase("")) {
				itemObject.setValue(baseID, attributeMultiText);
			}
			if (attributeValueText != null || attributeValueText.equalsIgnoreCase("")) {
				itemObject.setValue(baseID, attributeValueText);
			}
			if (attributeHeading != null || attributeHeading.equalsIgnoreCase("")) {
				itemObject.setValue(baseID, attributeHeading);
			}
			break;

		case 2:
			dataTypeOfAttribute = 4;
			itemObject.setValue(baseID, attributeValueList);
			break;

		case 3:
			dataTypeOfAttribute = 3;
			itemObject.setValue(baseID, attributeValueDate);
			break;

		case 4:
			dataTypeOfAttribute = 5;
			itemObject.setValue(baseID, attributeValueMultiList);
			break;

		case 5:
			dataTypeOfAttribute = 8;
			itemObject.setValue(baseID, attributeNumeric);
			break;

		}

		return itemObject;
	}
	
}
