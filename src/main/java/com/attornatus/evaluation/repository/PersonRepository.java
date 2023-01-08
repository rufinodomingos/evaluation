package com.attornatus.evaluation.repository;

import com.attornatus.evaluation.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    Optional<Person> findByName(String name);
    @Query(value = "Select * from Person p where  LOWER(p.name)  LIKE LOWER(CONCAT('%',:name,'%')) ORDER BY  p.name ASC ",nativeQuery = true)
    List<Person> findAllByName(String name);
}
