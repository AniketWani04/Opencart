package listeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;  //UI of the report
    public ExtentReports extent;  // populate common info on the report

    // Thread-safe ExtentTest
    ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    String repName;

    public void onStart(ITestContext testContext) {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("opencart Automation Report");
        sparkReporter.config().setReportName("opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", "Aniket");
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result) {

        test.set(extent.createTest(result.getTestClass().getName()));
        test.get().assignCategory(result.getMethod().getGroups());
        test.get().log(Status.PASS, result.getName() + " got successfully executed");
    }

    /*public void onTestFailure(ITestResult result) {

        test.set(extent.createTest(result.getTestClass().getName()));
        test.get().assignCategory(result.getMethod().getGroups());

        test.get().log(Status.FAIL, result.getName() + " got failed");
        test.get().log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseTest().captureScreen(result.getName());
            test.get().addScreenCaptureFromPath(imgPath);
        }
        catch(IOException e1) {
            e1.printStackTrace();
        }
    }*/

    public void onTestFailure(ITestResult result) {
        // Get the actual WebDriver from the running test
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        // Create a thread-safe ExtentTest
        ExtentTest extentTest = extent.createTest(result.getTestClass().getName());
        test.set(extentTest);  // assign it to ThreadLocal

        // Assign category and log failure
        test.get().assignCategory(result.getMethod().getGroups());
        test.get().log(Status.FAIL, result.getName() + " got failed");
        test.get().log(Status.INFO, result.getThrowable().getMessage());

        try {
            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            String targetPath = System.getProperty("user.dir") + "\\screenshots\\" + result.getName() + " " + timeStamp + ".png";
            File targetFile = new File(targetPath);
            src.renameTo(targetFile);

            // Attach screenshot to report
            test.get().addScreenCaptureFromPath(targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {

        test.set(extent.createTest(result.getTestClass().getName()));
        test.get().assignCategory(result.getMethod().getGroups());

        test.get().log(Status.SKIP, result.getName() + " got skipped");
        test.get().log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context) {

        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}