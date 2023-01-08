package com.attornatus.evaluation.controller;

import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.PersonDto;
import com.attornatus.evaluation.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @Test
    public void testCreatePerson() throws Exception {

        PersonDto mockPerson = new PersonDto();
        mockPerson.setPersonId(1L);
        mockPerson.setName("Rufino Domingos");
        mockPerson.setBirthDate(LocalDate.parse("1994-06-04"));

        Person person = new Person();
        person.setPersonId(mockPerson.getPersonId());
        person.setName(mockPerson.getName());
        person.setBirthDate(mockPerson.getBirthDate());

        String inputInJson = this.mapToJson(mockPerson);

        String URI = "/person";

        Mockito.when(personService.createPerson(Mockito.any(PersonDto.class))).thenReturn(person);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void testFindOnePerson() throws Exception {

        Person mockPerson = new Person();
        mockPerson.setPersonId(1L);
        mockPerson.setName("Rufino Domingos");
        mockPerson.setBirthDate(LocalDate.parse("1994-06-04"));

        Mockito.when(personService.findOnePerson(1L)).thenReturn(mockPerson);

        String URI = "/person/1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(mockPerson);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @Test
    public void testFindAllPersons() throws Exception {

        Person mockPerson1 = new Person();
        mockPerson1.setPersonId(1L);
        mockPerson1.setName("Rufino Domingos");
        mockPerson1.setBirthDate(LocalDate.parse("1994-06-04"));

        Person mockPerson2 = new Person();
        mockPerson2.setPersonId(2L);
        mockPerson2.setName("Biany Domingos");
        mockPerson2.setBirthDate(LocalDate.parse("2020-07-08"));

        List<Person> personList = new ArrayList<>();
        personList.add(mockPerson1);
        personList.add(mockPerson2);

        Pageable paging = PageRequest.of(0, 10, Sort.by("person_id"));
        int start = (int) paging.getOffset();
        int end = (int) (Math.min((start + paging.getPageSize()), personList.size()));
        Page<Person> personDtoPage = new PageImpl<Person>(personList.subList(start, end), paging, personList.size());

        Mockito.when(personService.findAllPersons(null)).thenReturn(personList);

        String URI = "/person";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(personDtoPage);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
    }



    private String mapToJson(Object object) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper.writeValueAsString(object);
    }

}
