package place;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Basic data class representing a review
 * relativeTime is a text returned by the GMaps api representing the review date e.g. 11 months ago
 * time is in unix time that's why we store it as long
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceReview {
    @JsonProperty("author_name")
    private String author;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("text")
    private String text;

    @JsonProperty("time")
    private long time;

    @JsonProperty("relative_time_description")
    private String relativeTime;
}