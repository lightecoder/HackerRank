package spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.service.MyService;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping("/retry")
    public ResponseEntity<String> retry() {
        String result;
        try {
            result = myService.retryable();
        } catch (RetryException e) {
            throw new RetryException(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
