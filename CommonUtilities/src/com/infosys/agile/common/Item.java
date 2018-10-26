package com.infosys.agile.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.IAgileClass;
import com.agile.api.IAgileSession;
import com.agile.api.IAutoNumber;
import com.agile.api.IChange;
import com.agile.api.IItem;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;

public class Item
{

    /**
     * Create a new Item using the item number and type as input
     * 
     * @param IAgileSession Agile Session object
     * @param String Number of the item to be created
     * @param String Subclass of the item to be created
     * @param Map<Object, Object> Map consisting of other values to be set on the item to be
     *            created. This input is optional and can be null.
     * @return IItem New Item Object created
     * @throws APIException APIException
     */
    public IItem createItemObject(final IAgileSession session,
                                  final String itemNumber,
                                  final String itemType,
                                  final Map<Object, Object> inputValues)
                                      throws APIException
    {

        final IAgileClass agileClass = session.getAdminInstance().getAgileClass(itemType);
        final IItem newItemObj = (IItem) session.createObject(agileClass, itemNumber);

        if (inputValues != null)
        {
            newItemObj.setValues(inputValues);
        }
        return newItemObj;
    }


    /**
     * Create a new Item using the Auto numbers associated with the subclass
     * 
     * @param IAgileSession Agile Session object
     * @param String Subclass of the item to be created
     * @param Map<Object, Object> Map consisting of other values to be set on the item to be
     *            created. This input is optional and can be null.
     * @return IItem New Item Object created
     * @throws APIException APIException
     */
    public IItem createItemObjectUsingNextAutoNumber(final IAgileSession session,
                                                     final String itemType,
                                                     final Map<Object, Object> inputValues)
                                                         throws APIException
    {

        final IAgileClass agileClass = session.getAdminInstance().getAgileClass(itemType);
        final IAutoNumber[] autoNumbers = agileClass.getAutoNumberSources();
        final String itemNumber = autoNumbers[0].getNextNumber();
        final IItem newItemObj = (IItem) session.createObject(agileClass, itemNumber);
        if (inputValues != null)
        {
            newItemObj.setValues(inputValues);
        }
        return newItemObj;
    }


    /**
     * @param IItem Item object on which data has to be updated
     * @param Map<Object, Object> Map consisting of other values to be set on the item to be
     *            created. This input is optional and can be null.
     * @return IItem Item which is updated
     * @throws APIException APIException
     */
    public IItem updateItemObject(final IItem itemObj,
                                  final Map<Object, Object> inputValues)
                                      throws APIException
    {
        if (inputValues != null)
        {
            itemObj.setValues(inputValues);
        }
        return itemObj;
    }


    /**
     * Get current Revision of the item object
     * 
     * @param IItem Item object for which we have to fetch the current revision
     * @return String Current Revision
     * @throws APIException APIException
     */
    public String getCurrentRevisionOfItem(final IItem itemObj)
        throws APIException
    {
        return itemObj.getRevision();
    }


    /**
     * Get all the Revisions of the item object
     * 
     * @param IItem Item object for which we have to fetch the current revision
     * @return Map<Object, Object> Get all revisions
     * @throws APIException APIException
     */
    public Map<Object, Object> getAllRevisionsOfItem(final IItem itemObj)
        throws APIException
    {
        return itemObj.getRevisions();
    }


    /**
     * Get all the title block attributes as a map from the item object
     * 
     * @param IItem Item object for which we have to fetch title block attributes
     * @return Map<Object, Object> Title block attributes in Map
     * @throws APIException APIException
     */
    public Map<Object, Object> getTitleBlockAttributesFromItemAsMap(final IItem itemObject)
        throws APIException
    {
        Map<Object, Object> params = new HashMap<Object, Object>();
        final ITable table = itemObject.getTable(ItemConstants.TABLE_TITLEBLOCK);
        final Iterator<IRow> itr = table.iterator();
        while (itr.hasNext())
        {
            final IRow row = itr.next();
            params = row.getValues();
        }
        return params;
    }


    /**
     * Get all the Page Two attributes as a map from the item object
     * 
     * @param IItem Item object for which we have to fetch Page two attributes
     * @return Map<Object, Object> Page Two attributes in Map
     * @throws APIException APIException
     */
    public Map<Object, Object> getPageTwoAttributesFromItemAsMap(final IItem itemObject)
        throws APIException
    {
        Map<Object, Object> params = new HashMap<Object, Object>();
        final ITable table = itemObject.getTable(ItemConstants.TABLE_PAGETWO);
        final Iterator<IRow> itr = table.iterator();
        while (itr.hasNext())
        {
            final IRow row = itr.next();
            params = row.getValues();
        }
        return params;
    }


