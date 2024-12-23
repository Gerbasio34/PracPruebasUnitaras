package micromobility;

import data.GeographicPoint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JourneyService {
    // Attributes
    private LocalDateTime initDate; // Start date
    private LocalTime initHour; // Start time
    private int duration; // Duration of the journey
    private float distance; // Distance traveled (in kilometers)
    private float avgSpeed; // Average speed (in km/h)
    private GeographicPoint originPoint; // Starting point
    private GeographicPoint endPoint; // End point
    private LocalDateTime endDate; // End date
    private LocalTime endHour; // End time
    private BigDecimal importCost; // Total cost of the journey
    private String serviceID; // Unique service ID
    private boolean inProgress; // Service status (true = in progress)

    // Constructor
    public JourneyService(String serviceID, GeographicPoint originPoint) {
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
        this.importCost = new BigDecimal(0); // Calculated when the service ends
        this.inProgress = false; // Service is not started initially
    }

    // GETTERS SETTERS

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public LocalTime getInitHour() {
        return initHour;
    }

    public void setInitHour(LocalTime initHour) {
        this.initHour = initHour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public GeographicPoint getOriginPoint() {
        return originPoint;
    }

    public void setOriginPoint(GeographicPoint originPoint) {
        this.originPoint = originPoint;
    }

    public GeographicPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(GeographicPoint endPoint) {
        this.endPoint = endPoint;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public BigDecimal getImportCost() {
        return importCost;
    }

    public void setImportCost(BigDecimal importCost) {
        this.importCost = importCost;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    // Method to initialize the service
    public void setServiceInit() {
        if (inProgress) {
            throw new IllegalStateException("The service is already in progress.");
        }
        this.inProgress = true; // Mark the service as in progress
    }

    public void setServiceFinish() {
        if (!inProgress) {
            throw new IllegalStateException("The service is not in progress.");
        }
        this.inProgress = false; // Mark the service as finished
    }
}