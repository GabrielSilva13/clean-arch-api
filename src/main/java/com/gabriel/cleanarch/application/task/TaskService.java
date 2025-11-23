package com.gabriel.cleanarch.application.task;

import com.gabriel.cleanarch.domain.task.Task;
import com.gabriel.cleanarch.domain.task.TaskRepository;
import com.gabriel.cleanarch.domain.user.User;
import com.gabriel.cleanarch.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> findAllByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return taskRepository.findByOwner(user);
    }

    public Task createTask(String userEmail, String title, String description) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = Task.builder()
                .title(title)
                .description(description)
                .done(false)
                .createdAt(LocalDateTime.now())
                .owner(user)
                .build();

        return taskRepository.save(task);
    }

    public Task updateTask(String userEmail, Long id, String title, String description, boolean done) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        if (!task.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Não autorizado");
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setDone(done);
        return taskRepository.save(task);
    }

    public void deleteTask(String userEmail, Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        if (!task.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Não autorizado");
        }

        taskRepository.delete(task);
    }
}
