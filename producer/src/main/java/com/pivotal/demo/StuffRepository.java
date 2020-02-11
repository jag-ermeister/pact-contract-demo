package com.pivotal.demo;

import org.springframework.stereotype.Repository;

@Repository
public class StuffRepository {

    public StuffDTO getStuff() {
        return new StuffDTO(1, "Jeremy", "is awesome");
    }
}
