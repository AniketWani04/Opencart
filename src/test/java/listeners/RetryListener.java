package listeners;

import org.openqa.selenium.StaleElementReferenceException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer {

    private final int maxRetry = 2; // max retries
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        // Get the exception that caused the test to fail
        Throwable cause = result.getThrowable();

        // Retry only for Selenium exceptions
        if (cause != null &&
                (cause instanceof StaleElementReferenceException
                        /* add more exceptions if needed */
                )) {

            if (retryCount < maxRetry) {
                retryCount++;
                System.out.println(result.getName() + " failed due to " + cause +
                        ". Retry " + retryCount + " of " + maxRetry);
                return true; // rerun test
            }
        }

        // Don't retry for other exceptions (like assertion failures)
        return false;
    }
}