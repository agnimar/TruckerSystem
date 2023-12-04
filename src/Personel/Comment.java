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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String commentSection;
    @ManyToOne
    private Forum forum;
    @ManyToOne
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;

    public Comment(String title, String commentSection, Forum forum) {
        this.title = title;
        this.commentSection = commentSection;
        this.forum = forum;
    }

    public Comment(String title, String commentSection, Comment parentComment) {
        this.title = title;
        this.commentSection = commentSection;
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return  "Title = " + title +
                "\nComment = '" + commentSection + '\'';
    }
}
