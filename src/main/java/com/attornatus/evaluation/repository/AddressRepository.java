package com.attornatus.evaluation.repository;

import com.attornatus.evaluation.model.Address;
import com.attornatus.evaluation.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.principal=TRUE AND a.person.personId=:personId")
    Optional<Address> findPrincipalAddress(Long personId);
    List<Address> findAddressByPerson(Person person);
}
