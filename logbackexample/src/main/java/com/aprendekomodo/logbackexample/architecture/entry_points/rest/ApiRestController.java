package com.aprendekomodo.logbackexample.architecture.entry_points.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiRestController {

    private static final Logger logger = LoggerFactory.getLogger(ApiRestController.class);

    @Value("${warkub.context}")
    private String context;

    @GetMapping
    public ResponseEntity<String> getOk(){
        MDC.put("context", context);
        MDC.put("classMethod", "ApiRestController.getOk");
        logger.info("Response OK");
        return ResponseEntity.ok("OK");
    }

}

