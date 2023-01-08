package com.attornatus.evaluation.service;

import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.PersonDto;

import java.util.List;

public interface PersonService {
   Person createPerson(PersonDto personDto);
   Person editPerson(PersonDto personDto);
   Person findOnePerson(Long personId);
   List<Person> findAllPersons(String name);
}
