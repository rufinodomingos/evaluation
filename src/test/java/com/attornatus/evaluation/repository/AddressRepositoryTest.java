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
public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testcreateAddressForPerson() {
        Address address = getAddress();
        Address savedInDb = entityManager.persist(address);
        Address getFromDb = addressRepository.findById(savedInDb.getAddressId()).get();

        assertThat(getFromDb).isEqualTo(savedInDb);
    }

    @Test
    public void testFindAddressByPerson() {

        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        Address address1 = new Address();
        address1.setCep("123456-789");
        address1.setPublicPlace("Santa Catarina");
        address1.setCity("São Paulo");
        address1.setNumber("12345");
        address1.setPrincipal(true);
        address1.setPerson(person);

        entityManager.persist(address1);

        Iterable<Address> allAddressFromDb = addressRepository.findAddressByPerson(person);
        List<Address> addressList = new ArrayList<>();

        for (Address address : allAddressFromDb) {
            addressList.add(address);
        }
        assertThat(addressList).isEqualTo(allAddressFromDb);
    }


    private Address getAddress() {

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
        return address;
    }
}
