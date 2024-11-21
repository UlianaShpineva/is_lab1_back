package se.ifmo.is_lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ifmo.is_lab1.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
