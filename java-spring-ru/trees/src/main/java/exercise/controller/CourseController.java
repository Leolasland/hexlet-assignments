package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCorses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    @GetMapping(path = "/{id}/previous")
    public Iterable<Course> getPreviousCourses(@PathVariable long id) {
        Course byId = courseRepository.findById(id);
        List<Long> parentIdsFromPath = getParentIdsFromPath(byId.getPath());
        if (parentIdsFromPath.isEmpty()) {
            return Collections.emptyList();
        }
        return courseRepository.findAllById(parentIdsFromPath);
    }

    private List<Long> getParentIdsFromPath(String path) {
        if (path != null && !"".equals(path)) {
            return Arrays.stream(path.split("\\."))
                    .map(Long::parseLong)
                    .toList();
        }
        return Collections.emptyList();
    }
    // END

}
