package exercise.controller;

import exercise.model.Person;
import exercise.dto.PersonDto;
import exercise.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    // Автоматически заполняем значение поля
    private final PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable long id) {
        return personRepository.findById(id).orElseThrow();
    }

    @GetMapping(path = "")
    public Iterable<Person> getPeople() {
        return this.personRepository.findAll();
    }

    // BEGIN
    @PostMapping("")
    public void savePerson(@RequestBody PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        personRepository.save(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable long id) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isPresent()) {
            personRepository.deleteById(byId.get().getId());
        }
    }

    @PatchMapping("/{id}")
    public void updatePerson(@PathVariable long id, @RequestBody PersonDto personDto) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isPresent()) {
            Person person = byId.get();
            person.setFirstName(personDto.getFirstName());
            person.setLastName(personDto.getLastName());
            personRepository.save(person);
        }
    }
    // END
}
