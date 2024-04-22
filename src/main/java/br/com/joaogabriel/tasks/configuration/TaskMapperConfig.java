package br.com.joaogabriel.tasks.configuration;

import br.com.joaogabriel.tasks.mapper.TaskMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskMapperConfig {

    @Bean
    public TaskMapper taskMapper() {
        return new TaskMapper();
    }
}
