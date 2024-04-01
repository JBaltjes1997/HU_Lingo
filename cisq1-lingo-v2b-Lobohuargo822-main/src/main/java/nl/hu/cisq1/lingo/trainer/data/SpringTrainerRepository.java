package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringTrainerRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(Long id);
}
