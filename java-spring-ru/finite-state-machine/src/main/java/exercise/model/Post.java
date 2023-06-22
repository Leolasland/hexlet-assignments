package exercise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title;

    @Lob
    private String body;

    private PostState state = PostState.CREATED;

    // BEGIN

    public boolean publish() {
        if (state == PostState.CREATED) {
            state = PostState.PUBLISHED;
            return true;
        }
        return false;
    }

    public boolean archive() {
        if (state == PostState.CREATED || state == PostState.PUBLISHED) {
            state = PostState.ARCHIVED;
            return true;
        }
        return false;
    }
    // END
}
