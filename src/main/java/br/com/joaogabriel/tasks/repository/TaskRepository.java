package br.com.joaogabriel.tasks.repository;

import br.com.joaogabriel.tasks.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
