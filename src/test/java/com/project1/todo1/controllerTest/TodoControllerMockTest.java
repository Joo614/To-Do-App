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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(TodoController.class)
@AutoConfigureRestDocs
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
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.order").value(post.getOrder()))
                .andExpect(jsonPath("$.completed").value(post.isCompleted()))
                .andDo(document("post-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("해야 할 일"),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("등록 순서").optional(),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("todoId").type(JsonFieldType.NUMBER).description("Todo 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("해야 할 일"),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("등록 순서"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                )
                        )
                ));
    }

    @Test
    void patchTodoTest() throws Exception {
        // given
        long todoId = 1;

        TodoDto.Patch patch = new TodoDto.Patch(1, "sleeping", 1, false);
        String content = gson.toJson(patch);

        TodoDto.Response response = new TodoDto.Response(1, "sleeping", 1, false);

        given(mapper.todoPatchToTodo(Mockito.any(TodoDto.Patch.class))).willReturn(new TodoEntity());
        given(todoService.updateTodo(Mockito.any(TodoEntity.class))).willReturn(new TodoEntity());
        given(mapper.todoToTodoResponse(Mockito.any(TodoEntity.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/todos/{todo-id}", todoId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.todoId").value(patch.getTodoId()))
                .andExpect(jsonPath("$.title").value(patch.getTitle()))
                .andExpect(jsonPath("$.order").value(patch.getOrder()))
                .andExpect(jsonPath("$.completed").value(patch.isCompleted()))
                .andDo(document("patch-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("todo-id").description("Todo 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("todoId").type(JsonFieldType.NUMBER).description("Todo 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("해야 할 일").optional(),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("등록 순서").optional(),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("todoId").type(JsonFieldType.NUMBER).description("Todo 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("해야 할 일"),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("등록 순서"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                )
                        )
                ));
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
        ResultActions actions = mockMvc.perform(get("/todos/{todo-id}", todo.getTodoId())
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todo.getTitle()))
                .andExpect(jsonPath("$.order").value(todo.getOrder()))
                .andExpect(jsonPath("$.completed").value(todo.isCompleted()))
                .andDo(document("get-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("todo-id").description("Todo 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("todoId").type(JsonFieldType.NUMBER).description("Todo 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("해야 할 일"),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("등록 순서"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                )
                        )
                ));
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
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].title").value(todo1.getTitle()))
                .andExpect(jsonPath("$.[0].order").value(todo1.getOrder()))
                .andExpect(jsonPath("$.[0].completed").value(todo1.isCompleted()))
                .andExpect(jsonPath("$.[1].title").value(todo2.getTitle()))
                .andExpect(jsonPath("$.[1].order").value(todo2.getOrder()))
                .andExpect(jsonPath("$.[1].completed").value(todo2.isCompleted()))
                .andDo(document("get-todos",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("결과 리스트"),
                                        fieldWithPath("[].todoId").type(JsonFieldType.NUMBER).description("Todo 식별자"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("해야 할 일"),
                                        fieldWithPath("[].order").type(JsonFieldType.NUMBER).description("등록 순서"),
                                        fieldWithPath("[].completed").type(JsonFieldType.BOOLEAN).description("완료 여부")
                                )
                        )
                ));
    }

    @Test
    void deleteTodoTest() throws Exception {
        // given
        long todoId = 1;

        doNothing().when(todoService).deleteTodo(todoId);

        // when
        ResultActions actions = mockMvc.perform(delete("/todos/{todo-id}", todoId));

        // then
        actions.andExpect(status().isNoContent())
                .andDo(document("delete-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("todo-id").description("회원 식별자")
                        )
                ));
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
        actions.andExpect(status().isNoContent())
                .andDo(document("delete-todos",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}
