package com.attornatus.evaluation.service;

import com.attornatus.evaluation.model.dto.PersonDto;

import java.util.List;
import java.util.UUID;

public interface PersonService {
   PersonDto createPerson(PersonDto personDto);
   PersonDto editPerson(PersonDto personDto);
   PersonDto findOnePerson(UUID personId);
   List<PersonDto> findAllPersons(String name);
}
