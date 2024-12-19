package micromobility;

import data.GeographicPoint;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public class JourneyService {
    // Atributos
    private LocalDate initDate; // Fecha de inicio
    private LocalTime initHour; // Hora de inicio
    private Duration duration; // Duración del viaje
    private double distance; // Distancia recorrida (en kilómetros)
    private double avgSpeed; // Velocidad promedio (en km/h)
    private GeographicPoint originPoint; // Punto de inicio
    private GeographicPoint endPoint; // Punto final
    private LocalDate endDate; // Fecha de fin
    private LocalTime endHour; // Hora de fin
    private double importCost; // Importe total del viaje
    private String serviceID; // ID único del servicio
    private boolean inProgress; // Estado del servicio (true = en progreso)

    // Constructor
    public JourneyService(String serviceID, GeographicPoint originPoint) {
        this.serviceID = serviceID;
        this.originPoint = originPoint;
        this.initDate = null; // Se establecerá cuando se inicie el servicio
        this.initHour = null; // Se establecerá cuando se inicie el servicio
        this.duration = null; // Calculada al finalizar el servicio
        this.distance = 0.0; // Inicialmente 0
        this.avgSpeed = 0.0; // Calculada al finalizar el servicio
        this.endPoint = null; // Se establecerá al finalizar el servicio
        this.endDate = null; // Se establecerá al finalizar el servicio
        this.endHour = null; // Se establecerá al finalizar el servicio
        this.importCost = 0.0; // Calculada al finalizar el servicio
        this.inProgress = false; // Servicio no iniciado inicialmente
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

    /**
     * Finaliza el servicio de viaje, registrando la fecha, hora de fin y calculando los valores relacionados.
     *
     * @param endPoint   Punto final del viaje.
     * @param distance   Distancia recorrida (en kilómetros).
     */
    public void setServiceFinish(GeographicPoint endPoint, double distance) {
        if (!inProgress) {
            throw new IllegalStateException("El servicio no está en progreso.");
        }
        this.endDate = LocalDate.now();
        this.endHour = LocalTime.now();
        this.endPoint = endPoint;
        this.distance = distance;

        // Calcular la duración del viaje
        this.duration = Duration.between(initHour, endHour);

        // Calcular la velocidad promedio
        long durationInMinutes = this.duration.toMinutes();
        if (durationInMinutes > 0) {
            this.avgSpeed = (distance / durationInMinutes) * 60; // km/h
        }

        // Calcular el importe del viaje
        calculateImport();

        this.inProgress = false; // Marcar el servicio como finalizado
        System.out.println("Servicio finalizado: " + endDate + " a las " + endHour);
    }

    /**
     * Calcula el importe total del viaje en función de la distancia y la duración.
     * Ejemplo: $1 por kilómetro + $0.50 por minuto.
     */
    private void calculateImport() {
        long durationInMinutes = this.duration.toMinutes();
        this.importCost = (distance * 1.0) + (durationInMinutes * 0.50); // Tarifa simple
    }

}
