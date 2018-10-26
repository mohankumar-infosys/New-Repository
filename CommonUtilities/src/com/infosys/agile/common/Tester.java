package com.infosys.agile.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.agile.api.IQuery;
import com.agile.api.IStatus;
import com.agile.api.ITable;
import com.agile.api.IWorkflow;

public class Tester {

	public static void main(String[] args) {
		Tester test = new Tester();
		//test.testSplitMethod();
		//test.testExcelToListMethod();
		test.testCheckIfNumber();
	}
	
	public void testSplitMethod(){
		List<String> input = new ArrayList<String>();
		input.add("a");
		input.add("b");
		input.add("c");
		input.add("d");
		input.add("e");
		input.add("f");
		input.add("g");
		
		
		List<List<Object>> result = Utility.splitListObject((List)input, 2, false);
		
		System.out.println(input.toString());
		System.out.println(result.toString());
		
		result.get(2).add("x");
		//input.add("x");
		
		System.out.println(input.toString());
		System.out.println(result.toString());
		
	}
	
	public void testExcelToListMethod(){
		try {
			List result = Utility.readExcelDataAsListObject("D:\\Common Util\\New folder\\Common Wrapper Utility.xlsx", "Agile Session", 5);
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testCheckIfNumber(){
		
		System.out.println(Utility.checkIfInputDataIsNumeric("125645"));
		System.out.println(Utility.checkIfInputDataIsNumeric("12446.000177"));
		System.out.println(Utility.checkIfInputDataIsNumeric("12446.000277"));
		System.out.println(Utility.checkIfInputDataIsNumeric("12446.000377"));
		System.out.println(Utility.checkIfInputDataIsNumeric("-12.874600"));
		System.out.println(Utility.checkIfInputDataIsNumeric(".129874646"));
		System.out.println(Utility.checkIfInputDataIsNumeric("+12624685"));
		System.out.println(Utility.checkIfInputDataIsNumeric("12A"));
		System.out.println(Utility.checkIfInputDataIsNumeric("12-"));
		
	}
	
	public void testIQuery(){
		IAgileSession session = null;
		try {
			IQuery query = (IQuery) session.createObject(IQuery.OBJECT_TYPE, "");
			query = (IQuery) session.getObject(IQuery.OBJECT_TYPE, "\\Global Search\\Query_name");
			IChange change = null;
			IWorkflow workflow = change.getWorkflow();
			IStatus[] states = workflow.getStates();
			IStatus status = states[1];
			query.setCriteria("", new Object[]{status});
			Stream str = query.execute().parallelStream();
			
			
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
