package com.gabriel.cleanarch.interfacetask;

import com.gabriel.cleanarch.application.task.TaskService;
import com.gabriel.cleanarch.domain.task.Task;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    /**
     * Returns a list of tasks for the authenticated user.
     * 
     * @return a response containing the list of tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> list(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(taskService.findAllByUser(email));
    }

    /**
     * Creates a new task for the authenticated user.
     * <p>
     * The task's title and description are retrieved from the request body.
     * <p>
     * The task is then saved to the database and returned in the response.
     * <p>
     * If the task is successfully created, a response with a 200 status code is returned containing the created task.
     * @param auth the authenticated user
     * @param request the request containing the task's title and description
     * @return a response containing the created task
     */
    @PostMapping
    public ResponseEntity<Task> create(
            Authentication auth,
            @RequestBody TaskRequest request
    ) {
        String email = auth.getName();
        Task task = taskService.createTask(email, request.getTitle(), request.getDescription());
        return ResponseEntity.ok(task);
    }

    /**
     * Updates a task for the authenticated user.
     * <p>
     * The task's ID is retrieved from the path variable.
     * The task's title, description and done status are retrieved from the request body.
     * <p>
     * The task is then updated in the database and returned in the response.
     * <p>
     * If the task is successfully updated, a response with a 200 status code is returned containing the updated task.
     * @param auth the authenticated user
     * @param id the task's ID
     * @param request the request containing the task's title, description and done status
     * @return a response containing the updated task
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(
            Authentication auth,
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request
    ) {
        String email = auth.getName();
        Task task = taskService.updateTask(
                email,
                id,
                request.getTitle(),
                request.getDescription(),
                request.isDone()
        );
        return ResponseEntity.ok(task);
    }

/**
 * Deletes a task for the authenticated user.
 * <p>
 * The task's ID is retrieved from the path variable.
 * <p>
 * If the task is successfully deleted, a response with a 204 status code is returned.
 * @param auth the authenticated user
 * @param id the task's ID
 * @return a response with a 204 status code
 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication auth, @PathVariable Long id) {
        String email = auth.getName();
        taskService.deleteTask(email, id);
        return ResponseEntity.noContent().build();
    }

    @Data
    public static class TaskRequest {
        @NotBlank
        private String title;
        private String description;
    }

    @Data
    public static class TaskUpdateRequest {
        @NotBlank
        private String title;
        private String description;
        private boolean done;
    }
}
