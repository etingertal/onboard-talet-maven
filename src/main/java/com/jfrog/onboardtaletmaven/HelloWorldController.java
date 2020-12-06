package com.jfrog.onboardtaletmaven;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello-world")
    public @ResponseBody ResponseEntity<String> helloWorld() {
        return new ResponseEntity<String>("Hello world!", HttpStatus.OK);
    }
}
