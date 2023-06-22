package exercise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// BEGIN
@Getter
@Setter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Lob
    private String body;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
// END
