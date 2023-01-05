package com.attornatus.evaluation.service.impl;

import com.attornatus.evaluation.exceptions.AttornatusException;
import com.attornatus.evaluation.model.Address;
import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.AddressDto;
import com.attornatus.evaluation.repository.AddressRepository;
import com.attornatus.evaluation.repository.PersonRepository;
import com.attornatus.evaluation.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;

    @Override
    public AddressDto createAddressForPerson(AddressDto addressDto, UUID personId) {
        Optional<Address> existCepOptional = addressRepository.findAddressByCep(addressDto.getCep());
        if (existCepOptional.isPresent()) {
            throw new AttornatusException("CEP is already Registered");
        }

        Person personExistOptional = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not Exist!"));

        addressDto.setPersonId(personId);
        Address address = new Address(addressDto);
        BeanUtils.copyProperties(addressDto, address);
        address = addressRepository.save(address);
        BeanUtils.copyProperties(address, addressDto);
        return addressDto;
    }

    @Override
    public List<AddressDto> findAddressByPerson(UUID personId) {
        return null;
    }
}
