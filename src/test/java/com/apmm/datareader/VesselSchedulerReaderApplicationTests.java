package com.apmm.datareader;

import com.apmm.datareader.controller.EventController;
import com.apmm.datareader.service.EventService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest(EventController.class)
public class VesselSchedulerReaderApplicationTests {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private EventService service;
}