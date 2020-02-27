package place;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A data class representing a Place which can either be a restaurant, cinema etc.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class
Place {

    @JsonProperty("place_id")
    private String gmapsPlaceId;
    private String name;
    private Double locationX;
    private Double locationY;

    // TODO PLCH-50
    //  private List<String> photos;

    @JsonProperty("price_level")
    private Integer priceLevel;

    private PlaceType placeType;

    @JsonProperty("formatted_address")
    private String placeAddress;

    private List<String> openingHours;

    private String website;

    // TODO
    // private Set<PlaceItem> items;

    private Boolean isOpen;
    private Double rating;
    private List<PlaceReview> reviews;

    /**
     * This function will extract the property from the json called "geometry" which looks like:
     * "geometry" : {
     *    "location" : {
     *       "lat" : -33.8688197,
     *       "lng" : 151.2092955
     *    }
     *  }
     *  This is a nested JSON that's why we need these small tricks here.
     */
    @JsonProperty("geometry")
    @SuppressWarnings("unchecked")
    private void unpackLocation(Map<String,Object> geometry) {
        Map<String,Double> location = (Map<String,Double>)geometry.get("location");
        setLocationX(location.get("lat"));
        setLocationY(location.get("lng"));
    }

    /**
     * This function will extract the types from the "types" array that are relevant for us to store.
     * i.e. Present in our enum
     */
    @JsonProperty("types")
    private void unpackTypes(List<String> types) {
        Optional<String> type = types.stream().filter(e -> {
            try {
                PlaceType.valueOf(e.toUpperCase());
                return true;
            } catch (Exception ex) {
                return false;
            }
        }).findFirst();

        type.ifPresent(e -> PlaceType.valueOf(e.toUpperCase()));
    }

    /**
     * This function will extract the properties from the more detailed json (Enriched)
     * Since everything is stored under the property "result" we need a lot of extracting.
     */
    @JsonProperty("result")
    @SuppressWarnings("unchecked")
    private void unpackEnrichedResult(Map<String,Object> enrichedPlaceData) {
        setPlaceAddress((String)enrichedPlaceData.get("formatted_address"));
        setWebsite((String)enrichedPlaceData.get("website"));

        Map<String, Object> hours = (Map<String, Object>)enrichedPlaceData.get("opening_hours");
        Boolean isOpen = (Boolean)hours.get("open_now");
        List<String> openingHours = (List<String>) hours.get("weekday_text");
        setOpeningHours(openingHours);
        setIsOpen(isOpen);

        List<PlaceReview> reviews = (List<PlaceReview>) enrichedPlaceData.get("reviews");
        setReviews(reviews);

        setWebsite((String) enrichedPlaceData.get("website"));
    }
}