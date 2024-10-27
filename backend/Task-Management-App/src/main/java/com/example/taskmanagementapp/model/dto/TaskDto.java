package com.example.taskmanagementapp.model.dto;

import jakarta.persistence.Column;

public class TaskDto {

    private String title;
    private String description;

    public TaskDto() {
    }

    public TaskDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
