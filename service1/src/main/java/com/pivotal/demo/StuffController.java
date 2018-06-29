package com.pivotal.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuffController {

    @Autowired
    StuffRepository repository;

    @GetMapping("/api/stuff")
    public StuffDTO getStuff() {
        return repository.getStuff();
    }
}
