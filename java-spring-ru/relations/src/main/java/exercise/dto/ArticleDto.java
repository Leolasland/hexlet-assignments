package exercise.dto;

import exercise.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// BEGIN
@Getter
@Setter
public class ArticleDto {

    private long id;

    private String name;

    private String body;

    private Category category;
}
// END
