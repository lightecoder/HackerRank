package spring.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.app.repository.MyRepository;

@Configuration
public class MyConfig {

    @Bean
    public MyRepository myRepository(){
        return new MyRepository();
    }
}
