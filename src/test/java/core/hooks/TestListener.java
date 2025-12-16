package core.hooks;

import core.driver.DriverFactory;
import core.utils.AllureAttachments;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class TestListener implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        attachEvidence("SUCESSO");
        DriverFactory.quitDriver();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        attachEvidence("FALHA");
        DriverFactory.quitDriver();
    }

    private void attachEvidence(String status) {
        if (DriverFactory.getDriver() != null) {
            AllureAttachments.screenshot(
                    DriverFactory.getDriver(),
                    status
            );
            AllureAttachments.pageSource(
                    DriverFactory.getDriver(),
                    status
            );
        }
    }
}
