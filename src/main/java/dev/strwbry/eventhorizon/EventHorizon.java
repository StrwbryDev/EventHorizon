package dev.strwbry.eventhorizon;

import dev.strwbry.eventhorizon.commands.CommandRootEventHorizon;
import dev.strwbry.eventhorizon.events.EventInitializer;
import dev.strwbry.eventhorizon.events.EventManager;
import dev.strwbry.eventhorizon.events.utility.EntityAddToWorldListener;
import dev.strwbry.eventhorizon.events.utility.PlayerDropItemListener;
import dev.strwbry.eventhorizon.events.utility.PlayerInventoryListener;
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
 * Main plugin class for EventHorizon, a Minecraft tournament management plugin.
 * This class handles plugin initialization, management of core components, and serves as the central access point
 * for various plugin functionalities.
 *
 * <p>The plugin manages tournament scheduling, event handling, and block manipulation through various components.</p>
 *
 * @since 1.0
 */
@SuppressWarnings("UnstableApiUsage")
public final class EventHorizon extends JavaPlugin implements CommandExecutor
{
    /** The tournament scheduler instance */
    private static Scheduler scheduler;

    /** The event initializer instance for setting up tournament events */
    private static EventInitializer eventInitializer;

    /** The event manager instance for handling tournament events */
    private static EventManager eventManager;

    /** Static reference to the plugin instance */
    private static EventHorizon plugin;

    /** Utility for handling block masks in WorldEdit operations */
    private static BlockMasks blockMasks;
    /** Utility for generating random patterns in WorldEdit operations */
    private static RandomPatterns randomPatterns;

    /** Collection of entity keys that need to be cleaned up */
    public static Collection<NamespacedKey> entityKeysToDelete = new ArrayList<>();

    /**
     * Gets the plugin instance.
     * @return The active EventHorizon plugin instance
     */
    public static EventHorizon getPlugin() {
        return plugin;
    }

    /**
     * Called when the plugin is enabled.
     * Initializes core components, sets up PlaceholderAPI integration if available,
     * and registers the main command structure.
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
        getServer().getPluginManager().registerEvents(new EntityAddToWorldListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);

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