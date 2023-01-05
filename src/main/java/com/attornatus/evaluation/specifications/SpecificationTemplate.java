package com.attornatus.evaluation.specifications;


import com.attornatus.evaluation.model.Person;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @Spec(path = "name", spec = Like.class)
    public interface PersonSpec extends Specification<Person> {
    }


}
