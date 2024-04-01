package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.enumerations.Mark;
import nl.hu.cisq1.lingo.trainer.domain.enumerations.Round_States;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.InvalidFeedbackException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.enumerations.Mark.ABSENT;

@Entity
public class Round {
    public static final int max_attempts = 5;

    @Id
    @GeneratedValue
    private Long id;

    private String wordToGuess;

    @Enumerated(EnumType.STRING)
    private Round_States round_status = Round_States.PLAYING;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private final List<Feedback> feedbackHistory = new ArrayList<>();

    public Round_States getRound_status() {
        return round_status;
    }
    public void setRound_status(Round_States round_status) {
        this.round_status = round_status;
    }

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public Round() {
    }

    public String giveFirstHint(String wordToGuess) {
        String firstHint = "";
        if (wordToGuess.length() > 0) {
            firstHint += wordToGuess.charAt(0);
            for (int i = 1; i < wordToGuess.length(); i++) {
                firstHint += ".";
            }
        }
        return firstHint;
    }

    public void guess(String guess) {
        if (guess.length() != this.wordToGuess.length()) {
            throw new InvalidFeedbackException("Attempt is too long");
        } else if(feedbackHistory.size() > 4) {
            this.setRound_status(Round_States.ELIMINATED);
        }
        Feedback feedback = new Feedback(guess, this.generateMarks(guess));
        feedbackHistory.add(feedback); // schrijf nog een test hiervoor + test dit
    }

    public List<Character> showFeedback(){
        return feedbackHistory.get(feedbackHistory.size()-1).giveHint();
    }

    public List<Mark> generateMarks(String guess) {
        List<Mark> marks = new ArrayList<>();

        List<Character> presentList = new ArrayList<>();

        int index = 0;
        for (char character : wordToGuess.toCharArray()) {
            if (guess.charAt(index) == character) {
                marks.add(index, Mark.CORRECT);
                index++;
                continue;
            } else if (!presentList.contains(guess.charAt(index))
                    && wordToGuess.contains(Character.toString(guess.charAt(index)))) {
                marks.add(index, Mark.PRESENT);
                presentList.add(guess.charAt(index));
                index++;
                continue;
            }
            marks.add(index, ABSENT);
            index++;
        }
        return marks;
    }

    public int calculateScore(){
        return 5 * (5-feedbackHistory.size()) + 5;
    }
}
