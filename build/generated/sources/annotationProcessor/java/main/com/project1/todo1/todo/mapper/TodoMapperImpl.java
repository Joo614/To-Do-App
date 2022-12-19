package com.project1.todo1.todo.mapper;

import com.project1.todo1.todo.dto.TodoDto;
import com.project1.todo1.todo.entity.TodoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-19T01:40:44+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public TodoEntity todoPostToTodo(TodoDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTitle( requestBody.getTitle() );
        todoEntity.setOrder( requestBody.getOrder() );
        todoEntity.setCompleted( requestBody.isCompleted() );

        return todoEntity;
    }

    @Override
    public TodoEntity todoPatchToTodo(TodoDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTitle( requestBody.getTitle() );
        todoEntity.setTodoId( requestBody.getTodoId() );
        todoEntity.setOrder( requestBody.getOrder() );
        todoEntity.setCompleted( requestBody.isCompleted() );

        return todoEntity;
    }

    @Override
    public TodoDto.Response todoToTodoResponse(TodoEntity todo) {
        if ( todo == null ) {
            return null;
        }

        long todoId = 0L;
        String title = null;
        long order = 0L;
        boolean completed = false;

        todoId = todo.getTodoId();
        title = todo.getTitle();
        order = todo.getOrder();
        completed = todo.isCompleted();

        TodoDto.Response response = new TodoDto.Response( todoId, title, order, completed );

        return response;
    }

    @Override
    public List<TodoDto.Response> todosToTodoResponses(List<TodoEntity> todos) {
        if ( todos == null ) {
            return null;
        }

        List<TodoDto.Response> list = new ArrayList<TodoDto.Response>( todos.size() );
        for ( TodoEntity todoEntity : todos ) {
            list.add( todoToTodoResponse( todoEntity ) );
        }

        return list;
    }
}
