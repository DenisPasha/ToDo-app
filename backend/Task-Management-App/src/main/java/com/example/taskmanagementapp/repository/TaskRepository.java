package com.example.taskmanagementapp.repository;

import com.example.taskmanagementapp.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    Optional<Task> findTaskByTitle(String title);
    List<Task> findAll();
}
