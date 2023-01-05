package com.attornatus.evaluation.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class AddressDto {

    public interface AddressView {
        public static interface RegistrationPost {}
        public static interface AddressPut {}
    }

    @JsonView({AddressDto.AddressView.AddressPut.class})
    private UUID addressId;

    @JsonView({AddressDto.AddressView.RegistrationPost.class})
    @NotBlank(groups = {PersonDto.PersonView.PersonPut.class})
    private String publicPlace;

    @JsonView({AddressDto.AddressView.RegistrationPost.class})
    @NotBlank(groups = {AddressDto.AddressView.RegistrationPost.class})
    private String cep;

    @JsonView({AddressDto.AddressView.RegistrationPost.class})
    @NotBlank(groups = {AddressDto.AddressView.RegistrationPost.class})
    private String number;

    @JsonView({AddressDto.AddressView.RegistrationPost.class})
    @NotBlank(groups = {AddressDto.AddressView.RegistrationPost.class})
    private String city;

    @JsonView({AddressDto.AddressView.RegistrationPost.class})
    private boolean principal;

    @JsonView({AddressDto.AddressView.AddressPut.class})
    private UUID personId;


}