    /**
     * Get all the Page Three attributes as a map from the item object
     * 
     * @param IItem Item object for which we have to fetch Page Three attributes
     * @return Map<Object, Object> Page Three attributes in Map
     * @throws APIException APIException
     */
    public Map<Object, Object> getPageThreeAttributesFromItemAsMap(IItem itemObject)
        throws APIException
    {
        Map<Object, Object> params = new HashMap<Object, Object>();
        final ITable table = itemObject.getTable(ItemConstants.TABLE_PAGETHREE);
        final Iterator<IRow> itr = table.iterator();
        while (itr.hasNext())
        {
            final IRow row = itr.next();
            params = row.getValues();
        }
        return params;
    }


    /**
     * Get all the attributes as a map from the item object
     * 
     * @param IItem Item object for which we have to fetch all the attributes
     * @return Map<Object, Object> All attributes in Map
     * @throws APIException APIException
     */
    public Map<Object, Object> getAllItemAttributesFromItemAsMap(IItem itemObject)
        throws APIException
    {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.putAll(getTitleBlockAttributesFromItemAsMap(itemObject));
        params.putAll(getPageTwoAttributesFromItemAsMap(itemObject));
        params.putAll(getPageThreeAttributesFromItemAsMap(itemObject));
        return params;
    }


    /**
     * Incorporate Item Object
     * 
     * @param IItem Item to be incorporated
     * @throws APIException APIException
     */
    public void incorporateItemObject(final IItem itemObject)
        throws APIException
    {
        if (!itemObject.isIncorporated())
        {
            itemObject.setIncorporated(true);
        }
    }


    /**
     * Un incorporate Item Object
     * 
     * @param IItem Item to be incorporated
     * @throws APIException APIException
     */
    public void unincorporateItemObject(final IItem itemObject)
        throws APIException
    {
        if (itemObject.isIncorporated())
        {
            itemObject.setIncorporated(false);
        }
    }


    /**
     * Set revision to item object
     * 
     * @param IItem Item on which revision has to be set
     * @param String Revision to be set
     * @return boolean True if revision is set
     * @throws APIException APIException
     */
    public boolean setRevision(final IItem itemObject,
                               final String revision)
                                   throws APIException
    {
        itemObject.setRevision(revision);
        return true;
    }


    /**
     * Get specific attribute from item object
     * 
     * @param IItem Item from which data has to be fetched
     * @param Object Base Id Or APIName of the attribute
     * @return String Attribute value
     * @throws APIException APIException
     */
    public String getAttributeValue(final IItem itemObject,
                                    final Object attributeId)
                                        throws APIException
    {
        final Object itemDescription = itemObject.getValue(attributeId);
        if (itemDescription != null)
        {
            return itemDescription.toString();
        }
        return "";
    }


    /**
     * Get first level bom items
     * 
     * @param IItem Item for which first level bom items have to be fetched
     * @param Map<IItem, IRow> Empty map or null
     * @return Map<IItem, IRow>
     * @throws APIException APIException
     */
    public Map<IItem, IRow> getFirstLevelBomItems(final IItem itemObject,
                                                  Map<IItem, IRow> params)
                                                      throws APIException
    {
        if (params == null)
        {
            params = new HashMap<IItem, IRow>();
        }
        final ITable bomTable = itemObject.getTable(ItemConstants.TABLE_BOM);
        final Iterator<IRow> itr = bomTable.iterator();
        while (itr.hasNext())
        {
            IRow row = (IRow) itr.next();
            IItem itemObj = (IItem) row.getReferent();
            params.put(itemObj, row);
        }
        return params;
    }


    /**
     * Get all level bom items
     * 
     * @param IItem Item for which all level bom items have to be fetched
     * @param Map<IItem, IRow> Empty map or null
     * @return Map<IItem, IRow>
     * @throws APIException APIException
     */
    public Map<IItem, IRow> getAllLevelBomItems(final IItem itemObject,
                                                Map<IItem, IRow> params)
                                                    throws APIException
    {
        if (params == null)
        {
            params = new HashMap<IItem, IRow>();
        }
        ITable bomTable = itemObject.getTable(ItemConstants.TABLE_BOM);
        Iterator itr = bomTable.iterator();
        while (itr.hasNext())
        {
            IRow row = (IRow) itr.next();
            IItem itemObj = (IItem) row.getReferent();
            params.put(itemObj, row);
            getAllLevelBomItems(itemObj, params);
        }

        return params;
    }


