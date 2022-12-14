package com.project1.todo1.todo.repository;

import com.project1.todo1.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
