package com.attornatus.evaluation.controller;

import com.attornatus.evaluation.exceptions.ApiExceptionHandler;
import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.PersonDto;
import com.attornatus.evaluation.service.PersonService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Create Person", tags = {
            "person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registration done successfully!", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Request error! Check the submitted information.", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Server Exception", content = @Content(schema = @Schema(implementation = ApiExceptionHandler.class), mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createPerson(@Valid
                                               @JsonView(PersonDto.PersonView.RegistrationPost.class) @RequestBody PersonDto personDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(personDto));
    }

    @Operation(summary = "Update Person", description = "Update Person Info", method = "POST", tags = {
            "person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated successfully!", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Request error! Check the submitted information.", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Record not found", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Server Exception", content = @Content(schema = @Schema(implementation = ApiExceptionHandler.class), mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> editPerson(@Valid @JsonView(PersonDto.PersonView.PersonPut.class) @RequestBody PersonDto personDto) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.editPerson(personDto));
    }

    @Operation(summary = "Get all persons", method = "POST", tags = {
            "person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Request error! Check the submitted information.", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Did not find any Person", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Server Exception", content = @Content(schema = @Schema(implementation = ApiExceptionHandler.class), mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping()
    public ResponseEntity<Page<Person>> findAll(@RequestParam(required = false, name = "name") String name,
                                                   @ParameterObject @PageableDefault(page = 0, size = 10, sort = "person_id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<Person> personList = personService.findAllPersons(name);
        int start = (int) pageable.getOffset();
        int end = (int) (Math.min((start + pageable.getPageSize()), personList.size()));
        Page<Person> personDtoPage = new PageImpl<Person>(personList.subList(start, end), pageable, personList.size());
        return ResponseEntity.status(HttpStatus.OK).body(personDtoPage);
    }

    @Operation(summary = "Get a person by its id", method = "POST", tags = {
            "person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the person",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found",
                    content = @Content)})
    @GetMapping("/{personId}")
    public ResponseEntity<Person> findOnePerson(
            @Parameter(description = "id of person to be searched")
            @PathVariable("personId") Long personId) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findOnePerson(personId));
    }


}
