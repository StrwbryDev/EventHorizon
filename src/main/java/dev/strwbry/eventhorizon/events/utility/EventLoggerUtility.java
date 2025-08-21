package dev.strwbry.eventhorizon.events.utility;

import dev.strwbry.eventhorizon.events.attributes.BaseAttribute;
import dev.strwbry.eventhorizon.utility.MsgUtility;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventLoggerUtility {
    private static final String EVENT_INIT_FORMAT = "%s event initialized with %s";

    /*
    Example usage:

    EventLogger.logEventInitialization("GrowthSpurt",
            "scale", AdvConfig.getGrowthScale(),
            "maxHP", AdvConfig.getGrowthMaxHP(),
            "attack dmg", AdvConfig.getGrowthAttack()
            );
    */

    // If the superclass is passed ALL fields, this method will log them automatically
    public static void logEventInitialization(Object instance) {
        Class<?> clazz = instance.getClass();
        String eventName = clazz.getSimpleName();

        // Get all fields from the superclass
        Field[] fields = clazz.getSuperclass().getDeclaredFields();
        List<Object> parameters = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(instance);
                // Skip static, final, and null fields
                if (!Modifier.isStatic(field.getModifiers()) &&
                        !Modifier.isFinal(field.getModifiers()) &&
                        value != null) {
                    parameters.add(field.getName().replaceAll("([A-Z])", "-$1").toLowerCase());
                    parameters.add(value);
                }
            } catch (IllegalAccessException e) {
                MsgUtility.warning("Failed to access field: " + field.getName());
            }
        }

        logEventInitialization(eventName, parameters.toArray());
    }

    public static void logEventInitialization(BaseAttribute instance) {
        String eventName = instance.getClass().getSimpleName();
        List<Object> parameters = new ArrayList<>();

        // Get attribute modifiers using reflection
        try {
            Field modifiersField = BaseAttribute.class.getDeclaredField("attributeModifiers");
            modifiersField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<Attribute, AttributeModifier> modifiers =
                    (Map<Attribute, AttributeModifier>) modifiersField.get(instance);

            for (Map.Entry<Attribute, AttributeModifier> entry : modifiers.entrySet()) {
                String attrName = entry.getKey().toString().toLowerCase().replace('_', ' ');
                parameters.add(attrName);
                parameters.add(entry.getValue().getAmount());
            }
        }

        catch (NoSuchFieldException | IllegalAccessException e) {
            MsgUtility.warning("Failed to access attribute modifiers");
        }

        logEventInitialization(eventName, parameters.toArray());
    }

    public static void logEventInitialization(String eventName, Object... parameters) {
        if (parameters.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters must be provided in pairs (name and value)");
        }

        StringBuilder parameterString = new StringBuilder();
        for (int i = 0; i < parameters.length; i += 2) {
            parameterString.append(parameters[i]).append(": %s");
            if (i < parameters.length - 2) {
                parameterString.append(", ");
            }
        }

        Object[] values = new Object[parameters.length / 2 + 1];
        values[0] = eventName;
        for (int i = 1, j = 1; i < values.length; i++, j += 2) {
            values[i] = parameters[j];
        }

        MsgUtility.log(EVENT_INIT_FORMAT.formatted(eventName, parameterString));
    }
}
