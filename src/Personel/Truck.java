package Personel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numPlate;
    private String make;
    private String model;
    private int year;
    private boolean isInTransit;
    private String location;
    @ManyToOne
    private Trucker trucker;
    @ManyToOne
    private Destination jobs;

    public Truck(String numPlate, String make, String model, int year, boolean isInTransit, String location) {
        this.numPlate = numPlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.isInTransit = isInTransit;
        this.location = location;
    }

    @Override
    public String toString() {
        return  "ID = " + id +
                "\nMake = '" + make + '\'' +
                "\nModel = '" + model + '\'' +
                "\nNumber plate = '" + numPlate + '\'';
    }
}
