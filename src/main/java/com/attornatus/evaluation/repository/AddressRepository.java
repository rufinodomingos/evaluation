package com.attornatus.evaluation.repository;

import com.attornatus.evaluation.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = "select * from Address  where cep=:cep limit 1", nativeQuery = true)
    Optional<Address> findAddressByCep(String cep);
}
