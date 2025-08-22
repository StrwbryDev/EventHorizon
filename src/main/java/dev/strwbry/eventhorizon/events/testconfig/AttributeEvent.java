package dev.strwbry.eventhorizon.events.testconfig;

/**
 * Represents an attribute event with specific parameters.
 * All Attribute Events might not utilize all parameters.
 *
 * @param name  The name of the event
 * @param scale The scale/size multiplier for the event
 * @param maxHealth The max health increase for the event
 * @param attackDmg The attack damage increase for the event
 * @param knockResist The knockback resistance multiplier for the event
 * @param movespeed The movement speed multiplier for the event
 * @param sneakspeed The sneaking speed multiplier for the event
 * @param waterMovespeed The water movement speed multiplier for the event
 * @param stepHeight The step height increase for the event
 * @param jumpStrength The jump strength multiplier for the event
 * @param safeFallDist The safe fall distance increase for the event
 * @param blockInteractRange The block interaction range multiplier for the event
 * @param entityInteractRange The entity interaction range multiplier for the event
 * @param gravity The gravity change for the event (negative values for reduced gravity)
 * @implNote This record implements the Event interface.
 * Usage example:
 * <pre>
 * {@code AttributeEvent <name> = AdvConfigTest.get<event-name>Event();
 *
 * Double knock = <name>.knockback-resist-mult();
 * AdvConfigTest.getAttributeEvent().knockback-resist-mult();
 * }
 * </pre>
 * @see Event
 */
public record AttributeEvent(
                             String name,
                             Double scale,
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
                             Double entityInteractRange,
                             Double gravity
) implements Event {
    @Override
    public String getName() { return name; }
}
