The idea is to use Google's Map API for getting POIs.
According to their website the pricing should be good initially because we won't be charged until $200 per month.
[API Docs](https://developers.google.com/places/web-service/search)
More specifically we would need [Nearby Search](https://developers.google.com/places/web-service/search#PlaceSearchRequests)
However the disclaimer says this returns back a huge amount of data that can bump the pricing. This might cause some
disrupts in the future.

Probably we will have to use the returned JSON to filter the data we need (name, price range, website etc.). This should
not be a problem since we are getting back a well known format.

About the technical details. We can send basic HTTP GET requests to their URLs (HttpClient?).
However we will have to define the API key somewhere in our project that should not be shared in the source control.
Probably an application property will do.