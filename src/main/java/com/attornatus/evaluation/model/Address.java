package com.attornatus.evaluation.model;

import com.attornatus.evaluation.model.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID addressId;

    @Column(nullable = false, unique = true)
    private String publicPlace;

    @Column(nullable = false, unique = true)
    private String cep;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String city;

    @Column
    private boolean principal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id", nullable=false)
    private Person person;

    public Address(AddressDto addressDto) {
        Person personModel = new Person();
        personModel.setPersonId(addressDto.getPersonId());
        this.person = personModel;
        this.cep = addressDto.getCep();
        this.city = addressDto.getCity();
        this.publicPlace = addressDto.getPublicPlace();
        this.number = addressDto.getNumber();
        this.principal = addressDto.isPrincipal();
    }


}
