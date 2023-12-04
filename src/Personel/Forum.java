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
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @OneToMany (mappedBy = "forum", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Forum(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return  "ID = " + id +
                "\nTitle = '" + title + '\'' +
                "\nDescription = '" + description + '\'';
    }
}
