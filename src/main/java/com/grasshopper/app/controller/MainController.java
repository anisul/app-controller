package com.grasshopper.app.controller;

import com.grasshopper.app.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MainController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/copy-file")
    public ResponseEntity<String> copyApplication(@RequestParam(name = "fileName") String fileName) {
        log.info(fileName + " is requested to copy...");
        applicationService.extractZip(fileName);
        return ResponseEntity.ok()
                .body("Successfully copied file " + fileName);
    }

    @GetMapping("/execute")
    public ResponseEntity<String> execute(@RequestParam(name = "fileName") String fileName) {
        log.info(fileName + " is requested to get executed...");
        applicationService.execute(fileName);
        return ResponseEntity.ok()
                .body("Successfully executed file " + fileName);
    }

    @GetMapping("/copy-and-execute")
    public ResponseEntity<String> copyAndExecute(@RequestParam(name = "fileName") String fileName) {
        log.info(fileName + " is requested to copy and get executed...");
        applicationService.extractZip(fileName);
        applicationService.execute(fileName);
        return ResponseEntity.ok()
                .body("Successfully copied and executed file " + fileName);
    }

    @GetMapping("/active-processes")
    public ResponseEntity<Map<String, Long>> getActiveProcesses() {
        return ResponseEntity.ok()
                .body(applicationService.getActiveProcess());
    }

    @GetMapping("/terminate")
    public ResponseEntity<String> terminate(@RequestParam(name = "fileName") String fileName) {
        if (applicationService.terminate(fileName)) {
            return ResponseEntity.ok().body("Successfully terminated process attached with file: " + fileName);
        }

        return ResponseEntity.ok()
                .body("No such process attached with file: " + fileName);
    }
}
