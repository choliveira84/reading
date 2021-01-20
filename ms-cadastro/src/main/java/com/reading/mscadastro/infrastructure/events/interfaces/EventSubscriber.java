package com.reading.mscadastro.infrastructure.events.interfaces;

public interface EventSubscriber {
    <E extends ApplicationEvent> void onEvent(E event);
}
