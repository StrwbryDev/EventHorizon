package dev.strwbry.eventhorizon.events;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.utility.Config;
import dev.strwbry.eventhorizon.utility.MsgUtility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Manages the triggering and execution of events in the EventHorizon plugin.
 * This class handles random event selection based on weights, specific event triggering,
 * and maintains the current active event state.
 */
public class EventManager {
    /** Weight for positive events loaded from config */
    private double posWeight;
    /** Weight for negative events loaded from config */
    private double negWeight;
    /** Weight for neutral events loaded from config */
    private double neutralWeight;
    /** Random number generator for event selection */
    private final Random random = new Random();
    /** Currently active event */
    BaseEvent currentEvent;

    /**
     * Constructs a new EventManager and initializes event weights from config.
     */
    public EventManager() {
        loadWeightsFromConfig();
    }

    /**
     * Gets the currently active event.
     *
     * @return The current BaseEvent instance, or null if no event is active
     */
    public BaseEvent getCurrentEvent() {
        return currentEvent;
    }

    /**
     * Sets the current active event.
     *
     * @param event The BaseEvent to set as current
     */
    public void setCurrentEvent(BaseEvent event) {
        currentEvent = event;
    }

    /**
     * Triggers a random event based on the configured weights for different event classifications.
     * The event is selected using weighted probability and executed on the next server tick.
     * Broadcasts the selected event name and displays it as a title and action bar message.
     * Events are removed from the available pool after being triggered.
     */
    public void triggerEventByWeight() {
        Bukkit.getLogger().info("Triggering event...");

        // Get current available events and their counts
        EventInitializer eventInitializer = EventHorizon.getEventInitializer();
        int posCount = eventInitializer.getEventCount(EventClassification.POSITIVE);
        int negCount = eventInitializer.getEventCount(EventClassification.NEGATIVE);
        int neutralCount = eventInitializer.getEventCount(EventClassification.NEUTRAL);

        // Check if any events are available
        if (posCount == 0 && negCount == 0 && neutralCount == 0) {
            Bukkit.getLogger().warning("No more events available to trigger!");
            return;
        }

        // Use original weights from config, but set to 0 if no events available
        double dynamicPosWeight = posCount > 0 ? posWeight : 0.0;
        double dynamicNegWeight = negCount > 0 ? negWeight : 0.0;
        double dynamicNeutralWeight = neutralCount > 0 ? neutralWeight : 0.0;

        List<EventClassification> availableClassifications = new ArrayList<>();
        List<Double> dynamicWeights = new ArrayList<>();

        if (posCount > 0) {
            availableClassifications.add(EventClassification.POSITIVE);
            dynamicWeights.add(dynamicPosWeight);
        }
        if (negCount > 0) {
            availableClassifications.add(EventClassification.NEGATIVE);
            dynamicWeights.add(dynamicNegWeight);
        }
        if (neutralCount > 0) {
            availableClassifications.add(EventClassification.NEUTRAL);
            dynamicWeights.add(dynamicNeutralWeight);
        }

        double totalWeight = 0.0;
        for (Double weight : dynamicWeights) {
            totalWeight += weight;
        }

        // If no weights are available, select randomly from remaining events
        if (totalWeight == 0.0) {
            triggerRandomEvent();
            return;
        }

        double randomNumber = random.nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;

        for (int i = 0; i < availableClassifications.size(); i++) {
            cumulativeWeight += dynamicWeights.get(i);

            if (randomNumber < cumulativeWeight) {
                EventClassification eventClassification = availableClassifications.get(i);
                List<BaseEvent> selectedEvents = eventInitializer.getEnabledEvents().get(eventClassification);
                
                if (selectedEvents != null && !selectedEvents.isEmpty()) {
                    BaseEvent selectedEvent = selectedEvents.get(random.nextInt(selectedEvents.size()));

                    // Remove the event from available events
                    eventInitializer.removeEvent(selectedEvent);

                    MsgUtility.broadcast("Selected event: " + selectedEvent.getName());
                    MsgUtility.showTitleWithDurations(Bukkit.getServer(), selectedEvent.getName());
                    MsgUtility.actionBar(Bukkit.getServer(), selectedEvent.getName());
                    Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), () -> {
                        MsgUtility.sound(Bukkit.getServer(), Sound.sound(Key.key("minecraft:block.note_block.bell"), Sound.Source.BLOCK, 1.0f, 1.0f));
                    });
                    Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> selectedEvent.run());
                    return;
                }
            }
        }
    }

    /**
     * Triggers a specific event by its name.
     *
     * @param eventName The name of the event to trigger (case-insensitive)
     */
    public void triggerEventByName(String eventName) {
        BaseEvent event = EventHorizon.getEventInitializer().getRegisteredEvents().get(eventName.toLowerCase());
        if (event != null) {
            Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> event.run());
        }
    }

    /**
     * Triggers a completely random event from all enabled events,
     * regardless of their classification or weights.
     * Events are removed from the available pool after being triggered.
     */
    public void triggerRandomEvent() {
        List<BaseEvent> allEvents = new ArrayList<>();
        for (List<BaseEvent> events : EventHorizon.getEventInitializer().getEnabledEvents().values()) {
            allEvents.addAll(events);
        }
        
        if (allEvents.isEmpty()) {
            Bukkit.getLogger().warning("No more events available to trigger!");
            return;
        }
        
        BaseEvent randomEvent = allEvents.get(random.nextInt(allEvents.size()));
        
        // Remove the event from available events
        EventHorizon.getEventInitializer().removeEvent(randomEvent);
        
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> randomEvent.run());
    }

    /**
     * Loads event weights from the configuration file and logs the new values.
     */
    public void loadWeightsFromConfig() {
        this.posWeight = Config.getPosWeight();
        this.negWeight = Config.getNegWeight();
        this.neutralWeight = Config.getNeutralWeight();

        Bukkit.getLogger().info("Raw weights - Pos: " + this.posWeight +
                ", Neg: " + this.negWeight +
                ", Neutral: " + this.neutralWeight);
    }

    public Set<Player> getAvailableEventPlayers() {
        Set<Player> players = new HashSet<>();
        for (Player player : EventHorizon.getPlugin().getServer().getOnlinePlayers()) {
            if (player.getGameMode() != org.bukkit.GameMode.SPECTATOR) {
                players.add(player);
            }
        }
        return players;
    }

    /**
     * Resets all events for a new tournament by reloading them from the configuration
     * and clearing the current active event.
     */
    public void resetForNewTournament() {
        EventHorizon.getEventInitializer().resetEventsForNewTournament();
        currentEvent = null;
        Bukkit.getLogger().info("Events reset for new tournament");
    }
}