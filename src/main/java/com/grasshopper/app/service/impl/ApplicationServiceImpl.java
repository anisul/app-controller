package com.grasshopper.app.service.impl;

import com.grasshopper.app.service.ApplicationService;
import com.grasshopper.app.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    @Value("${application.files.source}")
    String filesSource;

    @Value("${application.files.destination}")
    String filesDestination;

    @Override
    public void copyFile(String fileName) {
        log.info("copying file: " + fileName +
                ", source: " + filesSource +
                ", destination: " + filesDestination);

        try {
            FileUtils.extractZip(filesSource + "\\" + fileName, filesDestination);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("copy was successful");
    }
}
