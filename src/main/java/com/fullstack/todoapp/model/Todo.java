package com.fullstack.todoapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "todos")
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private Boolean status;

    public Todo(String title, String description, Boolean status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Todo() {
    }
}
