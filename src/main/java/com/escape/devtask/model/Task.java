package com.escape.devtask.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Task {
    private int id;
    private String title;
    private String description;
    private boolean completed;
}
