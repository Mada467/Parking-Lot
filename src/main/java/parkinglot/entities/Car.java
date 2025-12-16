package parkinglot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Owner is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @NotNull(message = "Parking spot is required")
    @Size(min = 1, max = 50, message = "Parking spot must be between 1 and 50 characters")
    @Column(name = "parking_spot", nullable = false, length = 50)
    private String parkingSpot;

    @NotNull(message = "License plate is required")
    @Size(min = 2, max = 20, message = "License plate must be between 2 and 20 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "License plate must contain only uppercase letters and numbers")
    @Column(name = "license_plate", nullable = false, length = 20, unique = true)
    private String licensePlate;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarPhoto photo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public CarPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(CarPhoto photo) {
        this.photo =photo;
    }
}