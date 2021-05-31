package pl.zzpj.spacer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadinessController {

    private static final String READY_MESSAGE = "Ready";

    @GetMapping("/readiness")
    public ResponseEntity<String> ready() {
        return ResponseEntity.status(HttpStatus.OK).body(READY_MESSAGE);
    }
}
