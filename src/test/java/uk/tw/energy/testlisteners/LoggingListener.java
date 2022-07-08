package uk.tw.energy.testlisteners;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class LoggingListener implements TestExecutionListener {

    private final BiConsumer<Throwable, Supplier<String>> logger;

    public LoggingListener() {
        this.logger = (throwable, stringSupplier) -> {
            System.out.println(stringSupplier.get());
            if(throwable != null){
                throwable.printStackTrace();
            }
        };
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        log("On %s -> Execution Started: %s - %s", System.getProperty("org.gradle.test.worker"), testIdentifier.getDisplayName(), testIdentifier.getUniqueId());
    }

    private void log(String message, Object... args) {
        logWithThrowable(message, null, args);
    }

    private void logWithThrowable(String message, Throwable t, Object... args) {
        this.logger.accept(t, () -> String.format(message, args));
    }

}
