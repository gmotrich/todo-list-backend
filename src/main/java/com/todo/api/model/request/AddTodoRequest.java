package com.todo.api.model.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTodoRequest {
    private String content;
    private LocalDate start;
    private LocalDate finish;
}
