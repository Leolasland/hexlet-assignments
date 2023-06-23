package exercise.controller;

import exercise.model.User;
import exercise.repository.UserRepository;
import exercise.service.SearchCriteria;
import exercise.service.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserRepository userRepository;

    // BEGIN
    @GetMapping("")
    public List<User> getUsers(@RequestParam(value = "firstName", required = false) String firstName,
                               @RequestParam(value = "lastName", required = false) String lastName) {
        List<Specification> specifications = new ArrayList<>();
        if (firstName != null) {
            specifications.add(new UserSpecification(new SearchCriteria<String>("firstName", firstName)));
        }
        if (lastName != null) {
            specifications.add(new UserSpecification(new SearchCriteria<String>("lastName", lastName)));
        }

        Specification<User> result = specifications.stream()
                .reduce(null, (specificationResult, specification) -> {
                    if (specificationResult == null) {
                        return specification;
                    }
                    return specificationResult.and(specification);
                });
        if (!CollectionUtils.isEmpty(specifications)) {
            return userRepository.findAll(result);
        }
        return userRepository.findAll();

    }
    // END
}

