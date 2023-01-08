package com.attornatus.evaluation.service;

import com.attornatus.evaluation.model.Address;
import com.attornatus.evaluation.model.dto.AddressDto;

import java.util.List;

public interface AddressService {
    Address createAddressForPerson(AddressDto addressDto, Long personId);
    List<Address> findAddressByPerson(Long personId);
}
