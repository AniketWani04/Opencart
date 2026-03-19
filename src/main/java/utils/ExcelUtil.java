package utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtil {
    public static FileInputStream fi;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;

    static String xlFile;
    static String xlSheet;

    public ExcelUtil(String xlFile, String xlSheet) {
        this.xlFile = xlFile;
        this.xlSheet = xlSheet;
    }
    /*public static int getRowCount() throws IOException {
        fi = new FileInputStream(xlFile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlSheet);
        int rowCount = ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowCount;
    }*/

    public Object[][] getExcelData() throws IOException {
        fi = new FileInputStream(xlFile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlSheet);
        int rows = ws.getLastRowNum();

        Object[][] data = new Object[rows][3];
        for(int i = 1; i <= rows; i++) {
            data[i-1][0] = ws.getRow(i).getCell(0).toString();
            data[i - 1][1] = ws.getRow(i).getCell(1).toString();
            data[i - 1][2] = ws.getRow(i).getCell(2).toString();
        }
        return data;
    }

}
