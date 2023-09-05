package spring.app.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.RetryException;
import spring.app.repository.MyRepository;

@SpringBootTest
class MyServiceTest {

    @InjectMocks
    private MyService myService;

    @Mock
    private MyRepository myRepository;

    @Test
    void testRetryableMethod() {
        // Mock the behavior of myRepository.call() to throw RetryException
        Mockito.when(myRepository.call()).thenThrow(new RetryException("Fail Retry"));

        // Call the retryable method
        try {
            myService.retryable();
        } catch (RetryException e) {
            // Handle the exception if needed
        }

        // Verify that myRepository.call() was called exactly 3 times
        Mockito.verify(myRepository, Mockito.times(3)).call();
    }
}