    /**
     * Get Redlined Title Block Attributes from an item
     * 
     * @param IItem Item for which title block attributes have to be fetched
     * @param IChange Change from which the data of item has to be fetched
     * @return Map<IItem, IRow>
     * @throws APIException APIException
     */
    public Map<Object, Object> getRedlinedTitleBlockAttributesFromItemAsMap(final IItem itemObject,
                                                                            final IChange changeObject)
                                                                                throws APIException
    {
        Map params = new HashMap<>();
        final ITable affectedItemsTable = changeObject.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
        final Iterator iterator = affectedItemsTable.getReferentIterator();

        while (iterator.hasNext())
        {
            final IItem affectedItem = (IItem) iterator.next();
            final ITable redLinedTitleBlockTable = affectedItem.getTable(ItemConstants.TABLE_REDLINETITLEBLOCK);
            final Iterator redlineIter = redLinedTitleBlockTable.iterator();
            while (redlineIter.hasNext())
            {
                IRow row = (IRow) redlineIter.next();
                params = (HashMap) row.getValues();
            }

        }
        return params;
    }


    /**
     * Get Redlined Page Two Attributes from an item
     * 
     * @param IItem Item for which data attributes have to be fetched
     * @param IChange Change from which the data of item has to be fetched
     * @return Map<IItem, IRow>
     * @throws APIException APIException
     */
    public Map<Object, Object> getRedlinedPageTwoAttributesFromItemAsMap(final IItem itemObject,
                                                                         final IChange changeObject)
                                                                             throws APIException
    {
        Map params = new HashMap<>();
        final ITable affectedItemsTable = changeObject.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
        final Iterator iterator = affectedItemsTable.getReferentIterator();

        while (iterator.hasNext())
        {
            final IItem affectedItem = (IItem) iterator.next();
            final ITable redLinedTitleBlockTable = affectedItem.getTable(ItemConstants.TABLE_REDLINEPAGETWO);
            final Iterator redlineIter = redLinedTitleBlockTable.iterator();
            while (redlineIter.hasNext())
            {
                IRow row = (IRow) redlineIter.next();
                params = (HashMap) row.getValues();
            }

        }
        return params;
    }


    /**
     * Get Redlined Page Three Attributes from an item
     * 
     * @param IItem Item for which data attributes have to be fetched
     * @param IChange Change from which the data of item has to be fetched
     * @return Map<IItem, IRow>
     * @throws APIException APIException
     */
    public Map<Object, Object> getRedlinedPageThreeAttributesFromItemAsMap(final IItem itemObject,
                                                                           final IChange changeObject)
                                                                               throws APIException
    {
        Map params = new HashMap<>();
        final ITable affectedItemsTable = changeObject.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
        final Iterator iterator = affectedItemsTable.getReferentIterator();

        while (iterator.hasNext())
        {
            final IItem affectedItem = (IItem) iterator.next();
            final ITable redLinedTitleBlockTable = affectedItem.getTable(ItemConstants.TABLE_REDLINEPAGETHREE);
            final Iterator redlineIter = redLinedTitleBlockTable.iterator();
            while (redlineIter.hasNext())
            {
                IRow row = (IRow) redlineIter.next();
                params = (HashMap) row.getValues();
            }

        }
        return params;
    }


    /**
     * Get all the redlined attributes as a map from the item object
     * 
     * @param IItem Item object for which we have to fetch all the attributes
     * @return Map<Object, Object> All attributes in Map
     * @throws APIException APIException
     */
    public Map<Object, Object> getAllItemRedlinedAttributesFromItemAsMap(final IItem itemObject,
                                                                         final IChange changeObject)
                                                                             throws APIException
    {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.putAll(getRedlinedTitleBlockAttributesFromItemAsMap(itemObject, changeObject));
        params.putAll(getRedlinedPageTwoAttributesFromItemAsMap(itemObject, changeObject));
        params.putAll(getRedlinedPageThreeAttributesFromItemAsMap(itemObject, changeObject));
        return params;
    }


    /**
     * Get all pending changes of an item
     * 
     * @param IItem Item for which pending changes has to be fetched
     * @return List
     * @throws APIException APIException
     */
    public List<IChange> getAllPendingChanges(final IItem itemObject)
        throws APIException
    {
        final List<IChange> pendingChangesList = new ArrayList<IChange>();
        final ITable pendingChangesTable = itemObject.getTable(ItemConstants.TABLE_PENDINGCHANGES);
        final Iterator itr = pendingChangesTable.iterator();
        while (itr.hasNext())
        {
            final IRow row = (IRow) itr.next();
            final IChange changeObj = (IChange) row.getReferent();
            pendingChangesList.add(changeObj);
        }
        return pendingChangesList;
    }


