package com.attornatus.evaluation.service.impl;

import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.PersonDto;
import com.attornatus.evaluation.repository.PersonRepository;
import com.attornatus.evaluation.service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        log.debug("POST createPerson personDto received {} ", personDto.toString());
        Person person = new Person();
        Optional<Person> personOptional = personRepository.findByName(personDto.getName());
        if (personOptional.isPresent()) {
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists!");
        }
        BeanUtils.copyProperties(personDto, person);
        person = personRepository.save(person);
        log.debug("INFO person created successfully personId {} ", person.getPersonId());
        BeanUtils.copyProperties(person,personDto);

        return personDto;
    }

    @Override
    public PersonDto editPerson(PersonDto personDto) {
        log.debug("PUT editPerson personDto received {} ", personDto.toString());
        Person personExist = personRepository.findById(personDto.getPersonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa informada não existe"));
        BeanUtils.copyProperties(personDto, personExist);
        personExist = personRepository.save(personExist);
        log.debug("INFO person updated successfully personId {} ", personDto.getPersonId());
        BeanUtils.copyProperties(personExist,personDto);
        return personDto;
    }

    @Override
    public PersonDto findOnePerson(UUID personId) {
        Person personExist = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa informada não existe"));
        PersonDto personDto = new PersonDto();
        BeanUtils.copyProperties(personExist,personDto);
        return personDto;
    }

    @Override
    public List<PersonDto> findAllPersons(String name) {
        Iterable<Person> iterable = personRepository.findByNameContainingIgnoreCase(name);

        List<PersonDto> personDtoList = StreamSupport.stream(iterable.spliterator(),false)
        .map( person -> {
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person, personDto);
            return personDto;
        }).collect(Collectors.toList());
        return  personDtoList;
    }
}
