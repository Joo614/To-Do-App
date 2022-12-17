package com.project1.todo1.ServiceTest;

import com.project1.todo1.todo.repository.TodoRepository;
import com.project1.todo1.todo.service.TodoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//  Mockito 를 사용한 Mocking 을 통해 데이터 액세스 계층을 거쳐 DB 에서 데이터를 가져오는 로직을 단절시켜야 함
@ExtendWith(MockitoExtension.class) // Spring 을 사용하지 않고, Junit 에서 Mockito 의 기능을 사용하겠다
public class TodoServiceMockTest {

    @Mock
    private TodoRepository todoRepository; // 가짜 객체

    @InjectMocks
    private TodoService todoService; // 이 객체의 생성자에 위 mock 객체를 di할 것임





}
