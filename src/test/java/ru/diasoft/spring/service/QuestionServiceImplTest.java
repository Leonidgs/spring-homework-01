package ru.diasoft.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.diasoft.spring.dao.QuestionDao;
import ru.diasoft.spring.domain.Question;

import java.util.List;

import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @Test
    void executeTest_shouldCallFindAll() {
        QuestionDao mockDao = Mockito.mock(QuestionDao.class);
        when(mockDao.findAll()).thenReturn(List.of(
                new Question("What is 2+2?", List.of("4", "3", "5"), 0)
        ));

        QuestionServiceImpl service = new QuestionServiceImpl(mockDao, 1);
        service.executeTest();

        verify(mockDao, times(1)).findAll();
    }
}
