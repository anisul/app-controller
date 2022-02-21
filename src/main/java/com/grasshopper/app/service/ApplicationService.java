package com.grasshopper.app.service;

import java.util.Map;

public interface ApplicationService {
    void extractZip(String fileName);
    void execute(String fileName);
    Map<String, Long> getActiveProcess();
    boolean terminate(String fileName);
}
