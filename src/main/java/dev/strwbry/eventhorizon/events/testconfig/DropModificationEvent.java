package dev.strwbry.eventhorizon.events.testconfig;

/**
 * This Event type is WIP. <p>
 * Represents a drop modification event with specific parameters.
 *
 * @implNote This record implements the Event interface.
 * @see Event
 */
public record DropModificationEvent(
                                    String name


) implements Event {
    @Override
    public String getName() { return name; }
}
