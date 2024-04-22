package br.com.joaogabriel.tasks.repository;

import br.com.joaogabriel.tasks.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
