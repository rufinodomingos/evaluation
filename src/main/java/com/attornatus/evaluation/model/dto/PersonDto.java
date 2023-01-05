package com.attornatus.evaluation.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PersonDto {

    public interface PersonView {
        public static interface RegistrationPost {}
        public static interface PersonPut {}
    }

    @JsonView({PersonView.PersonPut.class})
    private UUID personId;

    @JsonView({PersonView.RegistrationPost.class,PersonView.PersonPut.class})
    @NotBlank(groups = {PersonView.RegistrationPost.class,PersonView.PersonPut.class})
    private String name;

    @JsonView({PersonView.RegistrationPost.class,PersonView.PersonPut.class})
    @NotBlank(groups = {PersonView.RegistrationPost.class,PersonView.PersonPut.class})
    private LocalDate birthDate;

}
