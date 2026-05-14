package com.example.exambytel9.WebTests;

import com.example.exambytel9.WebUI.OrganisatorControllerSeite;
import com.example.exambytel9.geschaeftslogik.Organisator;
import com.example.exambytel9.geschaeftslogik.WochenTest;
import com.example.exambytel9.pseudoDatenbank.InMemoryTestRepository;
import com.example.exambytel9.pseudoDatenbank.TestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class OrganisatorControllerTest {

    @Autowired
    MockMvc mvc;




    InMemoryTestRepository inMemoryTestRepository = mock(InMemoryTestRepository.class);

    @Autowired
    TestService testService = new TestService(inMemoryTestRepository);

  //  WochenTest wochenTest = new WochenTest();



    @Test
    @DisplayName("Aufruf auf Organisator/organisatormain gibt eine die main Seite zurück")
    void test_1() throws Exception {

        mvc.perform(get("/Organisator/organisatormain"))
                .andExpect(status().isFound());
    }


}
