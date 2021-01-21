package com.reading.msleitura.api;

import com.reading.msleitura.domain.services.LeituraService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leituras")
public class LeituraController {

    private final Logger log = LoggerFactory.getLogger(LeituraController.class);

    @Autowired
    private LeituraService service;
}
