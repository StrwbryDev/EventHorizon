package dev.strwbry.eventhorizon;

import dev.strwbry.eventhorizon.commands.CommandRootEventHorizon;
import dev.strwbry.eventhorizon.events.EventInitializer;
import dev.strwbry.eventhorizon.events.EventManager;
import dev.strwbry.eventhorizon.listeners.EntityAddToWorldListener;
import dev.strwbry.eventhorizon.listeners.ListenerManager;
import dev.strwbry.eventhorizon.listeners.PlayerDropItemListener;
import dev.strwbry.eventhorizon.listeners.PlayerInventoryListener;
import dev.strwbry.eventhorizon.events.utility.fawe.BlockMasks;
import dev.strwbry.eventhorizon.events.utility.fawe.RandomPatterns;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Main class for the EventHorizon tournament management plugin.
 * Handles initialization and provides access to core components like scheduling,
 * event management, and WorldEdit integration.
 */
@SuppressWarnings("UnstableApiUsage")
public final class EventHorizon extends JavaPlugin implements CommandExecutor
{
    /** Static reference to the plugin instance */
    private static EventHorizon plugin;
    /** The tournament scheduler instance */
    private static Scheduler scheduler;

    /** The event initializer instance for setting up tournament events */
    private static EventInitializer eventInitializer;

    /** The event manager instance for handling tournament events */
    private static EventManager eventManager;
    /** Utility for handling block masks in WorldEdit operations */
    private static BlockMasks blockMasks;
    /** Utility for generating random patterns in WorldEdit operations */
    private static RandomPatterns randomPatterns;

    /** Collection of entity keys that need to be cleaned up */
    public static Collection<NamespacedKey> entityKeysToDelete = new ArrayList<>();



    /**
     * Initializes the plugin on server startup.
     * Sets up core utilities (BlockMasks, RandomPatterns), tournament components (EventInitializer, EventManager, Scheduler),
     * registers event listeners, loads configuration, and sets up command/metrics systems.
     */
    @Override
    public void onEnable() {
        plugin = this;
        blockMasks = new BlockMasks();
        randomPatterns = new RandomPatterns();
        eventInitializer  = new EventInitializer();
        eventManager = new EventManager();
        scheduler = new Scheduler();

        // Register event listeners
        ListenerManager.initializeEnityAddToWorld();
        ListenerManager.initializePlayerDropItem();
        ListenerManager.unregisterAllListeners();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new PlaceholderEventHorizon().register();
        }
        saveResource("config.yml", /* replace */ false);

        //initializes eventhorizon base command
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(CommandRootEventHorizon.buildCommand());
        });
        int pluginId = 25805; // from bStats website
        Metrics metrics = new Metrics(this, pluginId);


    }

    /**
     * Called when the plugin is disabled.
     * Performs cleanup operations and logs shutdown message.
     */
    @Override
    public void onDisable() {
        getLogger().info("EventHorizon has been disabled.");
    }

    /**
     * Gets the plugin instance.
     * @return The active EventHorizon plugin instance
     */
    public static EventHorizon getPlugin() {
        return plugin;
    }
    /**
     * Gets the event manager instance.
     * @return The active EventManager instance
     */
    public static EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Gets the event initializer instance.
     * @return The active EventInitializer instance
     */
    public static EventInitializer getEventInitializer() {
        return eventInitializer;
    }

    /**
     * Gets the block masks utility instance.
     * @return The active BlockMasks instance
     */
    public static BlockMasks getBlockMasks() {
        return blockMasks;
    }

    /**
     * Gets the scheduler instance.
     * @return The active Scheduler instance
     */
    public static Scheduler getScheduler() {
        return scheduler;
    }
    /**
     * Gets the random patterns utility instance.
     * @return The active RandomPatterns instance
     */
    public static RandomPatterns getRandomPatterns() {
        return randomPatterns;
    }

}