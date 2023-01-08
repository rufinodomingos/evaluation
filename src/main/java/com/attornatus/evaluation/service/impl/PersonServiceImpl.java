package com.attornatus.evaluation.service.impl;

import com.attornatus.evaluation.exceptions.AttornatusException;
import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.PersonDto;
import com.attornatus.evaluation.repository.PersonRepository;
import com.attornatus.evaluation.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person createPerson(PersonDto personDto) {
        Person person = new Person();
        Optional<Person> personOptional = personRepository.findByName(personDto.getName());
        if (personOptional.isPresent()) {
            throw  new AttornatusException("Name already exists!");
        }
        BeanUtils.copyProperties(personDto, person);
        person = personRepository.save(person);
        return person;
    }

    @Override
    public Person editPerson(PersonDto personDto) {
        Person personExist = personRepository.findById(personDto.getPersonId())
                .orElseThrow(() -> new AttornatusException("Person does not exist"));
        BeanUtils.copyProperties(personDto, personExist);
        personExist = personRepository.save(personExist);
        return personExist;
    }

    @Override
    public Person findOnePerson(Long personId) {
        Person personExist = personRepository.findById(personId)
                .orElseThrow(() -> new AttornatusException("Person does not exist"));
        PersonDto personDto = new PersonDto();
        return personExist;
    }

    @Override
    public List<Person> findAllPersons(String name) {
        Iterable<Person> iterable = personRepository.findAllByName(name);

        return StreamSupport.stream(iterable.spliterator(),false)
        .map( person -> {
            return person;
        }).collect(Collectors.toList());
    }
}
