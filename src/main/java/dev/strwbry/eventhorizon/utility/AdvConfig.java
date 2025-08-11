package dev.strwbry.eventhorizon.utility;

import dev.strwbry.eventhorizon.EventHorizon;


public class AdvConfig {

    public static void reload() {
        EventHorizon.reloadAdvConfig();
    }

    public static int getIceRadius(){
        return EventHorizon.getAdvConfig().getInt("block-modification.events.ice-is-nice.radius", 100);
    }
    public static int getIceHeight(){
        return EventHorizon.getAdvConfig().getInt("block-modification.events.ice-is-nice.height", 10);
    }
    public static int getIceHeightOffset(){
        return EventHorizon.getAdvConfig().getInt("block-modification.events.ice-is-nice.height-offset", 0);
    }
}
