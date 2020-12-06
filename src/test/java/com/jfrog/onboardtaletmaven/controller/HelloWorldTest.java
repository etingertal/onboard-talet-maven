package com.jfrog.onboardtaletmaven.controller;

import com.jfrog.onboardtaletmaven.HelloWorldController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class HelloWorldTest {

    @Autowired
    private HelloWorldController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
        log.info("Hello world is ok");
    }
}
