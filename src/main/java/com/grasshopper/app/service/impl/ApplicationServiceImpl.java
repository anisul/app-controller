package com.grasshopper.app.service.impl;

import com.grasshopper.app.client.InfoApiClient;
import com.grasshopper.app.service.ApplicationService;
import com.grasshopper.app.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private static Map<String, Long> ACTIVE_PROCESS_ID_MAP = new HashMap<>();

    @Value("${application.files.source}")
    String filesSource;

    @Value("${application.files.destination}")
    String filesDestination;

    @Autowired
    InfoApiClient infoApiClient;

    @Override
    public void extractZip(String fileName) {
        log.info("copying file: " + fileName +
                ", source: " + filesSource +
                ", destination: " + filesDestination);

        // we need to fetch file info from info api
        //String fileName2 = infoApiClient.call();

        try {
            FileUtils.extractZip(filesSource + "\\" + fileName, filesDestination);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("copy was successful");
    }

    @Override
    public void execute(String fileName) {
        File candidateFile = new File(filesDestination + "/" + fileName);

        if (ACTIVE_PROCESS_ID_MAP.containsKey(fileName)) {
            terminateActiveProcess(fileName);
            doExecute(fileName, candidateFile);
        } else {
            doExecute(fileName, candidateFile);
        }
    }

    @Override
    public Map<String, Long> getActiveProcess() {
        return ACTIVE_PROCESS_ID_MAP;
    }

    @Override
    public boolean terminate(String fileName) {
        if (ACTIVE_PROCESS_ID_MAP.containsKey(fileName)) {
            terminateActiveProcess(fileName);
            return true;
        }
        return false;
    }

    private void doExecute(String fileName, File candidateFile) {
        try {
            Process process = Runtime.getRuntime().exec(fileName, null, candidateFile);
            registerProcess(process, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void terminateActiveProcess(String fileName) {
        Long pid = ACTIVE_PROCESS_ID_MAP.get(fileName);

        String[] command = { "taskkill", "/F", "/T", "/PID", Long.toString(pid) };
        if (System.getProperty("os.name").startsWith("Linux")) {
            command = new String[]{ "kill", "-9", Long.toString(pid) };
        }

        try {
            Process killer = Runtime.getRuntime().exec(command);
            int result = killer.waitFor();
            log.info("Killed pid " + pid + " exitValue: " + result);

        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private void registerProcess(Process process, String fileName) {
        ACTIVE_PROCESS_ID_MAP.put(fileName, process.pid());
    }
}
