package com.reading.mscadastro.infrastructure.events.service;

import com.reading.mscadastro.infrastructure.events.interfaces.ApplicationEvent;
import com.reading.mscadastro.infrastructure.events.interfaces.EventBus;
import com.reading.mscadastro.infrastructure.events.interfaces.EventSubscriber;

public interface ApplicationService {
    default <E extends ApplicationEvent> void publishEvent(E event) {
        EventBus eventBus = getEventBus();
        if (eventBus != null) {
            eventBus.publish(event);
        }
    }

    default <E extends ApplicationEvent> void subscribe(String eventType, EventSubscriber subscriber) {
        EventBus eventBus = getEventBus();
        if (eventBus != null) {
            eventBus.subscribe(eventType, subscriber);
        }
    }

    default <E extends ApplicationEvent> void unsubscribe(String eventType, EventSubscriber subscriber) {
        EventBus eventBus = getEventBus();
        if (eventBus != null) {
            eventBus.unsubscribe(eventType, subscriber);
        }
    }

    EventBus getEventBus();

    void setEventBus(EventBus eventBus);
}
