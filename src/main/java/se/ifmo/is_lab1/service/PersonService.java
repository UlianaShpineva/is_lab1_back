package se.ifmo.is_lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.ifmo.is_lab1.dto.request.PersonRequest;
import se.ifmo.is_lab1.dto.response.PersonResponse;
import se.ifmo.is_lab1.exceptions.LocationNotFoundException;
import se.ifmo.is_lab1.model.Location;
import se.ifmo.is_lab1.model.Person;
import se.ifmo.is_lab1.repository.LocationRepository;
import se.ifmo.is_lab1.repository.PersonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    public List<PersonResponse> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons
                .stream()
                .map(this::toPersonResponse)
                .toList();
    }

    public PersonResponse createPerson(PersonRequest personRequest) {
        Person person = toPerson(personRequest);

        if (personRequest.getLocationId() != null) {
            person.setLocation(
                    locationRepository.findById(personRequest.getLocationId())
                            .orElseThrow(LocationNotFoundException::new)
            );
        } else {
            person.setLocation(null);
        }
        person = personRepository.save(person);
        return toPersonResponse(person);
    }

    private PersonResponse toPersonResponse(Person person) {
        return PersonResponse
                .builder()
                .id(person.getId())
                .name(person.getName())
                .eyeColor(person.getEyeColor())
                .hairColor(person.getHairColor())
                .location(person.getLocation())
                .weight(person.getWeight())
                .nationality(person.getNationality())
                .build();
    }

    private Person toPerson(PersonRequest req) {
        return Person
                .builder()
                .name(req.getName())
                .eyeColor(req.getEyeColor())
                .hairColor(req.getHairColor())
                .weight(req.getWeight())
                .nationality(req.getNationality())
                .build();
    }
}
