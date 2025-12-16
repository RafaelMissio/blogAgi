package core.hooks;

import base.BaseTest;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.*;

public class TestListener implements TestWatcher, BeforeTestExecutionCallback {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof BaseTest baseTest) {
            saveScreenshot(baseTest.takeScreenshot());
        }
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot) {
        return screenshot;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        // Poderia logar início do teste, anexar info de ambiente etc.
    }
}
