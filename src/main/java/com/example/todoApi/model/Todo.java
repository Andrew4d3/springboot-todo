package com.example.todoApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Todo {
    private final UUID id;
    private String description;
    private boolean done;
    private final Date created_at;
    private Date updated_at;

    public Todo(UUID id, String description, boolean done, Date created_at, Date updated_at) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Todo(@JsonProperty("description") String description, @JsonProperty("done") boolean done) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.done = done;
        this.created_at = null;
        this.updated_at = null;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

}
