package Personel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User{
    private boolean isAdmin;
    @OneToMany(mappedBy = "responsibleManager", cascade = CascadeType.ALL)
    private List<Destination> managedDestinations;

    public Manager(String password, String name, String lastname, LocalDate dateOfBirth, String phoneNum, String email, boolean isAdmin) {
        super(password, name, lastname, dateOfBirth, phoneNum, email);
        this.isAdmin = isAdmin;
    }
}
