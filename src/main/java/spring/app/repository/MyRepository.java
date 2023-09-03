package spring.app.repository;

import org.springframework.retry.RetryException;

public class MyRepository {

    public String call() {
        throw new RetryException("Fail Retry");
    }
}
