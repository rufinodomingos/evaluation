package com.attornatus.evaluation.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AddressDto {

    @NotBlank()
    private String publicPlace;

    @NotBlank()
    private String cep;

    @NotBlank()
    private String number;

    @NotBlank()
    private String city;

    private boolean principal;


}
