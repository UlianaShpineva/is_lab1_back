package se.ifmo.is_lab1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se.ifmo.is_lab1.dto.request.PersonRequest;
import se.ifmo.is_lab1.dto.response.PersonResponse;
import se.ifmo.is_lab1.service.PersonService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public List<PersonResponse> getAllPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    public PersonResponse createPerson(@RequestBody @Valid PersonRequest personRequest) {
        return personService.createPerson(personRequest);
    }
}
