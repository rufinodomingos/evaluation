package com.attornatus.evaluation.service;

import com.attornatus.evaluation.model.dto.AddressDto;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    AddressDto createAddressForPerson(AddressDto addressDto, UUID personId);
    List<AddressDto> findAddressByPerson(UUID personId);
}
