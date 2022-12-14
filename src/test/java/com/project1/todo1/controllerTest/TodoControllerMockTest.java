package com.project1.todo1.controllerTest;

import com.google.gson.Gson;
import com.project1.todo1.todo.controller.TodoController;
import com.project1.todo1.todo.dto.TodoDto;
import com.project1.todo1.todo.entity.TodoEntity;
import com.project1.todo1.todo.mapper.TodoMapper;
import com.project1.todo1.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
public class TodoControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private TodoService todoService;

    @MockBean
    private TodoMapper mapper;

    @Test
    void postTodoTest() throws Exception {
        // given
        TodoDto.Post post = new TodoDto.Post("studying",1,false);
        TodoDto.Response response = new TodoDto.Response(1,"studying",1,false);

        //TodoEntity todo = mapper.todoPostToTodo(post);
        given(todoService.createTodo(Mockito.any())).willReturn(new TodoEntity());
        given(mapper.todoToTodoResponse(Mockito.any(TodoEntity.class))).willReturn(response);

        String content = gson.toJson(post);

        // when
        ResultActions actions =
                mockMvc.perform(post("/todos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.order").value(post.getOrder()))
                .andExpect(jsonPath("$.completed").value(post.isCompleted()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void patchTodoTest() throws Exception {
        // given
        TodoDto.Patch patch = new TodoDto.Patch(1, "sleeping", 1, false);
        TodoDto.Response response = new TodoDto.Response(1, "sleeping", 1, false);

        given(mapper.todoPatchToTodo(Mockito.any(TodoDto.Patch.class))).willReturn(new TodoEntity());
        given(todoService.updateTodo(Mockito.any(TodoEntity.class))).willReturn(new TodoEntity());
        given(mapper.todoToTodoResponse(Mockito.any(TodoEntity.class))).willReturn(response);

        String content = gson.toJson(patch);

        // when
        ResultActions actions =
                mockMvc.perform(patch("/todos/" + patch.getTodoId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        MvcResult result = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(patch.getTitle()))
                .andExpect(jsonPath("$.order").value(patch.getOrder()))
                .andExpect(jsonPath("$.completed").value(patch.isCompleted()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    void getTodoTest() throws Exception {
        // given
        TodoEntity todo = new TodoEntity("sleeping",1,false);
        todo.setTodoId(1);

        TodoDto.Response response = new TodoDto.Response(1, "sleeping", 1, false);

        given(todoService.findTodo(Mockito.anyLong())).willReturn(new TodoEntity());
        given(mapper.todoToTodoResponse(Mockito.any(TodoEntity.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(get("/todos/" + todo.getTodoId())
                .accept(MediaType.APPLICATION_JSON));

        // then
        MvcResult result = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todo.getTitle()))
                .andExpect(jsonPath("$.order").value(todo.getOrder()))
                .andExpect(jsonPath("$.completed").value(todo.isCompleted()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getTodosTest() throws Exception {
        // given
        TodoEntity todo1 = new TodoEntity("sleeping",1,false);
        todo1.setTodoId(1);
        TodoEntity todo2 = new TodoEntity("studying",2,false);
        todo2.setTodoId(2);
        List<TodoEntity> todos = List.of(todo1, todo2);

        List<TodoDto.Response> responses = List.of(
                new TodoDto.Response(1,"sleeping",1,false),
                new TodoDto.Response(2,"studying",2,false));

        given(todoService.findTodos()).willReturn(todos);
        given(mapper.todosToTodoResponses(Mockito.any(List.class))).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(get("/todos")
                .accept(MediaType.APPLICATION_JSON));

        // then
        MvcResult result = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].title").value(todo1.getTitle()))
                .andExpect(jsonPath("$.[0].order").value(todo1.getOrder()))
                .andExpect(jsonPath("$.[0].completed").value(todo1.isCompleted()))
                .andExpect(jsonPath("$.[1].title").value(todo2.getTitle()))
                .andExpect(jsonPath("$.[1].order").value(todo2.getOrder()))
                .andExpect(jsonPath("$.[1].completed").value(todo2.isCompleted()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void deleteTodoTest() throws Exception {
        // given
        long todoId = 1;

        doNothing().when(todoService).deleteTodo(todoId);

        // when
        ResultActions actions = mockMvc.perform(delete("/todos/" + todoId));

        // then
        actions.andExpect(status().isNoContent());
    }

    @Test
    void deleteTodosTest() throws Exception {
        // given
        long todoId1 = 1;
        long todoId2 = 2;

        doNothing().when(todoService).deleteTodos();

        // when
        ResultActions actions = mockMvc.perform(delete("/todos"));

        // then
        actions.andExpect(status().isNoContent());
    }
}
