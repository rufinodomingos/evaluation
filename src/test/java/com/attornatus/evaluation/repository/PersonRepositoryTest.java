package com.attornatus.evaluation.repository;

import com.attornatus.evaluation.model.Address;
import com.attornatus.evaluation.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCreatePerson() {
        Person person = getPerson();
        Person savedInDb = entityManager.merge(person);
        Person getFromDb = personRepository.findById(savedInDb.getPersonId()).get();

        assertThat(getFromDb).isEqualTo(savedInDb);
    }

    @Test
    public void testGetPersonById(){

        entityManager.merge(getPerson());

        Person getFromDb = personRepository.getReferenceById(1L);
        assertThat(getFromDb.getName()).isEqualTo("Rufino Domingos");
    }

    @Test
    public void testEditPerson(){
        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        entityManager.merge(person);

        Person getFromDb = personRepository.findByName("Rufino Domingos").get();
        getFromDb.setName("Biany Domingos");
        entityManager.persist(getFromDb);

        assertThat(getFromDb.getName()).isEqualTo("Biany Domingos");


    }

    @Test
    public void testGetAllPersons() {
        Person person1 = new Person();
        person1.setPersonId(1L);
        person1.setName("Rufino Domingos");
        person1.setBirthDate(LocalDate.parse("1994-06-04"));

        List<Person> personList = new ArrayList<>();

        Person person2 = new Person();
        person2.setPersonId(2L);
        person2.setName("Biany Domingos");
        person2.setBirthDate(LocalDate.parse("2020-07-08"));

        entityManager.merge(person1);
        entityManager.merge(person2);



        Iterable<Person> allAddressFromDb = personRepository.findAllByName(null);
        List<Person> personsList = new ArrayList<>();

        for (Person person : allAddressFromDb) {
            personsList.add(person);
        }
        assertThat(personsList).isEqualTo(allAddressFromDb);
    }


    private Person getPerson() {
        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        return person;
    }
}
