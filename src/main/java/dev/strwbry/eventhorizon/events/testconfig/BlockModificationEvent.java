package dev.strwbry.eventhorizon.events.testconfig;

/**
 * Represents a block modification event with specific parameters.
 * All BlockModification Events might not utilize all parameters.
 *
 * @param name   The name of the event
 * @param radius The radius of the block modification
 * @param height The height of the block modification
 * @param offset The offset for the block modification
 * @implNote This record implements the Event interface
 * Usage example:
 * <pre>
 * {@code BlockModificationEvent <name> = AdvConfigTest.get<event-name>Event();
 *
 * Integer radius = <name>.radius();
 * AdvConfigTest.getBlockEvent().radius();
 * }
 * </pre>
 * @see Event
 */
public record BlockModificationEvent(
                                    String name,
                                    Integer radius,
                                    Integer height,
                                    Integer offset

) implements Event {
    @Override
    public String getName() { return name; }
}
