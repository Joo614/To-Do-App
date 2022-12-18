package com.project1.todo1.ServiceTest;

import com.project1.todo1.function.exception.BusinessLogicException;
import com.project1.todo1.function.utils.CustomBeanUtils;
import com.project1.todo1.todo.entity.TodoEntity;
import com.project1.todo1.todo.repository.TodoRepository;
import com.project1.todo1.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.util.AssertionErrors.assertNull;

//  Mockito 를 사용한 Mocking 을 통해 데이터 액세스 계층을 거쳐 DB 에서 데이터를 가져오는 로직을 단절시켜야 함
@ExtendWith(MockitoExtension.class) // Spring 을 사용하지 않고, Junit 에서 Mockito 의 기능을 사용하겠다
public class TodoServiceMockTest {

    @Mock
    private TodoRepository todoRepository; // 가짜 객체

    @Mock
    private CustomBeanUtils<TodoEntity> customBeanUtils; // 가짜 객체

    @InjectMocks
    private TodoService todoService; // 이 객체의 생성자에 위 mock 객체들을 DI할 것임

    @Test
    public void saveTodoTest() {
        // given
        TodoEntity todo = new TodoEntity("sleeping",1,false);
        todo.setTodoId(1);

        given(todoRepository.save(Mockito.any(TodoEntity.class))).willReturn(todo);

        // when
        TodoEntity responseTodo = todoService.createTodo(todo);

        // then
        assertEquals(todo.getTodoId(), responseTodo.getTodoId());
        assertEquals(todo.getTitle(), responseTodo.getTitle());
        assertEquals(todo.getOrder(), responseTodo.getOrder());
        assertEquals(todo.isCompleted(), responseTodo.isCompleted());
    }

    @Test
    public void findTodoTest() {
        // given
        TodoEntity todo = new TodoEntity("sleeping",1,false);
        todo.setTodoId(1);

        given(todoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(todo));

        // when
        TodoEntity responseTodo = todoService.findTodo(todo.getTodoId());

        // then
        assertEquals(todo.getTodoId(), responseTodo.getTodoId());
        assertEquals(todo.getTitle(), responseTodo.getTitle());
        assertEquals(todo.getOrder(), responseTodo.getOrder());
        assertEquals(todo.isCompleted(), responseTodo.isCompleted());
    }

    @Test
    public void findTodosTest() {
        // given
        TodoEntity todo1 = new TodoEntity("sleeping",1,false);
        todo1.setTodoId(1);
        TodoEntity todo2 = new TodoEntity("studying",2,false);
        todo2.setTodoId(2);
        List<TodoEntity> todos = List.of(todo1, todo2);

        given(todoRepository.findAll()).willReturn(todos);

//        // when
//        List<TodoEntity> responseTodo = todoService.findTodo(todos);
//
//        // then
//        assertEquals(todo.getTodoId(), responseTodo.getTodoId());
//        assertEquals(todo.getTitle(), responseTodo.getTitle());
//        assertEquals(todo.getOrder(), responseTodo.getOrder());
//        assertEquals(todo.isCompleted(), responseTodo.isCompleted());
    }

    @Test
    public void updateTodoTest() {
//        // given
//        TodoEntity todo = new TodoEntity("sleeping",1,false);
//        todo.setTodoId(1);
//
//        TodoEntity todo2 = new TodoEntity("sleeping2",1,false);
//        todo.setTodoId(2);
//
//        given(todoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(todo));
//        given(customBeanUtils.copyNonNullProperties(Mockito.any(), Mockito.any())).willReturn(todo);
//
//        // when
//        TodoEntity responseTodo = todoService.updateTodo(todo2);
//
//        // then
//        assertEquals(todo.getTodoId(), responseTodo.getTodoId());
//        assertEquals(todo.getTitle(), responseTodo.getTitle());
//        assertEquals(todo.getOrder(), responseTodo.getOrder());
//        assertEquals(todo.isCompleted(), responseTodo.isCompleted());
    }

    @Test
    public void deleteTodoTest() {
        // given
        TodoEntity todo = new TodoEntity("sleeping",1,false);
        todo.setTodoId(1);

        given(todoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(todo));

        // when
        todoService.deleteTodo(todo.getTodoId());

        // then
        assertEquals(1, todo.getTodoId());
    }

    @Test
    public void deleteTodosTest() {
//        // given
//        TodoEntity todo = new TodoEntity("sleeping",1,false);
//        todo.setTodoId(1);
//
//        given(todoRepository.findAll()).willReturn(new ArrayList<>());
//
//        // when
//        todoService.deleteTodos();
//
//        // then
//        assertEquals([], todoService.findTodos());
//        assertNull(todoService.findTodo());
    }
}
