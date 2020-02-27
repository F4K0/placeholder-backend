package com.f4k0.placeholder.maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import place.Place;
import place.PlaceList;
import reactor.core.publisher.Mono;

@Component
public class MapsConnection {
    private static final String GMAPS_BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String GMAPS_KEY = System.getenv("GMAPS_API_KEY");
    private static final WebClient webClient = WebClient.builder().build();
    private static final Logger LOGGER = LoggerFactory.getLogger(MapsClient.class);

    MapsConnection() {
        // TODO PLCH-49
        //  probably put this somewhere else
        if (GMAPS_KEY == null) {
            // TODO PLCH-45
            //  throw new IllegalArgumentException("Please export environment variable GMAPS_API_KEY");
        }
    }

    public Mono<PlaceList> getNearby(double coordX, double coordY, int radius) {
        return webClient.get()
                .uri(GMAPS_BASE_URL + "nearbysearch/json?location="
                        + coordX + "," + coordY
                        + "&radius=" + radius
                        + "&key=" + GMAPS_KEY)
                .retrieve()
                .bodyToMono(PlaceList.class)
                .doOnError(error -> LOGGER.error("Error during GMaps API enrich query: {}", error.toString()));
    }

    public Mono<Place> getEnrichedPlace(Place place) {
        return webClient.get()
                .uri(GMAPS_BASE_URL + "details/json?place_id="
                        + place.getGmapsPlaceId()
                        + "&key=" + GMAPS_KEY)
                .retrieve()
                .bodyToMono(Place.class)
                .doOnError(error -> LOGGER.error("Error during GMaps API enrich query: {}", error.toString()));
    }
}