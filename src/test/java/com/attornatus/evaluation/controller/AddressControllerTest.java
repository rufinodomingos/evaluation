package com.attornatus.evaluation.controller;

import com.attornatus.evaluation.model.Address;
import com.attornatus.evaluation.model.Person;
import com.attornatus.evaluation.model.dto.AddressDto;
import com.attornatus.evaluation.service.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AddressService addressService;


    @Test
    public void testCreateAddressForPerson() throws Exception {

        Person person = new Person();
        person.setPersonId(1L);
        person.setName("Rufino Domingos");
        person.setBirthDate(LocalDate.parse("1994-06-04"));

        AddressDto mockAddress = new AddressDto();
        mockAddress.setCep("123456-789");
        mockAddress.setPublicPlace("Santa Catarina");
        mockAddress.setCity("São Paulo");
        mockAddress.setNumber("12345");
        mockAddress.setPrincipal(true);

        Address address = new Address();
        address.setAddressId(1L);
        address.setCep(mockAddress.getCep());
        address.setPublicPlace(mockAddress.getPublicPlace());
        address.setCity(mockAddress.getCity());
        address.setNumber(mockAddress.getNumber());
        address.setPrincipal(mockAddress.isPrincipal());
        address.setPerson(person);

        String inputInJson = this.mapToJson(mockAddress);

        String URI = "/address/person/1";

        when(addressService.createAddressForPerson(eq(any(AddressDto.class)),1L)).thenReturn(address);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void testFindAddressByPerson() throws Exception {

        Address mockAddress = new Address();
        mockAddress.setCep("123456-789");
        mockAddress.setPublicPlace("Santa Catarina");
        mockAddress.setCity("Sao Paulo");
        mockAddress.setNumber("12345");
        mockAddress.setPrincipal(true);

        List<Address> addressList = new ArrayList<>();
        addressList.add(mockAddress);

        when(addressService.findAddressByPerson(1L)).thenReturn(addressList);

        String URI = "/address/person/1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(addressList);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
    }



    private String mapToJson(Object object) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper.writeValueAsString(object);
    }

}
