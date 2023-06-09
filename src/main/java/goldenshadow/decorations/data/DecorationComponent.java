package goldenshadow.decorations.data;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Class which represents a decoration component
 */
public class DecorationComponent {

    private final EntityData data;
    private final Vector offsetVector;
    private final Vector direction;

    /**
     * Creates a new decoration component
     * @param data EntityData of the component
     * @param componentLocation The components location
     * @param playerLoc The root location
     */
    public DecorationComponent(EntityData data, Location componentLocation, Location playerLoc) {
        this.data = data;
        offsetVector = componentLocation.toVector().subtract(playerLoc.toVector());
        direction = componentLocation.getDirection();
    }

    /**
     * Creates a new decoration component. This constructor should only be called by its type adapter
     * @param data EntityData of the component
     * @param offsetVector The offset vector
     * @param direction The direction
     */
    public DecorationComponent(EntityData data, Vector offsetVector, Vector direction) {
        this.data = data;
        this.offsetVector = offsetVector;
        this.direction = direction;
    }


    /**
     * Getter for the entity data
     * @return The entity data
     */
    public EntityData getData() {
        return data;
    }

    /**
     * Getter for the offset vector
     * @return The offset vector
     */
    public Vector getOffsetVector() {
        return offsetVector;
    }

    /**
     * Getter for the direction
     * @return The direction
     */
    public Vector getDirection() {
        return direction;
    }
}
