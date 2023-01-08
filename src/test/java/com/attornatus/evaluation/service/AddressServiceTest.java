package com.attornatus.evaluation.service;

import com.attornatus.evaluation.model.Address;
import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.AddressDto;
import com.attornatus.evaluation.repository.AddressRepository;
import com.attornatus.evaluation.repository.PersonRepository;
import org.assertj.core.api.Assertions;
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
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void createAddressForPerson() {
        AddressDto dto = new AddressDto();
        dto.setCep("123456-789");
        dto.setPublicPlace("Santa Catarina");
        dto.setCity("São Paulo");
        dto.setNumber("12345");
        dto.setPrincipal(false);

        Address  address = new Address();
        address.setAddressId(1L);
        address.setCep(dto.getCep());
        address.setPublicPlace(dto.getPublicPlace());
        address.setCity(dto.getCity());
        address.setNumber(dto.getNumber());
        address.setPrincipal(dto.isPrincipal());

        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        address.setPerson(person);

        Mockito.when(addressRepository.save(address)).thenReturn(address);
        Assertions.assertThat(addressService.createAddressForPerson(dto,1L)).isEqualTo(address);
    }

    @Test
    public void testFindAddressByPerson(){

        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        Address address = new Address();
        address.setCep("123456-789");
        address.setPublicPlace("Santa Catarina");
        address.setCity("São Paulo");
        address.setNumber("12345");
        address.setPrincipal(true);
        address.setPerson(person);

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        Mockito.when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));

        Mockito.when(addressRepository.findAddressByPerson(person)).thenReturn(addressList);
        Assertions.assertThat(addressService.findAddressByPerson(person.getPersonId())).isEqualTo(addressList);
    }


}
