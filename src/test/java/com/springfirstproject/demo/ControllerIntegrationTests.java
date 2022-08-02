package com.springfirstproject.demo;

import com.springfirstproject.demo.beans.Country;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerIntegrationTests.class})
public class ControllerIntegrationTests {
    @Test
    @Order(1)
    public void getAllCountriesIntegrationTest() throws JSONException {
        String expected = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"India\",\n" +
                "        \"capital\": \"Delhi\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Pakistan\",\n" +
                "        \"capital\": \"Karachi\"\n" +
                "    }\n" +
                "]";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity <String> response = testRestTemplate.
                getForEntity("http://localhost:8080/getcountries",String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }
    @Test
    @Order(2)
    public void getCountryByIdIntegrationTest() throws JSONException {
        String expected ="{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"India\",\n" +
                "    \"capital\": \"Delhi\"\n" +
                "}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity <String> response = testRestTemplate.
                getForEntity("http://localhost:8080/getcountries/1",String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }
    @Test
    @Order(3)
    public void getCountryByNameIntegrationTest() throws JSONException {
        String expected ="{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Pakistan\",\n" +
                "    \"capital\": \"Karachi\"\n" +
                "}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity <String> response = testRestTemplate.
                getForEntity("http://localhost:8080/getcountry/countryname?name=Pakistan",String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }
    @Test
    @Order(4)
    public void addCountryIntegrationTest() throws JSONException {
        Country country = new Country(3,"Germany", "Berlin");
        String expected ="{\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Germany\",\n" +
                "    \"capital\": \"Berlin\"\n" +
                "}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request = new HttpEntity<>(country,httpHeaders);
        ResponseEntity <String> response = testRestTemplate.
                postForEntity("http://localhost:8080/addcountry",request,String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }
    @Test
    @Order(5)
    public void updateCountryIntegrationTest() throws JSONException {
        Country country = new Country(3,"Japan", "Tokyo");
        String expected ="{\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Japan\",\n" +
                "    \"capital\": \"Tokyo\"\n" +
                "}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request = new HttpEntity<>(country,httpHeaders);
        ResponseEntity <String> response = testRestTemplate.
                exchange("http://localhost:8080/updatecountry/3",HttpMethod.PUT,request,String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }
    @Test
    @Order(6)
    public void deleteCountryIntegrationTest() throws JSONException {
        Country country = new Country(3,"Japan", "Tokyo");
        String expected ="{\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Japan\",\n" +
                "    \"capital\": \"Tokyo\"\n" +
                "}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request = new HttpEntity<>(country,httpHeaders);
        ResponseEntity <String> response = testRestTemplate.
                exchange("http://localhost:8080/deletecountry/3",HttpMethod.DELETE,request,String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }

}
