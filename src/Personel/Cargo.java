package Personel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contractedCompany;
    private String product;
    private int weight;
    private boolean hasExpiration;

    public Cargo(String contractedCompany, String product, int weight, boolean hasExpiration) {
        this.contractedCompany = contractedCompany;
        this.product = product;
        this.weight = weight;
        this.hasExpiration = hasExpiration;
    }


    @Override
    public String toString() {
        return  "ID = " + id +
                "\nCompany = '" + contractedCompany + '\'' +
                "\nProduct = '" + product + '\'' +
                "\nWeight = '" + weight + '\'';
    }
}
