package com.attornatus.evaluation.service.impl;

import com.attornatus.evaluation.exceptions.AttornatusException;
import com.attornatus.evaluation.model.Address;
import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.AddressDto;
import com.attornatus.evaluation.repository.AddressRepository;
import com.attornatus.evaluation.repository.PersonRepository;
import com.attornatus.evaluation.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;

    public AddressServiceImpl(AddressRepository addressRepository, PersonRepository personRepository) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Address createAddressForPerson(AddressDto addressDto, Long personId) {
        Person personExistOptional = personRepository.findById(personId)
                .orElseThrow(() -> new AttornatusException("Person not Exist!"));

        if (addressDto.isPrincipal()) {
            Optional<Address> existPrincipalOptional = addressRepository.findPrincipalAddress(personId);
            if (existPrincipalOptional.isPresent()) {
                throw new AttornatusException("A primary address for this person already exists!");
            }
        }

        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        address.setPerson(personExistOptional);
        address = addressRepository.save(address);
        return address;
    }

    @Override
    public List<Address> findAddressByPerson(Long personId) {
        Person personExistOptional = personRepository.findById(personId)
                .orElseThrow(() -> new AttornatusException("Person not Exist!"));

        Iterable<Address> iterable = addressRepository.findAddressByPerson(personExistOptional);

        return StreamSupport.stream(iterable.spliterator(), false)
                .map(address -> {
                    return address;
                }).collect(Collectors.toList());
    }
}
