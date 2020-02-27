package place;

import lombok.Data;

import java.util.List;

/**
 * Basic data class that stores opening and closing hours in a 24-hour format without the colon
 * For example 9PM = 21:00 => 2100
 * We are forced to use this because GMaps returns this in the same format
 * However it also returns a list with a string representation e.g.
 * ["Monday: 9:00 am – 9:00 pm",
 * "Tuesday: 9:00 am – 9:00 pm",
 * "Wednesday: 9:00 am – 9:00 pm",
 * "Thursday: 9:00 am – 9:00 pm",
 * "Friday: 9:00 am – 9:00 pm",
 * "Saturday: 9:00 am – 9:00 pm",
 * "Sunday: 9:00 am – 9:00 pm"]
 * so we are storing that too
 */
@Data
public class PlaceOpeningHours {
    private List<String> stringRepresentation;
    // TODO PLCH-51
    //  only string representation for now
}