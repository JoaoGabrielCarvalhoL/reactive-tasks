package br.com.joaogabriel.tasks.repository;

import br.com.joaogabriel.tasks.controller.payload.TaskResponse;
import br.com.joaogabriel.tasks.mapper.TaskMapper;
import br.com.joaogabriel.tasks.model.Task;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class TaskReactiveCustomRepository {

    private final ReactiveMongoOperations reactiveMongoOperations;
    private final TaskMapper taskMapper;

    public TaskReactiveCustomRepository(ReactiveMongoOperations reactiveMongoOperations, TaskMapper taskMapper) {
        this.reactiveMongoOperations = reactiveMongoOperations;
        this.taskMapper = taskMapper;
    }

    public Mono<Page<TaskResponse>> findAllPaginated(String id, String title,
                                                     String description, int priority,
                                                     TaskState state, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "title");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("priority", "state");

        Task task = Task.builder()
                .withId(id)
                .withTitle(title)
                .withDescription(description)
                .withPriority(priority)
                .withState(state)
                .build();

        Example<Task> example = Example.of(task, matcher);
        Query query = Query.query(Criteria.byExample(example)).with(pageable);

        if(task.getPriority() > 0) {
            query.addCriteria(Criteria.where("priority").is(task.getPriority()));
        }

        if (task.getState() != null) {
            query.addCriteria(Criteria.where("state").is(task.getState()));
        }

        return reactiveMongoOperations.find(query, Task.class).collectList()
                .zipWith(reactiveMongoOperations.count(Query.query(Criteria.byExample(example)), Task.class))
                .map(tuple -> PageableExecutionUtils.getPage(tuple.getT1().stream()
                        .map(taskMapper::toTaskResponse).toList(), pageable, tuple::getT2));

    }

}
