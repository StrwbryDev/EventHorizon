package dev.strwbry.eventhorizon.events.gamerule;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.BaseEvent;
import dev.strwbry.eventhorizon.events.EventClassification;
import org.bukkit.GameRule;
import org.bukkit.World;

/** Class is WIP */
public class BaseGameRule<T> extends BaseEvent
{

    public BaseGameRule(EventClassification classification, String eventName)
    {
        super(classification, eventName);
    }

    @Override
    public void execute() {

    }

    @Override
    public void terminate() {

    }

    protected void applyGameRuleToWorld(World world, GameRule<T> gameRule, T value) {
        //initialValue = world.getGameRuleValue(gamerule);
        world.setGameRule(gameRule, value);
    }
    protected void applyGameRuleToAllWorlds(GameRule<T> gameRule, T value) {
        for (World world : EventHorizon.getPlugin().getServer().getWorlds()) {
            applyGameRuleToWorld(world, gameRule, value);
        }
    }

    protected void restoreGameRuleToWorld(World world, GameRule<T> gameRule) {
        //world.setGameRule(gamerule, initialValue);
    }

}
