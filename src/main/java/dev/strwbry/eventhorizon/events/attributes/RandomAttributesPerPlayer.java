package dev.strwbry.eventhorizon.events.attributes;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.MsgUtility;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.attribute.Attribute.*;

/**
 * This event assigns each player a unique set of random attribute modifiers.
 * Each player will have different attributes and values, creating a diverse gameplay experience.
 */
public class RandomAttributesPerPlayer extends BaseAttribute {
    /** Configurable parameters */
    protected int attributeCount = 5;

    /** Available attributes to choose from */
    private static final Attribute[] AVAILABLE_ATTRIBUTES = {
            ARMOR,
            ARMOR_TOUGHNESS,
            ATTACK_DAMAGE,
            ATTACK_KNOCKBACK,
            ATTACK_SPEED,
            BLOCK_BREAK_SPEED,
            BLOCK_INTERACTION_RANGE,
            ENTITY_INTERACTION_RANGE,
            FALL_DAMAGE_MULTIPLIER,
            GRAVITY,
            JUMP_STRENGTH,
            KNOCKBACK_RESISTANCE,
            MAX_ABSORPTION,
            MAX_HEALTH,
            MINING_EFFICIENCY,
            MOVEMENT_EFFICIENCY,
            MOVEMENT_SPEED,
            OXYGEN_BONUS,
            SAFE_FALL_DISTANCE,
            SCALE,
            SNEAKING_SPEED,
            STEP_HEIGHT,
            SUBMERGED_MINING_SPEED,
            SWEEPING_DAMAGE_RATIO,
            WATER_MOVEMENT_EFFICIENCY
    };

    /** Attribute value ranges */
    private static final Map<Attribute, double[]> ATTRIBUTE_RANGES;

    static {
        Map<Attribute, double[]> ranges = new HashMap<>();
        ranges.put(ARMOR, new double[]{-10.0, 10.0});
        ranges.put(ARMOR_TOUGHNESS, new double[]{-5.0, 10.0});
        ranges.put(ATTACK_DAMAGE, new double[]{-5.0, 5.0});
        ranges.put(ATTACK_KNOCKBACK, new double[]{0.0, 10.0});
        ranges.put(ATTACK_SPEED, new double[]{-1.0, 10.0});
        ranges.put(BLOCK_BREAK_SPEED, new double[]{-0.5, 3.0});
        ranges.put(BLOCK_INTERACTION_RANGE, new double[]{-4.0, 10.0});
        ranges.put(ENTITY_INTERACTION_RANGE, new double[]{-2.0, 10.0});
        ranges.put(FALL_DAMAGE_MULTIPLIER, new double[]{-1.0, 2.0});
        ranges.put(GRAVITY, new double[]{-0.1, 0.1});
        ranges.put(JUMP_STRENGTH, new double[]{-0.4, 1.5});
        ranges.put(KNOCKBACK_RESISTANCE, new double[]{-1.0, 1.0});
        ranges.put(MAX_HEALTH, new double[]{-19.0, 20.0});
        ranges.put(MINING_EFFICIENCY, new double[]{-1.0, 10.0});
        ranges.put(MOVEMENT_EFFICIENCY, new double[]{-1.0, 1.0});
        ranges.put(MOVEMENT_SPEED, new double[]{-0.1, 1.0});
        ranges.put(OXYGEN_BONUS, new double[]{-10.0, 10.0});
        ranges.put(SAFE_FALL_DISTANCE, new double[]{-3.0, 10.0});
        ranges.put(SCALE, new double[]{-1.0, 2.0});
        ranges.put(SNEAKING_SPEED, new double[]{-0.3, 2.0});
        ranges.put(STEP_HEIGHT, new double[]{-0.6, 5.0});
        ranges.put(SUBMERGED_MINING_SPEED, new double[]{-0.5, 2.0});
        ranges.put(SWEEPING_DAMAGE_RATIO, new double[]{0.0, 1.0});
        ranges.put(WATER_MOVEMENT_EFFICIENCY, new double[]{-2.0, 2.0});

        ATTRIBUTE_RANGES = Map.copyOf(ranges);
    }

    /** Default range for attributes not in the map */
    private static final double[] DEFAULT_RANGE = {-1.0, 1.0};

    public RandomAttributesPerPlayer() {
        super(EventClassification.NEUTRAL, "randomAttributesPerPlayer");
    }

    /** This method executes the RandomAttributesPerPlayer event. */
    @Override
    public void execute() {
        super.execute();
    }

    /** This method terminates the RandomAttributesPerPlayer event. */
    @Override
    public void terminate() {
        super.terminate();
    }

    /**
     * Applies unique random attribute modifiers to each online player.
     * Each player gets their own set of random attributes.
     */
    @Override
    public void applyAttributeModifiersToAllPlayers() {
        int successCount = 0;
        Set<Player> players = getAvailableEventPlayers();

        for (Player player : players) {
            try {
                attributeModifiers.clear();
                generateRandomAttributes();

                applyAttributeModifiersToPlayer(player);
                successCount++;
            } catch (Exception e) {
                MsgUtility.warning("Failed to apply attributes to player " + player.getName() + ": " + e.getMessage());
            }
        }
        MsgUtility.log("Applied unique random attribute modifiers to " + successCount + "/" + players.size() + " players for event: " + this.eventName);
    }

    /**
     * Generates random attributes with random values that will be applied to all players.
     */
    private void generateRandomAttributes() {
        Set<Attribute> selectedAttributes = selectRandomAttributes();

//        // Log which attributes were selected
//        StringBuilder attributeList = new StringBuilder();
//        for (Attribute attribute : selectedAttributes) {
//            if (!attributeList.isEmpty()) {
//                attributeList.append(", ");
//            }
//            attributeList.append("<yellow>").append(attribute.getKey().getKey()).append("</yellow>");
//        }
//        MsgUtility.log("<green>Selected random attributes: " + attributeList.toString());

        for (Attribute attribute : selectedAttributes) {
            double randomValue = generateRandomValue(attribute);
            addAttributeModifier(attribute, randomValue, AttributeModifier.Operation.ADD_NUMBER);

//            // Log each attribute modification with its value
//            String valueColor = randomValue >= 0 ? "<green>" : "<red>";
//            MsgUtility.log("<gray>- " + attribute.getKey().getKey() + ": " + valueColor + String.format("%.2f", randomValue) + "</gray>");
        }
    }

    /**
     * Selects random attributes from the available attributes.
     */
    private Set<Attribute> selectRandomAttributes() {
        Set<Attribute> selected = new HashSet<>();
        List<Attribute> available = Arrays.asList(AVAILABLE_ATTRIBUTES);
        Collections.shuffle(available, random);

        for (int i = 0; i < Math.min(attributeCount, available.size()); i++) {
            selected.add(available.get(i));
        }

        return selected;
    }

    /**
     * Generates a random value appropriate for the given attribute.
     */
    private double generateRandomValue(Attribute attribute) {
        double[] range = ATTRIBUTE_RANGES.getOrDefault(attribute, DEFAULT_RANGE);
        double min = range[0];
        double max = range[1];
        return min + random.nextDouble() * (max - min);
    }
}
