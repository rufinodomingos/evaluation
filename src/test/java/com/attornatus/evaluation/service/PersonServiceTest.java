package com.attornatus.evaluation.service;

import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.PersonDto;
import com.attornatus.evaluation.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    //@Ignore
    public void testCreatePerson(){
        PersonDto request = new PersonDto();
        request.setPersonId(1L);
        request.setName("Rufino Domingos");
        request.setBirthDate(LocalDate.parse("1994-06-04"));

        Person person = new Person();
        person.setPersonId(1L);
        person.setName(request.getName());
        person.setBirthDate(request.getBirthDate());

        Mockito.when(personRepository.save(person)).thenReturn(person);
        Assertions.assertThat(personService.createPerson(request)).isEqualTo(person);

    }

    @Test
    public void testGetPersonById(){
        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        Assertions.assertThat(personService.findOnePerson(person.getPersonId())).isEqualTo(person);
    }

    @Test
    public void testEditPerson(){
        PersonDto request = new PersonDto();
        request.setPersonId(1L);
        request.setName("Rufino Domingos");
        request.setBirthDate(LocalDate.parse("1994-06-04"));

        Person person = new Person();
        person.setPersonId(1L);
        person.setName(request.getName());
        person.setBirthDate(request.getBirthDate());

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        person.setName("Rufino Pedro Domingos");

        Mockito.when(personRepository.save(person)).thenReturn(person);

        Assertions.assertThat(personService.editPerson(request)).isEqualTo(person);

    }

    @Test
    public void testGetAllPersons() {
        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        List<Person> personList = new ArrayList<>();

        Person person2 = new Person();
        person2.setPersonId(2L);
        person2.setName("Biany Domingos");
        person2.setBirthDate(LocalDate.parse("2020-07-08"));

        personList.add(person);
        personList.add(person2);

        Mockito.when(personRepository.findAllByName("")).thenReturn(personList);
        Assertions.assertThat(personService.findAllPersons("")).isEqualTo(personList);
    }

}
