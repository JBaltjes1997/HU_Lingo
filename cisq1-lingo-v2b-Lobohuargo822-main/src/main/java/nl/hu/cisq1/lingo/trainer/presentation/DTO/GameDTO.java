package nl.hu.cisq1.lingo.trainer.presentation.DTO;

import nl.hu.cisq1.lingo.trainer.domain.enumerations.Game_States;
import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.util.List;

public class GameDTO {
    private Long id;
    private int score;
    private List<Round> rounds;
    private Game_States state;

    public GameDTO(Long id, int score, List<Round> rounds, Game_States state ) {
        this.id = id;
        this.score = score;
        this.rounds = rounds;
        this.state = state;
    }
}
