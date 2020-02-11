package com.pivotal.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DataController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/api/data")
    public DataDTO getStuff() {
        StuffDTO stuff = restTemplate.getForObject("http://localhost:8081/api/stuff", StuffDTO.class);
        return new DataDTO(stuff.getId(), stuff.getName(), stuff.getNotes());
    }
}
