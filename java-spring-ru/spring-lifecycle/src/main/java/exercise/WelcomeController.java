package exercise;

import exercise.daytimes.Daytime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final Daytime daytime;
    private final Meal meal;

    @GetMapping("/daytime")
    public String enjoyMeal() {
        return "It is "+daytime.getName()+" now. Enjoy your " + meal.getMealForDaytime(daytime.getName());
    }
}
// END
