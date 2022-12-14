package com.project1.todo1.todo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1.todo1.function.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Todos")
@NoArgsConstructor
public class TodoEntity extends Auditable {
    // todo id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("todo_id")
    private long todoId;

    // todo 명
    @Column(nullable = false, updatable = false)
    private String title;

    // todo 순서
    @Column(name = "orders", nullable = false)
    private long order;

    // todo 완료했는지
    @Column(nullable = false)
    private boolean completed;

    public TodoEntity(String title, int order, boolean completed) {
        this.title = title;
        this.order = order;
        this.completed = completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
