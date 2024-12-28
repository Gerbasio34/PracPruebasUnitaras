package micromobility;

import data.GeographicPoint;
import data.ServiceID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a journey service, including details such as start and end points, duration, distance,
 * cost, and status (in progress or completed).
 */
public class JourneyService {

    // Attributes
    private LocalDateTime initDate; // Start date
    private LocalTime initHour; // Start time
    private int duration; // Duration of the journey in minutes
    private float distance; // Distance traveled (in kilometers)
    private float avgSpeed; // Average speed (in km/h)
    private GeographicPoint originPoint; // Starting point
    private GeographicPoint endPoint; // End point
    private LocalDateTime endDate; // End date
    private LocalTime endHour; // End time
    private BigDecimal importCost; // Total cost of the journey
    private ServiceID serviceID; // Unique service ID
    private boolean inProgress; // Service status (true = in progress)

    /**
     * Constructs a new JourneyService.
     *
     * @param serviceID The unique ID for this service.
     * @param originPoint The starting point of the journey.
     */
    public JourneyService(ServiceID serviceID, GeographicPoint originPoint) {
        this.serviceID = serviceID;

        this.initDate = null; // Set when the service starts
        this.initHour = null; // Set when the service starts
        this.duration = 0; // Calculated when the service ends
        this.distance = 0; // Initially 0
        this.avgSpeed = 0; // Calculated when the service ends
        this.originPoint = originPoint;

        this.endPoint = null; // Set when the service ends
        this.endDate = null; // Set when the service ends
        this.endHour = null; // Set when the service ends
        this.importCost = new BigDecimal(0); // Initially 0
        this.inProgress = false; // Service is not started initially
    }

    // GETTERS AND SETTERS

    /**
     * Gets the initialization date of the journey.
     *
     * @return The start date of the journey.
     */
    public LocalDateTime getInitDate() {
        return initDate;
    }

    /**
     * Sets the initialization date of the journey.
     *
     * @param initDate The start date to set.
     */
    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    /**
     * Gets the initialization time of the journey.
     *
     * @return The start time of the journey.
     */
    public LocalTime getInitHour() {
        return initHour;
    }

    /**
     * Sets the initialization time of the journey.
     *
     * @param initHour The start time to set.
     */
    public void setInitHour(LocalTime initHour) {
        this.initHour = initHour;
    }

    /**
     * Gets the duration of the journey.
     *
     * @return The duration of the journey in minutes.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the journey.
     *
     * @param duration The duration to set, in minutes.
     * @throws IllegalArgumentException if the duration is negative.
     */
    public void setDuration(int duration) throws IllegalArgumentException {
        if (duration < 0)
            throw new IllegalArgumentException("Duration must be a positive number.");
        this.duration = duration;
    }

    /**
     * Gets the distance traveled during the journey.
     *
     * @return The distance traveled (in kilometers).
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Sets the distance traveled during the journey.
     *
     * @param distance The distance to set (in kilometers).
     * @throws IllegalArgumentException if the distance is negative.
     */
    public void setDistance(float distance) throws IllegalArgumentException {
        if (distance < 0)
            throw new IllegalArgumentException("Distance must be a positive number.");
        this.distance = distance;
    }

    /**
     * Gets the average speed during the journey.
     *
     * @return The average speed (in km/h).
     */
    public float getAvgSpeed() {
        return avgSpeed;
    }

    /**
     * Sets the average speed during the journey.
     *
     * @param avgSpeed The average speed to set (in km/h).
     */
    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    /**
     * Gets the starting point of the journey.
     *
     * @return The starting point.
     */
    public GeographicPoint getOriginPoint() {
        return originPoint;
    }

    /**
     * Sets the starting point of the journey.
     *
     * @param originPoint The starting point to set.
     */
    public void setOriginPoint(GeographicPoint originPoint) {
        this.originPoint = originPoint;
    }

    /**
     * Gets the ending point of the journey.
     *
     * @return The ending point.
     */
    public GeographicPoint getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the ending point of the journey.
     *
     * @param endPoint The ending point to set.
     */
    public void setEndPoint(GeographicPoint endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Gets the end date of the journey.
     *
     * @return The end date.
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the journey.
     *
     * @param endDate The end date to set.
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the end time of the journey.
     *
     * @return The end time.
     */
    public LocalTime getEndHour() {
        return endHour;
    }

    /**
     * Sets the end time of the journey.
     *
     * @param endHour The end time to set.
     */
    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    /**
     * Gets the cost of the journey.
     *
     * @return The total cost of the journey.
     */
    public BigDecimal getImportCost() {
        return importCost;
    }

    /**
     * Sets the cost of the journey.
     *
     * @param importCost The cost to set.
     */
    public void setImportCost(BigDecimal importCost) {
        this.importCost = importCost;
    }

    /**
     * Gets the unique service ID.
     *
     * @return The service ID.
     */
    public ServiceID getServiceID() {
        return serviceID;
    }

    /**
     * Sets the unique service ID.
     *
     * @param serviceID The service ID to set.
     */
    public void setServiceID(ServiceID serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * Checks if the service is in progress.
     *
     * @return True if the service is in progress, false otherwise.
     */
    public boolean getInProgress() {
        return inProgress;
    }

    /**
     * Starts the journey service.
     *
     * @throws IllegalStateException if the service is already in progress.
     */
    public void setServiceInit() {
        if (inProgress) {
            throw new IllegalStateException("The service is already in progress.");
        }
        this.inProgress = true; // Mark the service as in progress
    }

    /**
     * Completes the journey service.
     *
     * @throws IllegalStateException if the service is not in progress.
     */
    public void setServiceFinish() {
        if (!inProgress) {
            throw new IllegalStateException("The service is not in progress.");
        }
        this.inProgress = false; // Mark the service as finished
    }
}
