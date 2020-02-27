package com.f4k0.placeholder.maps;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import place.Place;
import place.PlaceList;
import reactor.core.publisher.Mono;

public class MapsClient {

    private static final int RADIUS = 500;
    private static final Logger LOGGER = LoggerFactory.getLogger(MapsClient.class);

    @Autowired
    @Setter
    @Getter
    private MapsConnection mapsConnection;

    /**
     * This function returns a list of the places near to the given coordinates and in a RADIUS defined on the class level.
     * This will call the more generic endpoint of the GMaps API which is not adding some important info
     * like opening hours, address, website etc.
     * These can be added with the enriching function.
     * Returns null if something goes wrong during the query.
     */
    public Mono<PlaceList> getNearbyPlaces(double coordX, double coordY) {
        return mapsConnection.getNearby(coordX, coordY, RADIUS);

    }

    /**
     * This function will enrich a given place by calling the more specific endpoint from the GMaps API.
     * It returns an empty optional if the enriching was unsuccessful.
     */
    public Mono<Place> enrichPlace(Place place) {
        if (StringUtils.isEmpty(place.getGmapsPlaceId())) {
            throw new IllegalArgumentException("Can't enrich place without a GMaps ID.");
        }

        Mono<Place> enrichedPlace = mapsConnection.getEnrichedPlace(place);

        return enrichedPlace.map(e -> {
            place.setOpeningHours(e.getOpeningHours());
            place.setPlaceAddress(e.getPlaceAddress());
            place.setIsOpen(e.getIsOpen());
            place.setWebsite(e.getWebsite());
            return place;
        });
    }
}