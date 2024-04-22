package br.com.joaogabriel.tasks.repository;

import br.com.joaogabriel.tasks.controller.payload.TaskResponse;
import br.com.joaogabriel.tasks.mapper.TaskMapper;
import br.com.joaogabriel.tasks.model.Task;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class TaskCustomRepository {

    private final MongoOperations mongoOperations;
    private final TaskMapper taskMapper;

    public TaskCustomRepository(MongoOperations mongoOperations, TaskMapper taskMapper) {
        this.mongoOperations = mongoOperations;
        this.taskMapper = taskMapper;
    }

    public Page<TaskResponse> findAllPaginated(String id, String title,
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

        Page<Task> page = PageableExecutionUtils.getPage(mongoOperations.find(query, Task.class), pageable,
                () -> mongoOperations.count(query, Task.class));
        List<TaskResponse> list = page.stream().map(taskMapper::toTaskResponse).toList();
        return new PageImpl<TaskResponse>(list, pageable, list.size());

    }

}
