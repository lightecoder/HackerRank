package spring.app.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.retry.RetryException;
import spring.app.repository.MyRepository;
import spring.app.service.MyService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MyControllerTest {

    @SpyBean
    private MyService myService;
    @MockBean
    private MyRepository myRepository;
    @Autowired
    private MyController sut;

    @Test
    void testRetry() {
        Mockito.when(myRepository.call())
                .thenThrow(new RetryException("Fail Retry 1"))
                .thenThrow(new RetryException("Fail Retry 2"))
                .thenReturn("Success!");

        sut.retry();

        Mockito.verify(myRepository, Mockito.times(3)).call();

    }

    @Test
    void testRetry2() {
        Mockito.when(myRepository.call())
                .thenThrow(new RetryException("Fail Retry 1"))
                .thenThrow(new RetryException("Fail Retry 2"))
                .thenThrow(new RetryException("Fail Retry 3"));

        RetryException exception = assertThrows(RetryException.class, () -> {
            sut.retry();
        });

        Mockito.verify(myRepository, Mockito.times(3)).call();
        assertEquals("Fail Retry 3", exception.getMessage());
    }

}