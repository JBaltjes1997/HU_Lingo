package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.enumerations.Mark;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.InvalidFeedbackException;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Feedback {

    @Id
    @GeneratedValue
    private Long id;

    private String guess;
    @ElementCollection
    private List<Mark> marks;
    @ElementCollection
    private List<Character> hint;

    public Feedback(String guess, List<Mark> marks) throws InvalidFeedbackException {
        if (marks.contains(Mark.INVALID)) {
            throw new InvalidFeedbackException("Attempt is invalid");
        }
        this.guess = guess;
        this.marks = marks;
        this.hint = new ArrayList<>();
    }

    public Feedback() {
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark.equals(Mark.CORRECT));
    }

    public List<Character> giveHint() {
        int index = 0;
        for (char character : guess.toCharArray()) {
            if (marks.get(index).equals(Mark.CORRECT)) {
                hint.add(index, character);
            } else {
                hint.add(index, '.');
            }
            index++;
        }
        return hint;
    }

}
