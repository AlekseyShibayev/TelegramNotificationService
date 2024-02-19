package com.company.app.core.controller;

import com.company.app.common.tool.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {

    private final LogService logService;

    /**
     * Вытаскивает логи приложения, в виде архива.
     * пример запроса: http://localhost:8080/log/logsAsZip
     */
    @GetMapping(value = "/logsAsZip", produces = "application/zip")
    public ResponseEntity<byte[]> getLogsAsZip() {
        return ResponseEntity.ok(logService.getLogsAsZip());
    }

}
