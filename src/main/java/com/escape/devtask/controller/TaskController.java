package com.escape.devtask.controller;
import com.escape.devtask.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Optional: allow frontend access
public class TaskController {

    private final Map<Integer, Task> taskRepo = new HashMap<>();
    private int currentId = 1;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setId(currentId++);
        taskRepo.put(task.getId(), task);
        return task;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        Task task = taskRepo.get(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        Task removed = taskRepo.remove(id);
        if (removed == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.ok("Task deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        Task existingTask = taskRepo.get(id);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        return ResponseEntity.ok(existingTask);
    }



    @GetMapping
    public Collection<Task> getAllTasks() {
        return taskRepo.values();
    }
}