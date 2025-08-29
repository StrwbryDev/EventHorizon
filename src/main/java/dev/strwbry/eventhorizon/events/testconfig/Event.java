package dev.strwbry.eventhorizon.events.testconfig;

/**
 * Represents a generic event in the EventHorizon system.
 * <p>
 * This interface is implemented by various event types such as
 * EffectEvent, AttributeEvent, BlockModificationEvent, etc.
 * Each event type has its own specific parameters and behavior.
 */
public sealed interface Event permits
        EffectEvent, AttributeEvent, BlockModificationEvent,
        DropModificationEvent, InventoryAdjustmentEvent, ItemSpawnEvent,
        MobSpawnEvent, GameruleEvent {
    String getName();
}
