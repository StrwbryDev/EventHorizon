package dev.strwbry.eventhorizon.events.attributes;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

/**
 * Represents an event that shrinks players, similar to the movie "Honey, I Shrunk the Kids".
 * This event reduces player size, health, and attack damage while increasing movement capabilities.
 * Extends {@link BaseAttribute} and is classified as a NEGATIVE event.
 */
public class HoneyIShrunkTheKids extends BaseAttribute {

    /**
     * Constructs a new HoneyIShrunkTheKids event.
     * Initializes the event with NEGATIVE classification and applies various attribute modifiers:
     * - Reduces player scale by 75%
     * - Decreases max health by 2 hearts
     * - Increases movement speed by 25%
     * - Reduces attack damage by 25%
     * - Enhances sneaking, swimming, and jumping abilities
     * - Slightly increases safe fall distance
     */
    public HoneyIShrunkTheKids() {
        super(EventClassification.NEGATIVE, "honeyIShrunkTheKids");

        addAttributeModifier(Attribute.SCALE, AdvConfig.getShrunkScale(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.MAX_HEALTH, AdvConfig.getShrunkMaxHP(), AttributeModifier.Operation.ADD_NUMBER);
        addAttributeModifier(Attribute.MOVEMENT_SPEED, AdvConfig.getShrunkMovespeed(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.ATTACK_DAMAGE, AdvConfig.getShrunkAttack(), AttributeModifier.Operation.ADD_SCALAR);

        addAttributeModifier(Attribute.SNEAKING_SPEED, AdvConfig.getShrunkSneakspeed(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.WATER_MOVEMENT_EFFICIENCY, AdvConfig.getShrunkWaterMove(), AttributeModifier.Operation.ADD_SCALAR);

        addAttributeModifier(Attribute.JUMP_STRENGTH, AdvConfig.getShrunkJumpStr(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.SAFE_FALL_DISTANCE, AdvConfig.getShrunkSafeFall(), AttributeModifier.Operation.ADD_NUMBER);

        /*EventLoggerUtility.logEventInitialization("HoneyIShrunkTheKids",
                "scale", AdvConfig.getShrunkScale(),
                "maxHP", AdvConfig.getShrunkMaxHP(),
                "attack dmg", AdvConfig.getShrunkAttack(),
                "movement speed", AdvConfig.getShrunkMovespeed(),
                "sneaking speed", AdvConfig.getShrunkSneakspeed(),
                "water movement", AdvConfig.getShrunkWaterMove(),
                "jump strength", AdvConfig.getShrunkJumpStr(),
                "safe fall distance", AdvConfig.getShrunkSafeFall()
        );*/

        EventLoggerUtility.logEventInitialization(this);
    }

    /**
     * Executes the shrinking event by calling the parent's execute method.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the shrinking event by calling the parent's terminate method.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
