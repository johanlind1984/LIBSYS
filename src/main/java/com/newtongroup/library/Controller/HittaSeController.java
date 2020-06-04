package com.newtongroup.library.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtongroup.library.Entity.Person;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import com.newtongroup.library.Wrapper.UserPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hitta-se-api")
public class HittaSeController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/search-person-by-phone")
    private String searchHittaSePersonByPhoneNumber(
            @RequestParam(value = "phonenumber", required = false) String phoneNumber,
            @RequestParam(value = "usertype", required = false) String userType,
            Model theModel, Principal principal) throws JsonProcessingException {

        if (phoneNumber == null) {
            return "register-visitor/register-visitor";
        }

        HttpHeaders headers = createHeaderForApiCall(phoneNumber);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> result = makeApiCallAndResult(entity, phoneNumber);
        UserPerson userPerson = new UserPerson();

        try {
            userPerson = extractResultToModel(result, phoneNumber);
        } catch (NullPointerException e) {
            theModel.addAttribute("userPerson", new UserPerson());
            theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
            return returnToRegisterForm(userType);
        }

        theModel.addAttribute("userPerson", userPerson);
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        return returnToRegisterForm(userType);
    }

    private String returnToRegisterForm(String userType) {
        switch (userType) {
            case "visitor":
                return "register-visitor/register-visitor";
            case "librarian":
                return "register-librarian/register-librarian";
            case "admin":
                return "register-admin/register-admin";
        }

        return "error";
    }

    private UserPerson extractResultToModel(ResponseEntity<String> result, String phoneNumber) throws JsonProcessingException {
        JsonNode productNode = new ObjectMapper().readTree(result.getBody());
        Person personToReturn = new Person();

        JsonNode personNode = productNode.get("result").get("persons").get("person").get(0);
        JsonNode adress = personNode.get("address").get(0);
        System.out.println(adress.asText());
        String[] fullname = personNode.get("displayName").textValue().split(" ");
        personToReturn.setFirstName(fullname[0]);
        personToReturn.setLastName(fullname[fullname.length - 1]);
        personToReturn.setStreet(adress.get("street").textValue() + " " + adress.get("number").textValue());
        personToReturn.setPostalCode(adress.get("zipcode").asText());
        personToReturn.setCity(adress.get("city").textValue());
        personToReturn.setPhone(phoneNumber);

        return new UserPerson(personToReturn);
    }

    private ResponseEntity<String> makeApiCallAndResult(HttpEntity<String> entity, String phoneNumber) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(getUri(phoneNumber), HttpMethod.GET, entity, String.class);
        return result;
    }

    private HttpHeaders createHeaderForApiCall(String phoneNumber) {
        String uri = getUri(phoneNumber);
        //String callerId = "lindsoft";
        //String apiKey = "7VvmJZJDgyZHBhzx1kmEIBOXDfKdO4e70hzISbdY";
        String callerId = "lindstudios";
        String apiKey = "lxiyeXwfmaer5DZc0YLjhVY9gmOeDvqpi9nzCy88";

        Date now = new Date();
        long unixTime = now.getTime() / 1000L;
        String randomGeneratedString = "kdew9213diqcRdxt";
        String unHashedKey = callerId + unixTime + apiKey + randomGeneratedString;
        String hashedKey = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(unHashedKey.getBytes("utf8"));
            hashedKey = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        Map<String, String> headerMap = new HashMap<>();

        headerMap.put("X-Hitta-CallerId", callerId);
        headerMap.put("X-Hitta-Time", String.valueOf(unixTime));
        headerMap.put("X-Hitta-Random", randomGeneratedString);
        headerMap.put("X-Hitta-Hash", hashedKey);
        headers.setAll(headerMap);
        return headers;
    }

    private String getUri(String phoneNumber) {
        final String starturi = "https://api.hitta.se/publicsearch/v1/combined?what=";
        String enduri = "&page.number=1&page.size=3";
        String uri = starturi + phoneNumber + enduri;
        return uri;
    }

}
