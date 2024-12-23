package data;
final public class GeographicPoint {
    // The geographical coordinates expressed as decimal degrees
    private final float latitude;
    private final float longitude;
    public GeographicPoint (float lat, float lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    // Getters
    public float getLatitude () { return latitude; }
    public float getLongitude () { return longitude; }
    @Override
    public boolean equals (Object o) {
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint gP = (GeographicPoint) o;
        eq = ((latitude == gP.latitude) && (longitude == gP.longitude));
        return eq;
    }
    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        return result;
    }
    @Override
    public String toString () {
        return "Geographic point {" + "latitude='" + latitude + '\'' +
                "longitude='" + longitude + "\'}";
    }

    public float calculateDistance(GeographicPoint other) {
        float R = 6371; // Earth's radius

        float lat1 = (float) Math.toRadians(this.latitude);
        float lon1 = (float) Math.toRadians(this.longitude);
        float lat2 = (float) Math.toRadians(other.latitude);
        float lon2 = (float) Math.toRadians(other.longitude);

        float latDistance = lat2 - lat1;
        float lonDistance = lon2 - lon1;

        // Haversine formula
        float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(lat1) * Math.cos(lat2) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));

        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));

        // La distancia en kilómetros
        return R * c;
    }
}
