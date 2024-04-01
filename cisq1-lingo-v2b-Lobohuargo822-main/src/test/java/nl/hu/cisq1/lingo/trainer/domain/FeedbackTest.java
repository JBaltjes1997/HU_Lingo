package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.enumerations.Mark;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    @DisplayName("Guess is invalid when one of the Marks is marked as INVALID")
    public void guessIsInvalid(){
        assertThrows(InvalidFeedbackException.class, () -> new Feedback(
                "woorden",
                List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID )
        ));
    }

    @Test
    @DisplayName("Word is guessed when all Marks in list<Marks> are marked as CORRECT")
    public void wordIsGuessed(){
        Feedback feedback = new Feedback(
                "woorden",
                List.of(Mark.CORRECT, Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT)
        );
        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed when not all Marks in list<Marks> are marked as CORRECT")
    public void wordIsNotGuessed(){
        Feedback feedback = new Feedback(
                "woorden",
                List.of(Mark.PRESENT, Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT)
        );
        assertFalse(feedback.isWordGuessed());
    }

    // test naam, stel zelf de vraag "wat test ik?"
    // displaynaam, wanneer is de test voldaan

    @ParameterizedTest
    @DisplayName("Hint is provided when letters are marked as CORRECT otherwise it shows '.'")
    @MethodSource("provideHintExamples")
    public void isHintProvided(String attempt, List<Mark> marks, List<Character> hint){
        Feedback feedback = new Feedback(attempt, marks);
        assertEquals(hint, feedback.giveHint());
    }

    private static Stream<Arguments> provideHintExamples(){
        return Stream.of(
                Arguments.of(
                        "woorden",
                        List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT),
                        List.of('w', '.', '.', 'r', '.', 'e', 'n')
                ),
                Arguments.of(
                        "woorden",
                        List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT),
                        List.of('w', '.', '.', 'r', '.', 'e', 'n')
                ),
                Arguments.of(
                        "woorden",
                        List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT),
                        List.of('w', 'o', 'o', 'r', 'd', 'e', 'n')
                ),
                Arguments.of(
                        "woorden",
                        List.of(Mark.ABSENT, Mark.ABSENT,Mark.ABSENT,Mark.PRESENT,Mark.PRESENT,Mark.PRESENT, Mark.PRESENT),
                        List.of('.', '.', '.', '.', '.', '.', '.')
                )
        );
    }

}