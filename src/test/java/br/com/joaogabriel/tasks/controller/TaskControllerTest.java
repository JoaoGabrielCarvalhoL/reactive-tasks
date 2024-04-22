package br.com.joaogabriel.tasks.controller;

import br.com.joaogabriel.tasks.controller.payload.TaskRequest;
import br.com.joaogabriel.tasks.controller.payload.TaskResponse;
import br.com.joaogabriel.tasks.mapper.TaskMapper;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import br.com.joaogabriel.tasks.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.joaogabriel.tasks.model.Task;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;
    @Mock
    private TaskServiceImpl taskService;
    @Mock
    private TaskMapper taskMapper;

    private final String BASE_URL = "/tasks";

    private final Task perishable = Task.builder().withTitle("Test Title")
            .withDescription("Test Description")
            .withPriority(1)
            .withState(TaskState.INSERT)
            .build();


    private final Task persisted = Task.builder()
            .withId(UUID.randomUUID().toString())
            .withTitle("Test Title")
            .withDescription("Test Description")
            .withPriority(1)
            .withState(TaskState.INSERT)
            .build();

    @DisplayName("Given task object, when save task method, then return object persisted from database.")
    @Test
    public void givenAValidTaskObject_whenSaveTask_thenReturnObjectPersistedFromDatabase() {
        TaskResponse response = taskMapper.toTaskResponse(persisted);

        Mockito.when(this.taskMapper.toTask(Mockito.any())).thenReturn(persisted);
        Mockito.when(this.taskMapper.toTaskResponse(Mockito.any())).thenReturn(response);

        Mockito.when(this.taskService.insert(Mockito.any(TaskRequest.class))).thenReturn(Mockito.any());

        WebTestClient client = WebTestClient.bindToController(taskController).build();

        client.post().uri(BASE_URL).bodyValue(perishable).exchange().expectStatus().isCreated();

    }

    @DisplayName("Given a list object, when retrieve all tasks, then return list of a tasks from database.")
    @Test
    public void givenAListOfObject_whenFindAll_thenReturnAListOfTasksPersistedFromDatabase() {
        TaskResponse response = taskMapper.toTaskResponse(persisted);
        Flux<List<TaskResponse>> listFlux = Flux.just(Collections.singletonList(response));
        Mockito.when(taskService.findAll()).thenReturn(listFlux);

        WebTestClient client = WebTestClient.bindToController(taskController).build();
        client.get().uri(BASE_URL).exchange().expectStatus().isOk();

    }

    @DisplayName("Given a valid id, when delete method, then nothing.")
    @Test
    @Disabled
    public void givenAValidId_whenDeleteTask_thenNothingD() {
        Mockito.doNothing().when(taskService).delete(Mockito.any(String.class));
        WebTestClient client = WebTestClient.bindToController(taskController).build();
        client.delete().uri(BASE_URL)
                .attribute("id", UUID.randomUUID().toString())
                .exchange().expectStatus().isNoContent();
    }

    @DisplayName("Given a valid id, when delete method, then nothing.")
    @Test
    public void givenAValidId_whenDeleteTask_thenNothing() {
        Mockito.when(taskService.delete(Mockito.any(String.class))).thenReturn(Mockito.any());
        WebTestClient client = WebTestClient.bindToController(taskController).build();
        client.delete().uri(String.format("%s/%s", BASE_URL, UUID.randomUUID().toString()))
                .attribute("id", UUID.randomUUID().toString())
                .exchange().expectStatus().isNoContent();
    }
}

