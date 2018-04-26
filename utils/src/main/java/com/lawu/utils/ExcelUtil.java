package com.lawu.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author jiangxinjun
 * @date 2017年9月22日
 */
public class ExcelUtil {
    
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	
    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public static List<List<String>> readExcel(String path, int fristRowNum, int lastCellNum) throws IOException {
        if (path == null || "".equals(path)) {
            return null;
        } else {
            String postfix = getPostfix(path);
            if (!"".equals(postfix)) {
                if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path, fristRowNum, lastCellNum);
                } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path, fristRowNum, lastCellNum);
                }
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
	public static List<List<String>> readXlsx(String path, int fristRowNum, int lastCellNum) throws IOException {
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<List<String>> list = new ArrayList<>();
        List<String> data = null;
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = fristRowNum; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            	XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                	if (xssfRow.getCell(0) == null) {
        				continue;
        			}
                	data = new ArrayList<>();
                	for (int cellNum = 0; cellNum <= lastCellNum; cellNum++) {
                		XSSFCell cell = xssfRow.getCell(cellNum);
                		if (cell != null) {
                			data.add(cell.getStringCellValue());
                		} else {
                			data.add(null);
                		}
                	}
                	list.add(data);
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
	public static List<List<String>> readXls(String path, int fristRowNum, int lastCellNum) throws IOException {
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<List<String>> list = new ArrayList<>();
        List<String> data = new ArrayList<>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = fristRowNum; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	if (hssfRow.getCell(0) == null) {
        				continue;
        			}
                	data = new ArrayList<>();
                	for (int cellNum = 0; cellNum <= lastCellNum; cellNum++) {
                		HSSFCell cell = hssfRow.getCell(cellNum);
                		if (cell != null) {
                			data.add(cell.getStringCellValue());
                		} else {
                			data.add(null);
                		}
                	}
                	list.add(data);
                }
            }
        }
        return list;
    }
    
    /**
     * get postfix of the path
     * @param path
     * @return
     */
    private static String getPostfix(String path) {
        if (path == null || "".equals(path.trim())) {
            return "";
        }
        if (path.contains(".")) {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
        return "";
    }
}