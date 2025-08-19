package dev.strwbry.eventhorizon.events.testconfig;

public sealed interface Event permits EffectEvent, AttributeEvent {
    String getName();
}
