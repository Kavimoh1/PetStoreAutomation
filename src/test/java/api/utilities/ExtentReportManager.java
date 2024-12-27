package api.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    public ExtentReports extent;
    public ExtentTest test;
    public ExtentSparkReporter sparkReporter;
    String repName;

    @BeforeClass
    public void onStart(ITestContext context) {
        System.out.println("Extent Report Initialization Started");
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timestamp + ".html";

        // Ensure directory exists
        File reportDir = new File("./Reports/");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        // Initialize ExtentSparkReporter
        sparkReporter = new ExtentSparkReporter("./Reports/" + repName);
        sparkReporter.config().setReportName("Pet store user api");
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
        sparkReporter.config().setTheme(Theme.DARK);

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set system information
        extent.setSystemInfo("App", "Your Name");
        extent.setSystemInfo("Application", "Pet store user api");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Mohan");
        System.out.println("Extent Report Initialized Successfully");
    }

    @Test
    public void sampleTestPass(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, "Test Passed");
    }

    @Test
    public void sampleTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    @Test
    public void sampleTestSkip(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed: " + result.getName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Failed: " + result.getName());
            test.fail(result.getThrowable());
            String screenshotPath = captureScreenshot(result.getName());
            test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test Skipped: " + result.getName());
            test.skip(result.getThrowable());
        }
    }

    @AfterClass
    public void onFinish(ITestContext testContext) {
        System.out.println("Finalizing Extent Report");
        extent.flush();
        System.out.println("Extent Report Finalized");
    }

    private String captureScreenshot(String testName) {
        String screenshotPath = "./Reports/screenshots/" + testName + ".png";
        File screenshotDir = new File("./Reports/screenshots/");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        try {
            File dummyFile = new File(screenshotPath);
            if (!dummyFile.exists()) {
                dummyFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
}
