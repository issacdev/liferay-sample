package com.dbs.portal.ui.export.report;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.dbs.portal.database.constants.CommonConstant;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.application.XMLApplication;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.pagetable.PagedTableDataType;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IView;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;

public class ExcelReportExporter implements IReportGenertor {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public ExcelReportExporter() {

	}

	@Override
	public byte[] generateReport(Map<String, Object> criteria, Map<String, String> displayMap, List<IView> viewList, IApplication app, IController control) throws Exception{
		if (viewList != null && viewList.size() > 0) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			LanguageChanger changer = app.getLanguageChanger();
			Messages message = changer.getMessages();
			
			HSSFDataFormat df = workbook.createDataFormat();
			HSSFCellStyle dateCS = workbook.createCellStyle();
			dateCS.setDataFormat(df.getFormat(CommonConstant.DATE_FORMAT));
			
			String criteriaSheetName = message.getString("common.subscription.criteria");
			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT);
			for (IView view : viewList){
				if (view instanceof ITableResultView){
					ITableResultView tableView = (ITableResultView)view;

					String sheetName = message.getString(tableView.getId());
					
					Map<String, BigDecimal> totalMap = new LinkedHashMap<String, BigDecimal>();
					Map<String, String> totalMapName = new LinkedHashMap<String, String>();
					List<String> totalColumnId = tableView.getTotalColumnId();
					if (totalColumnId == null){
						totalColumnId = new ArrayList<String>();
					}
					
//					Container container = tableView.getContainer();
					Container container = null;
					
//					if (container == null){
						try{
							
							Class cls = control.getClass();
							Method method = cls.getMethod(tableView.getFunctionName(), Map.class);
							if (method != null){
								List<Map<String, Object>> data = (List<Map<String, Object>>)method.invoke(control, criteria);
								
								TableDBDataProvider dataProvider = new TableDBDataProvider();
								dataProvider.setData(data);
								dataProvider.setDataColumnList(tableView.getVisibleColumnHeader());
								dataProvider.setPagedTableParameterMap(tableView.getPagedTableParameterMap());
								
								container = dataProvider.getDataContainer();
							}
							
						}catch(Exception e){
							logger.error("Error on get table data", e);
						}
//					}
					
					if (container == null){
						throw new Exception();
					}
						
					IndexedContainer provider = (IndexedContainer)container;	
					
					
//					List<Map<String, Object>> data = provider.get
					Object[] visibleColumnId = tableView.getVisibleColumns();
					List<PagedTableDataType> dataTypeList = tableView.getVisibleColumnDataType();
					HSSFSheet sheet = workbook.createSheet(sheetName);
					int rowIndex = 0;
					
					HSSFRow row = sheet.createRow(rowIndex++);
					
					int columnIndex = 0;
					for (int i = 0 ; i < tableView.getOriginalColumnHeader().length ; i++){
						Object headerName = tableView.getOriginalColumnHeader()[i];
						if (dataTypeList.get(i) != PagedTableDataType.CONTROL){
							HSSFCell cell = row.createCell(columnIndex++);
							cell.setCellValue(message.getString((String)headerName));
							if (totalColumnId.contains(tableView.getOriginalColumnDataId()[i])){
								totalMapName.put((String)tableView.getOriginalColumnDataId()[i], message.getString((String)headerName));
							}
						}
					}

					
					int itemIndex = 0;
					Collection itemIds = provider.getItemIds();
					for (Object item : itemIds){
						columnIndex = 0;
						row = sheet.createRow(rowIndex++);
						for (int i = 0 ; i < visibleColumnId.length ; i++){
							Property property = provider.getContainerProperty(item, visibleColumnId[i]);
							if (!(property.getValue() instanceof String)){
								continue;
							}
							String value = (String)property.getValue();
							try{
								switch(dataTypeList.get(i)){
									case STRING : 
										row.createCell(columnIndex).setCellValue(value);
										break;
									case DATE :
										try{
											HSSFCell cell = row.createCell(columnIndex); 
											cell.setCellValue(sdf.parse(value));
											cell.setCellStyle(dateCS);
										}catch(Exception e){
											row.createCell(columnIndex).setCellValue(value);
										}
										break;
									case INTEGER:
										if (totalColumnId.contains(visibleColumnId[i])){
											addToTotal(totalMap, value, (String)visibleColumnId[i]);
										}
										row.createCell(columnIndex).setCellValue(Integer.parseInt(value));
										break;
									case BIGDECIMAL:
										if (totalColumnId.contains(visibleColumnId[i])){
											addToTotal(totalMap, value, (String)visibleColumnId[i]);
										}
										row.createCell(columnIndex).setCellValue(new BigDecimal(value).doubleValue());
										break;
									case LONG:
										if (totalColumnId.contains(visibleColumnId[i])){
											addToTotal(totalMap, value, (String)visibleColumnId[i]);
										}
										row.createCell(columnIndex).setCellValue(Long.parseLong(value));
										break;
									default :
										break;
								}
							}catch(Exception e){
								row.createCell(columnIndex).setCellValue(value);
							}
							columnIndex++;
						}
					}
					
					//add a empty row
					rowIndex++;
					
					Boolean check = Boolean.FALSE;
					for (Iterator it = totalMap.keySet().iterator() ; it.hasNext() ;){
						String key =(String)it.next();
						String label = (String)totalMapName.get(key);
						BigDecimal value = totalMap.get(key);
						
						HSSFRow totalRow = sheet.createRow(rowIndex++);
						totalRow.createCell(0).setCellValue(label);
						totalRow.createCell(1).setCellValue(value.doubleValue());
						check = Boolean.TRUE;
					}
					
					if (check) {
						//add a empty row
						rowIndex++;
						String totalLabel = "Total Count";
						int count = rowIndex - 4 < 0 ? 0 : rowIndex - 4;
						HSSFRow totalRow = sheet.createRow(rowIndex++);
						totalRow.createCell(0).setCellValue(totalLabel);
						totalRow.createCell(1).setCellValue(count);
					}
					
					for (int i = 0 ; i < columnIndex ; i++){
						sheet.autoSizeColumn(i);
					}
				}
			}
			
			if (displayMap != null){
				HSSFSheet criteriaSheet = workbook.createSheet(criteriaSheetName);
				int rowIndex = 0;
				for (Iterator it = displayMap.keySet().iterator() ; it.hasNext(); ){
					String label = (String)it.next();
					String value = (String)displayMap.get(label);
					
					HSSFRow row = criteriaSheet.createRow(rowIndex++);
					row.createCell(0).setCellValue(label);
					row.createCell(1).setCellValue(value);
				}
				
				for (int i = 0 ; i < 2 ; i++){
					criteriaSheet.autoSizeColumn(i);
				}
			}
			
			
			
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			try{
				workbook.write(bo);
				return bo.toByteArray();
			}catch(Exception e){
				logger.error("Error on transform excel report to byte array", e);
			}
			
		}

		return null;

	}
	
	private void addToTotal(Map<String, BigDecimal> totalMap, String value, String dataId){
		if (totalMap.get(dataId) != null){
			totalMap.put(dataId, totalMap.get(dataId).add(new BigDecimal(value)));
		}else{
			totalMap.put(dataId, new BigDecimal(value));
		}
	}

}
