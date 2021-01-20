package com.reading.msleitura.infrastructure.events;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    // private final Logger log =
    // LoggerFactory.getLogger(CustomEventListener.class);

    @Async
    @Override
    public void onApplicationEvent(CustomEvent event) {
    }

}
