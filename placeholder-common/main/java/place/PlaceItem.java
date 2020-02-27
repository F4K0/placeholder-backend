package place;

import lombok.Data;

/**
 * A data class representing an item on the menu
 * e.g. Diet Coke - 5$
 */
@Data
public class PlaceItem {
    private String name;
    private double price;
}