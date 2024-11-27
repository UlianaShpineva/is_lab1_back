package se.ifmo.is_lab1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ifmo.is_lab1.model.Movie;
import se.ifmo.is_lab1.model.enums.MovieGenre;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("select s from Movie s " +
            "join s.director where " +
            "(:groupName is null or s.name ilike %:groupName%) " +
            "and (:directorName is null or s.director.name ilike %:directorName%)"
    )
    Page<Movie> findByFilter(
            @Param("groupName") String groupName,
            @Param("directorName") String adminName,
            Pageable pageable
    );

    @Query("SELECT AVG(m.usaBoxOffice) FROM Movie m")
    Double findAverageUsaBoxOffice();

    @Query("SELECT m FROM Movie m WHERE m.director.id = (SELECT MAX(m2.director.id) FROM Movie m2)")
    Movie findMovieWithMaxDirector();

    @Query("SELECT m FROM Movie m WHERE m.tagline LIKE CONCAT(:prefix, '%')")
    List<Movie> findMoviesByTaglinePrefix(@Param("prefix") String prefix);

    @Query("SELECT SUM(m.oscarsCount) FROM Movie m WHERE m.genre = :sourceGenre")
    Integer findTotalOscarsByGenre(@Param("sourceGenre") MovieGenre sourceGenre);

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.genre = :targetGenre")
    Long countMoviesByGenre(@Param("targetGenre") MovieGenre targetGenre);

    @Modifying
    @Query("UPDATE Movie m SET m.oscarsCount = m.oscarsCount + :newOscarsCount WHERE m.genre = :targetGenre")
    void redistributeOscars(@Param("newOscarsCount") int newOscarsCount, @Param("targetGenre") MovieGenre targetGenre);

    @Modifying
    @Query("UPDATE Movie m SET m.oscarsCount = 0 WHERE m.genre = :sourceGenre")
    void setZeroOscars(@Param("sourceGenre") MovieGenre sourceGenre);

    @Modifying
    @Query("UPDATE Movie m SET m.oscarsCount = m.oscarsCount + 1 WHERE m.length > :length")
    void addOscarsToLongMovies(@Param("length") int length);


}
