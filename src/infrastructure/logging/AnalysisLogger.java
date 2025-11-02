package infrastructure.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnalysisLogger {
    private static AnalysisLogger instance;
    private final DateTimeFormatter formatter;
    private boolean debugEnabled = false;

    private AnalysisLogger() {
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static AnalysisLogger getInstance() {
        if (instance == null) {
            instance = new AnalysisLogger();
        }
        return instance;
    }

    public void enableDebug() {
        this.debugEnabled = true;
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void warning(String message) {
        log("WARN", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }

    public void debug(String message) {
        if (debugEnabled) {
            log("DEBUG", message);
        }
    }

    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.printf("[%s] %s: %s%n", timestamp, level, message);
    }
}