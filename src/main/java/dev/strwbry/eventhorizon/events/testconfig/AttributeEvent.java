package dev.strwbry.eventhorizon.events.testconfig;

public record AttributeEvent(
                             String name,
                             Double growthScale,
                             Double maxHealth,
                             Double attackDmg,
                             Double knockResist,
                             Double movespeed,
                             Double sneakspeed,
                             Double waterMovespeed,
                             Double stepHeight,
                             Double jumpStrength,
                             Double safeFallDist,
                             Double blockInteractRange,
                             Double entityInteractRange
) implements Event {
    @Override
    public String getName() { return name; }
}
