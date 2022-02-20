package com.grasshopper.app.controller;

import com.grasshopper.app.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MainController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/copy-file")
    public void copyApplication(@RequestParam(name = "fileName") String fileName) {
        log.info(fileName + " is requested to copy...");
        applicationService.copyFile(fileName);
    }
}
