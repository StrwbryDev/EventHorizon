package dev.strwbry.eventhorizon.events.utility;

import dev.strwbry.eventhorizon.utility.MsgUtility;

public class EventLoggerUtility {

    /**
     * @param eventName The name of the event being initialized.
     * @param keyValuePairs A variable number of key-value pairs representing event parameters.
     *                        Must be provided in pairs (key1, value1, key2, value2, ...).
     * Provides a method to log the initialization of events with their parameters.
     */

    public static void logEventInitialization(String eventName, Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide pairs of keys and values");
        }

        StringBuilder message = new StringBuilder();
        message.append(eventName).append(" event initialized with ");

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            message.append(keyValuePairs[i])
                    .append(": ")
                    .append(keyValuePairs[i + 1]);

            if (i < keyValuePairs.length - 2) {
                message.append(", ");
            }
        }

        MsgUtility.log(message.toString());
    }
}
