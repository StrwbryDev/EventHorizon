package dev.strwbry.eventhorizon.events.testconfig;

public record EffectEvent(
        String name,
        Integer duration,
        Double amplifier,
        Boolean ambient,
        Boolean showParticles,
        Boolean showIcon
) implements Event {
    @Override
    public String getName() { return name; }
}
