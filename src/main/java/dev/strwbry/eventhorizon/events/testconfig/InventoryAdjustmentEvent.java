package dev.strwbry.eventhorizon.events.testconfig;

/**
 * Represents an inventory adjustment event with specific parameters.
 * All InventoryAdjustment Events might not utilize all parameters.
 *
 * @param name              The name of the event
 * @param useContinuous     Whether the event uses continuous adjustments
 * @param origin            The origin of the adjustment (minimum size)
 * @param bound             The bound of the adjustment (maximum size, inclusive)
 * @param affectsMainhand   Whether the event affects the main hand
 * @param affectsOffhand    Whether the event affects the off hand
 * @implNote This record implements the Event interface.
 * Usage example:
 * <pre>
 * {@code InventoryAdjustmentEvent <name> = AdvConfigTest.get<event-name>Event();
 *
 * Boolean continuous = invAdjustEvent.useContinuous();
 * AdvConfigTest.getInventoryAdjustmentEvent().useContinuous();
 * }
 * </pre>
 * @see Event
 */
public record InventoryAdjustmentEvent(
                                        String name,
                                        Boolean useContinuous,
                                        Integer origin,
                                        Integer bound,
                                        Boolean affectsMainhand,
                                        Boolean affectsOffhand
)
implements Event {
    @Override
    public String getName() { return name; }
}
