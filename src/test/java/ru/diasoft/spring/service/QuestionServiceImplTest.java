package ru.diasoft.spring.service;

import org.junit.jupiter.api.Test;
import ru.diasoft.spring.dao.QuestionDao;
import ru.diasoft.spring.domain.Question;

import java.util.List;

import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @Test
    void executeTest_shouldCallFindAll() {
        QuestionDao mockDao = mock(QuestionDao.class);
        IOService mockIO = mock(IOService.class);

        when(mockDao.findAll()).thenReturn(List.of(
                new Question("What is 2+2?", List.of("4", "3", "5"), 0)
        ));
        when(mockIO.readLine()).thenReturn("John", "Doe", "1");

        QuestionServiceImpl service = new QuestionServiceImpl(mockDao, mockIO, 1);
        service.executeTest();

        verify(mockDao, times(1)).findAll();
    }
}
