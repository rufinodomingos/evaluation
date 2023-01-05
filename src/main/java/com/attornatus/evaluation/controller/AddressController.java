package com.attornatus.evaluation.controller;


import com.attornatus.evaluation.exceptions.ApiExceptionHandler;
import com.attornatus.evaluation.model.dto.AddressDto;
import com.attornatus.evaluation.model.dto.PersonDto;
import com.attornatus.evaluation.service.AddressService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AddressController {

    @Autowired
    AddressService addressService;

    @Operation(summary = "Create address for person", tags = {
            "address"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully!", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Request error! Check the submitted information.", content = @Content(schema = @Schema(implementation = PersonDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Server Exception", content = @Content(schema = @Schema(implementation = ApiExceptionHandler.class), mediaType = MediaType.APPLICATION_JSON_VALUE))})

    @PostMapping(path="/person/{personId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AddressDto> saveAddressForPerson(
            @Parameter(description = "id of person to be searched") @PathVariable("personId") UUID personId,
            @Valid @JsonView(AddressDto.AddressView.RegistrationPost.class) @RequestBody  AddressDto addressDto) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddressForPerson(addressDto,personId));
    }
}
