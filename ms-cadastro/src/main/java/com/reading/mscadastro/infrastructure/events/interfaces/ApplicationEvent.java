package com.reading.mscadastro.infrastructure.events.interfaces;

import java.util.Map;

public abstract class ApplicationEvent {
    protected Map<String, String> payload;

    public abstract String getType();

    public String getPayloadValue(String key) {
        if (this.payload.containsKey(key)) {
            return this.payload.get(key);
        }
        return "";
    }

    protected ApplicationEvent(Map<String, String> payload) {
        this.payload = payload;
    }
}
