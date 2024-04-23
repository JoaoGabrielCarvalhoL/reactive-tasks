package br.com.joaogabriel.tasks.service;

import br.com.joaogabriel.tasks.controller.payload.TaskRequest;
import br.com.joaogabriel.tasks.controller.payload.TaskResponse;
import br.com.joaogabriel.tasks.model.Task;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TaskService {

    Mono<TaskResponse> insert(TaskRequest task);

    Flux<List<TaskResponse>> findAll();


    Mono<Page<TaskResponse>> findAllPaginated(String id, String title, String description, int priority, TaskState state, Integer pageNumber, Integer pageSize);

    Mono<Void> delete(String id);

    Mono<TaskResponse> start(String id, String zipCode);
}
