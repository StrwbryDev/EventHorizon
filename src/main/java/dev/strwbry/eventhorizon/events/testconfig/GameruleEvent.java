package dev.strwbry.eventhorizon.events.testconfig;

/**
 * This Event type is WIP. <p>
 * Represents a gamerule event with specific parameters.
 *
 * @implNote This record implements the Event interface.
 * @see Event
 */
public record GameruleEvent(
                            String name

)
implements Event {
    @Override
    public String getName() { return name; }
}
