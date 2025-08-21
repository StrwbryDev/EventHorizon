package dev.strwbry.eventhorizon.events.attributes;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

/**
 * Represents an event that increases the player's physical attributes, simulating a growth spurt.
 * This event enhances various player capabilities including size, health, damage, movement, and interaction ranges.
 * Extends {@link BaseAttribute} and is classified as a NEUTRAL event.
 */
public class GrowthSpurt extends BaseAttribute {

    /**
     * Constructs a new GrowthSpurt event.
     * Initializes the event with NEUTRAL classification and applies various attribute modifiers:
     * - Doubles player scale
     * - Increases max health by 4 hearts
     * - Increases attack damage by 1
     * - Enhances knockback resistance by 50%
     * - Increases movement speeds by 50%
     * - Increases step height and safe fall distance
     * - Expands interaction ranges by 25%
     */
    public GrowthSpurt() {
        super(EventClassification.NEUTRAL, "growthSpurt");

        addAttributeModifier(Attribute.SCALE, AdvConfig.getGrowthScale(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.MAX_HEALTH, AdvConfig.getGrowthMaxHP(), AttributeModifier.Operation.ADD_NUMBER);
        addAttributeModifier(Attribute.ATTACK_DAMAGE, AdvConfig.getGrowthAttack(), AttributeModifier.Operation.ADD_NUMBER);
        addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, AdvConfig.getGrowthKnockResist(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.MOVEMENT_SPEED, AdvConfig.getGrowthMovespeed(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.SNEAKING_SPEED, AdvConfig.getGrowthSneakspeed(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.WATER_MOVEMENT_EFFICIENCY, AdvConfig.getGrowthWaterMove(), AttributeModifier.Operation.ADD_SCALAR);

        addAttributeModifier(Attribute.STEP_HEIGHT, AdvConfig.getGrowthStepHeight(), AttributeModifier.Operation.ADD_NUMBER);
        addAttributeModifier(Attribute.JUMP_STRENGTH, AdvConfig.getGrowthJumpStr(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.SAFE_FALL_DISTANCE, AdvConfig.getGrowthSafeFall(), AttributeModifier.Operation.ADD_NUMBER);

        addAttributeModifier(Attribute.BLOCK_INTERACTION_RANGE, AdvConfig.getGrowthBlockInteract(), AttributeModifier.Operation.ADD_SCALAR);
        addAttributeModifier(Attribute.ENTITY_INTERACTION_RANGE, AdvConfig.getGrowthEntityInteract(), AttributeModifier.Operation.ADD_SCALAR);

        /*EventLoggerUtility.logEventInitialization("GrowthSpurt",
                "scale", AdvConfig.getGrowthScale(),
                "maxHP", AdvConfig.getGrowthMaxHP(),
                "attack dmg", AdvConfig.getGrowthAttack(),
                "knockback resistance", AdvConfig.getGrowthKnockResist(),
                "movement speed", AdvConfig.getGrowthMovespeed(),
                "sneaking speed", AdvConfig.getGrowthSneakspeed(),
                "water movement", AdvConfig.getGrowthWaterMove(),
                "step height", AdvConfig.getGrowthStepHeight(),
                "jump strength", AdvConfig.getGrowthJumpStr(),
                "safe fall distance", AdvConfig.getGrowthSafeFall(),
                "block interaction range", AdvConfig.getGrowthBlockInteract(),
                "entity interaction range", AdvConfig.getGrowthEntityInteract()
        );*/

        EventLoggerUtility.logEventInitialization(this);
    }

    /**
     * Executes the growth spurt event by calling the parent's execute method.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the growth spurt event by calling the parent's terminate method.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}