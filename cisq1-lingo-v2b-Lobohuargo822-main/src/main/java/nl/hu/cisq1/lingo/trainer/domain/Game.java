package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.enumerations.Game_States;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    private int score = 0;

    @Enumerated(EnumType.STRING)
    private Game_States status = Game_States.WAITING;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private final List<Round> rounds = new ArrayList<>();

    public Game(int score, Game_States status) {
        this.score = score;
        this.status = status;
    }

    public Game() {
    }
    public Long getId() {
        return id;
    }
//    public int getScore() {
//        Round round = this.getCurrentRound();
//        return round.calculateScore();
//    }
    public Game_States getStatus() {
        return status;
    }
    public void setStatus(Game_States status) {
        this.status = status;
    }

    public List<Round> getRounds() {
        return rounds;
    }


    public void startGame(String wordToGuess){
        this.rounds.add(new Round(wordToGuess));
        this.status = Game_States.PLAYING;
    }

    public void guess(String guess){
        Round round = this.getCurrentRound();
        round.guess(guess);
    }

    public void playNewRound(String wordToGuess){
        this.startGame(wordToGuess);
    }

    public Round getCurrentRound(){
        if(rounds.size() == 0){
            return null;
        }
        return rounds.get(rounds.size()-1);
    }

    public int getScore(){
        Round round = this.getCurrentRound();
        return round.calculateScore();
    }


}
