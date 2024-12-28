package data;

/**
 * Represents a geographic point defined by latitude and longitude in decimal degrees.
 */
final public class GeographicPoint {

    /**
     * The latitude of the geographic point, expressed in decimal degrees.
     */
    private final float latitude;

    /**
     * The longitude of the geographic point, expressed in decimal degrees.
     */
    private final float longitude;

    /**
     * Constructs a {@code GeographicPoint} with the specified latitude and longitude.
     *
     * @param lat the latitude of the geographic point, in decimal degrees.
     * @param lon the longitude of the geographic point, in decimal degrees.
     */
    public GeographicPoint(float lat, float lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Returns the latitude of this geographic point.
     *
     * @return the latitude in decimal degrees.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of this geographic point.
     *
     * @return the longitude in decimal degrees.
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Compares this geographic point to the specified object.
     *
     * @param o the object to compare with.
     * @return {@code true} if the specified object is equal to this geographic point; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint gP = (GeographicPoint) o;
        return (latitude == gP.latitude) && (longitude == gP.longitude);
    }

    /**
     * Returns the hash code for this geographic point.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        return result;
    }

    /**
     * Returns a string representation of this geographic point.
     *
     * @return a string representation in the format "Geographic point {latitude='X', longitude='Y'}".
     */
    @Override
    public String toString () {
        return "Geographic point {" + "latitude='" + latitude + '\'' +
                "longitude='" + longitude + "\'}";
    }


    /**
     * Calculates the distance between this geographic point and another geographic point using the Haversine formula.
     *
     * @param other the other geographic point to calculate the distance to.
     * @return the distance between the two points in kilometers.
     */
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
