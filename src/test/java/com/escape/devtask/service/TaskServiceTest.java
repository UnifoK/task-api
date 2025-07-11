package com.escape.devtask.service;

import com.escape.devtask.model.Task;
import com.escape.devtask.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        List<Task> dummyTasks = List.of(
                new Task(1, "Test 1", "Desc 1", false),
                new Task(2, "Test 2", "Desc 2", true)
        );
        when(taskRepository.findAll()).thenReturn(dummyTasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testCreateTask() {
        Task task = new Task(0, "New Task", "Testing create", false);
        when(taskRepository.save(task)).thenReturn(task);

        Task saved = taskService.createTask(task);

        assertEquals("New Task", saved.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void testGetTaskByIdFound() {
        Task task = new Task(1, "Find Me", "Found", true);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1);

        assertTrue(result.isPresent());
        assertEquals("Find Me", result.get().getTitle());
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(99);

        assertFalse(result.isPresent());
    }
}
