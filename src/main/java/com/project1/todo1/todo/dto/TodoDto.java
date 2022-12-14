package com.project1.todo1.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TodoDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "내용은 공백이 아니어야 합니다.")
        private String title;

        @NotNull(message = "순서는 공백이 아니어야 합니다.")
        private long order;

        @NotNull
        private boolean completed;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        @NotNull
        private long todoId;

        @NotBlank(message = "내용은 공백이 아니어야 합니다.")
        private String title;

        @NotNull(message = "순서는 공백이 아니어야 합니다.")
        private long order;

        @NotNull
        private boolean completed;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long todoId;

        private String title;

        private long order;

        private boolean completed;
    }
}
