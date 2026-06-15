package org.example.service;

import org.example.dao.QuestionDao;
import org.example.domain.Question;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @Test
    void executeTest_shouldCallFindAll() {
        QuestionDao mockDao = Mockito.mock(QuestionDao.class);
        when(mockDao.findAll()).thenReturn(List.of(
                new Question("What is 2+2?", List.of("4", "3", "5"))
        ));

        QuestionServiceImpl service = new QuestionServiceImpl(mockDao);
        service.executeTest();

        verify(mockDao, times(1)).findAll();
    }
}
