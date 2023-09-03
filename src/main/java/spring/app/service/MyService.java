package spring.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import spring.app.repository.MyRepository;

@Service
public class MyService {

    @Autowired
    private MyRepository myRepository;

    @Retryable(
            retryFor = {RetryException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public String retryable() {
        return myRepository.call();
    }
}
