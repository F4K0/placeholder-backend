package com.f4k0.placeholder.maps;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import place.Place;
import place.PlaceReview;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;

class MapsClientTest {

    private MapsClient mapsClient = new MapsClient();
    private static final Logger LOGGER = LoggerFactory.getLogger(MapsClient.class);

    @Mock
    private MapsConnection mapsConnection = mock(MapsConnection.class);

    private void setupMock() {
        Place place = new Place();
        place.setGmapsPlaceId("XD");

        Place enrichedPlace = new Place();
        enrichedPlace.setWebsite("DUMMY");
        enrichedPlace.setReviews(List.of(new PlaceReview()));
        enrichedPlace.setOpeningHours(List.of("DUMMY"));
        enrichedPlace.setIsOpen(true);
        enrichedPlace.setPlaceAddress("Kazincbarcika xD");

        Mono<Place> enrichedMono = Mono.just(enrichedPlace);

        Mockito.when(mapsConnection.getEnrichedPlace(any(Place.class))).thenReturn(enrichedMono);
        mapsClient.setMapsConnection(mapsConnection);
    }

    @Test
    void testIfClientEnrichesAnExistingPlaceWithAdditionalFields() {
        setupMock();
        Place place = new Place();
        place.setGmapsPlaceId("DUMMY");

        mapsClient.enrichPlace(place).subscribe(enrichedPlace -> {
            assertThat(enrichedPlace.getPlaceAddress(), notNullValue());
            assertThat(enrichedPlace.getPlaceAddress(), is(not(emptyString())));

            assertThat(enrichedPlace.getIsOpen(), notNullValue());

            assertThat(enrichedPlace.getWebsite(), notNullValue());
            assertThat(enrichedPlace.getWebsite(), is(not(emptyString())));

            assertThat(enrichedPlace.getOpeningHours(), notNullValue());
            assertThat(enrichedPlace.getOpeningHours(), is(not(empty())));
        });
    }
} 