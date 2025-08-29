package dev.strwbry.eventhorizon.events.attributes;

import dev.strwbry.eventhorizon.events.EventClassification;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

/**
 * This event provides a significant boost to the attack knockback attribute.
 * Players will experience increased knockback when attacking, enhancing combat dynamics.
 */
public class KnockbackBoost extends BaseAttribute {
    /**
     * Constructs a new KnockbackBoost event with a high attack knockback modifier.
     */
    public KnockbackBoost() {
        super(EventClassification.NEUTRAL, "knockbackBoost");
        addAttributeModifier(Attribute.ATTACK_KNOCKBACK, 10.0, AttributeModifier.Operation.ADD_NUMBER);
    }

    /** This method executes the KnockbackBoost event. */
    @Override
    public void execute() {
        super.execute();
    }

    /** This method terminates the KnockbackBoost event. */
    @Override
    public void terminate() {
        super.terminate();
    }
}
