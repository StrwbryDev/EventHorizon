package dev.strwbry.eventhorizon.events.attributes;

import dev.strwbry.eventhorizon.events.EventClassification;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

/**
 * An attribute that gives the player an extremely high attack damage, allowing them to defeat most entities in a single hit.
 */
public class OnePunch extends BaseAttribute {
    /**
     * Constructs a new OnePunch event with a high attack damage modifier.
     */
    public OnePunch() {
        super(EventClassification.POSITIVE, "onePunch");
        addAttributeModifier(Attribute.ATTACK_DAMAGE, 2048.0, AttributeModifier.Operation.ADD_NUMBER);
    }

    /**
     * This method executes the OnePunch event.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * This method terminates the OnePunch event.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}