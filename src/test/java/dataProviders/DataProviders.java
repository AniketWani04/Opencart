package dataProviders;

import org.testng.annotations.DataProvider;
import utils.ExcelUtil;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "registerData")
    public Object[][] sendDate() {
        Object[][] data = {
                {"Jonty", "Rodes", "jonty@gmail.com", "Jonty@123", true, true},
                {"Ricky", "Ponting", "ricky@gmail.com", "Ricky@123", true, true},
        };
        return data;
    }


    @DataProvider(name = "loginData")
    public Object[][] getData() throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/resources/testdata/loginData.xlsx";
        ExcelUtil excelUtil = new ExcelUtil(path, "Sheet1");
        return excelUtil.getExcelData();
    }
}