    /**
     * Get all released changes of an item
     * 
     * @param IItem Item for which released changes has to be fetched
     * @return List
     * @throws APIException APIException
     */
    public List<IChange> getAllReleasedChanges(final IItem itemObject)
        throws APIException
    {
        final List<IChange> releasedChangesList = new ArrayList<IChange>();
        final ITable releasedChangesTable = itemObject.getTable(ItemConstants.TABLE_CHANGEHISTORY);
        final Iterator itr = releasedChangesTable.iterator();

        while (itr.hasNext())
        {
            final IRow row = (IRow) itr.next();
            final IChange changeObj = (IChange) row.getReferent();
            releasedChangesList.add(changeObj);
        }
        return releasedChangesList;
    }


    /**
     * Set Redlined Title Block attributes for an item object
     * 
     * @param IItem Item to which the TB attribute needs to be updated
     * @param IChange Change which affected item attributes to be updated
     * @param Map<Object, String> -> Map<AttributeID, AttributeValue>
     * @return Boolean
     * @throws APIException APIException
     */
    public boolean setRedlinedTitleBlockAttributes(final IItem itemObject,
                                                   final IChange changeObject,
                                                   final Map<Object, String> params)
                                                       throws APIException
    {

        if (changeObject != null)
        {
            itemObject.setRevision(changeObject);
            final ITable redlineTBTable = itemObject.getTable(ItemConstants.TABLE_REDLINETITLEBLOCK);
            final Iterator itr = redlineTBTable.iterator();
            while (itr.hasNext())
            {
                IRow row = (IRow) itr.next();
                row.setValues(params);
            }
            return true;

        }
        else
        {
            return false;

        }
    }


    /**
     * Set Redlined Page two attributes for an item object
     * 
     * @param IItem Item to which the P2 attribute needs to be updated
     * @param IChange Change which affected item attributes to be updated
     * @param Map<Object, String> -> Map<AttributeID, AttributeValue>
     * @return Boolean
     * @throws APIException APIException
     */
    public boolean setRedlinedPageTwoAttributes(IItem itemObject,
                                                IChange changeObject,
                                                Map<Object, String> params)
                                                    throws APIException
    {

        if (changeObject != null)
        {
            itemObject.setRevision(changeObject);
            ITable redlineP2Table = itemObject.getTable(ItemConstants.TABLE_REDLINEPAGETWO);
            Iterator itr1 = redlineP2Table.iterator();

            while (itr1.hasNext())
            {
                IRow row = (IRow) itr1.next();
                row.setValues(params);
            }
            return true;
        }
        else
        {
            return false;
        }

    }


    /**
     * Set Redlined Page Three attributes for an item object
     * 
     * @param IItem Item to which the P3 attribute needs to be updated
     * @param IChange Change which affected item attributes to be updated
     * @param Map<Object, String> -> Map<AttributeID, AttributeValue>
     * @return Boolean
     * @throws APIException APIException
     */
    public boolean setRedlinedPageThreeAttributes(IItem itemObject,
                                                  IChange changeObject,
                                                  Map<Object, String> params)
                                                      throws APIException
    {

        if (changeObject != null)
        {
            itemObject.setRevision(changeObject);
            ITable redlineP3Table = itemObject.getTable(ItemConstants.TABLE_REDLINEPAGETHREE);
            Iterator itr1 = redlineP3Table.iterator();

            while (itr1.hasNext())
            {
                IRow row = (IRow) itr1.next();
                row.setValues(params);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    
	/**
	 * Get specific type first level Bom item
	 * 
	 * @param item - Parent item for which Bom table needs to be filtered
	 * @param bomItemType - Item type to be filtered from Bom table.
	 * @return List of IRow containing the filtered rows
	 * @throws APIException
	 */
	public List<IRow> getSpecificFirstLevelBomItem(IItem item, String bomItemType) throws APIException {
		ITable table = item.getTable(ItemConstants.TABLE_BOM);
		String[] args = { bomItemType };
		table = table.where("itemType in %0", args);
		Iterator<IRow> tableIter = table.iterator();
		List<IRow> resultList = new ArrayList<IRow>();
		while (tableIter.hasNext()) {
			IRow iRow = (IRow) tableIter.next();
			resultList.add(iRow);
		}
		return resultList;
	}
}
