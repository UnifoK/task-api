package com.escape.devtask.repository;

import com.escape.devtask.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    // Custom methods later (like search by title)
    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    @Query("SELECT t FROM Task t WHERE t.title LIKE %:keyword%")
    List<Task> searchByTitle(@Param("keyword") String keyword);

    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> searchByTitleOrDescription(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM task WHERE title LIKE %:keyword%", nativeQuery = true)
    List<Task> nativeSearch(@Param("keyword") String keyword);


}