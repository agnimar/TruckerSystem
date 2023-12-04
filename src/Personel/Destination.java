package Personel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Destination{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double distance;
    private String startPoint;
    private String endPoint;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Status status;
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<CheckPoint> checkPoints;
    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL)
    private List<Truck> truck;
    @ManyToOne
    private Trucker driver;
    @ManyToOne
    private Manager responsibleManager;


    public Destination(double distance, String startPoint, String endPoint, LocalDate departureDate, LocalDate arrivalDate, Status status) {
        this.distance = distance;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    public Destination(double distance, String startPoint, String endPoint, LocalDate departureDate, LocalDate arrivalDate, Status status, Trucker driver, Manager responsibleManager) {
        this.distance = distance;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.driver = driver;
        this.responsibleManager = responsibleManager;
    }

    @Override
    public String toString(){
        return "ID: "+ id + "\nStatus: " + status+ "\nArrival Date: " + arrivalDate + "\nDeparture Date: " + departureDate  + "\nDriver:\n" + driver ;
    }

}
