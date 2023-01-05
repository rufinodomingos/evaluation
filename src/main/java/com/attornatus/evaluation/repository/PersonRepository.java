package com.attornatus.evaluation.repository;

import com.attornatus.evaluation.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {
    Optional<Person> findByName(@Param("name") String name);
    @Query(value = "Select * from person l where  LOWER(name)  LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Person> findByNameContainingIgnoreCase(String name);
}
