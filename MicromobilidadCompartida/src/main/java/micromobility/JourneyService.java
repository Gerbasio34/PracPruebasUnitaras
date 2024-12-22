package micromobility;

import data.GeographicPoint;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public class JourneyService {
    // Attributes
    private LocalDate initDate; // Start date
    private LocalTime initHour; // Start time
    private Duration duration; // Duration of the journey
    private double distance; // Distance traveled (in kilometers)
    private double avgSpeed; // Average speed (in km/h)
    private GeographicPoint originPoint; // Starting point
    private GeographicPoint endPoint; // End point
    private LocalDate endDate; // End date
    private LocalTime endHour; // End time
    private double importCost; // Total cost of the journey
    private String serviceID; // Unique service ID
    private boolean inProgress; // Service status (true = in progress)

    // Constructor
    public JourneyService(String serviceID, GeographicPoint originPoint) {
        this.serviceID = serviceID;
        this.originPoint = originPoint;
        this.initDate = null; // Set when the service starts
        this.initHour = null; // Set when the service starts
        this.duration = null; // Calculated when the service ends
        this.distance = 0.0; // Initially 0
        this.avgSpeed = 0.0; // Calculated when the service ends
        this.endPoint = null; // Set when the service ends
        this.endDate = null; // Set when the service ends
        this.endHour = null; // Set when the service ends
        this.importCost = 0.0; // Calculated when the service ends
        this.inProgress = false; // Service is not started initially
    }

    // Getters
    public LocalDate getInitDate() {
        return initDate;
    }

    public LocalTime getInitHour() {
        return initHour;
    }

    public Duration getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public GeographicPoint getOriginPoint() {
        return originPoint;
    }

    public GeographicPoint getEndPoint() {
        return endPoint;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public double getImportCost() {
        return importCost;
    }

    public String getServiceID() {
        return serviceID;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    // Method to initialize the service
    public void setServiceInit() {
        if (inProgress) {
            throw new IllegalStateException("The service is already in progress.");
        }
        this.initDate = LocalDate.now();
        this.initHour = LocalTime.now();
        this.inProgress = true; // Mark the service as in progress
        System.out.println("Service started: " + initDate + " at " + initHour);
    }

    public void setServiceFinish(GeographicPoint endPoint, double distance) {
        if (!inProgress) {
            throw new IllegalStateException("The service is not in progress.");
        }
        this.endDate = LocalDate.now();
        this.endHour = LocalTime.now();
        this.endPoint = endPoint;
        this.distance = distance;

        // Calculate the journey duration
        this.duration = Duration.between(initHour, endHour);

        // Calculate the average speed
        long durationInMinutes = this.duration.toMinutes();
        if (durationInMinutes > 0) {
            this.avgSpeed = (distance / durationInMinutes) * 60; // km/h
        }

        // Calculate the journey cost
        calculateImport();

        this.inProgress = false; // Mark the service as finished
        System.out.println("Service finished: " + endDate + " at " + endHour);
    }

    private void calculateImport() {
        long durationInMinutes = this.duration.toMinutes();
        this.importCost = (distance * 1.0) + (durationInMinutes * 0.50); // Simple rate
    }
}
