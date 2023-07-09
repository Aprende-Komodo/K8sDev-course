package com.aprendekomodo.Log4j2Example.architecture.entrypoints.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiRestController {

    private static final Logger logger = LogManager.getLogger(ApiRestController.class);

    @Value("${warkub.context}")
    private String context;

    @Value("${warkub.filename}")
    private String fileName;

    @Value("${warkub.filepath}")
    private String filePath;

    @GetMapping
    public ResponseEntity<String> getMessage() throws IOException {
        MDC.put("context", context);
        MDC.put("classMethod", "ApiRestController.getMessage");

        Path fullPath = Paths.get(filePath, fileName);
        logger.info("fullPath {}",fullPath.toAbsolutePath());

        String content = (new String(Files.readAllBytes(fullPath))).strip();
        logger.info("content {}",content);

        ObjectMapper mapper = new ObjectMapper();
        List<String> message = mapper.readValue(content, ArrayList.class);
        logger.info("message {}",message);

        return ResponseEntity.ok(formatMessage(message));
    }

    private String formatMessage(List<String> message){
        return message.stream()
                .reduce((newMessage, partMessage)->newMessage.concat(" ").concat(partMessage))
                .orElse("NO ENCONTRADO");
    }

}

