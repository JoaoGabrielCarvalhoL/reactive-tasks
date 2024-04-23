package br.com.joaogabriel.tasks.controller;

import br.com.joaogabriel.tasks.controller.payload.TaskRequest;
import br.com.joaogabriel.tasks.controller.payload.TaskResponse;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import br.com.joaogabriel.tasks.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Flux<List<TaskResponse>>> getTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Mono<TaskResponse>> save(@RequestBody TaskRequest task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskService.insert(task));
    }

    @GetMapping("/paginated")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Mono<Page<TaskResponse>>>
    findAllTasksWithPagination(@RequestParam(required = false, name = "id") String id,
                               @RequestParam(required = false, name = "title") String title,
                               @RequestParam(required = false, name = "description") String description,
                               @RequestParam(required = false, name = "priority", defaultValue = "0") int priority,
                               @RequestParam(required = false, name = "state") TaskState state,
                               @RequestParam(required = false, name = "pageNumber", defaultValue = "0") Integer pageNumber,
                               @RequestParam(required = false, name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.taskService.findAllPaginated(id, title, description, priority, state, pageNumber, pageSize));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Mono<Void>> delete(@PathVariable("id") String id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
