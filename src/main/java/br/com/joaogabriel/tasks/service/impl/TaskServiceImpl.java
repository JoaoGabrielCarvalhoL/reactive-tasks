package br.com.joaogabriel.tasks.service.impl;

import br.com.joaogabriel.tasks.controller.payload.TaskRequest;
import br.com.joaogabriel.tasks.controller.payload.TaskResponse;
import br.com.joaogabriel.tasks.mapper.TaskMapper;
import br.com.joaogabriel.tasks.model.Task;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import br.com.joaogabriel.tasks.repository.TaskCustomRepository;
import br.com.joaogabriel.tasks.repository.TaskRepository;
import br.com.joaogabriel.tasks.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskCustomRepository taskCustomRepository;
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository, TaskCustomRepository taskCustomRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.taskCustomRepository = taskCustomRepository;
    }

    @Override
    public Mono<TaskResponse> insert(TaskRequest task) {
        return Mono.just(task)
                .map(taskMapper::toTask)
                .map(Task::insert)
                .doOnNext(saved -> logger.log(Level.INFO, "Saving Task {1} into database.", saved.getId()))
                .flatMap(this::save)
                .doOnError(error -> logger.log(Level.INFO, "Something wrong... Cannot save task {1}", error))
                .map(taskMapper::toTaskResponse);
    }

    private Mono<Task> save(Task task) {
        return Mono.just(task)
                .map(taskRepository::save);
    }

    @Override
    public Flux<List<TaskResponse>> findAll() {
        return Flux.just(this.taskRepository
                .findAll()
                .stream()
                .map(taskMapper::toTaskResponse).toList());
    }

    @Override
    public Flux<Page<TaskResponse>> findAllPaginated(String id, String title, String description, int priority,
                                               TaskState state, Integer pageNumber, Integer pageSize) {
        return Flux.just(taskCustomRepository.findAllPaginated(id, title, description, priority, state, pageNumber, pageSize));
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.fromRunnable(() -> this.taskRepository.deleteById(id));

    }

}
