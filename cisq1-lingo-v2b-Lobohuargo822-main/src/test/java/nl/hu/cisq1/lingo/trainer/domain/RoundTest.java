package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.enumerations.Mark;
import nl.hu.cisq1.lingo.trainer.domain.enumerations.Round_States;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    @DisplayName("Only the first letter may be shown")
    public void testFirstLetterOnly(){
        Round round = new Round();
        assertEquals("w....", round.giveFirstHint("woord"));
        assertEquals("w.....", round.giveFirstHint("wereld"));
        assertEquals("k......", round.giveFirstHint("kleuren"));
        assertNotEquals(".....", round.giveFirstHint("woord"));
    }

    @Test
    @DisplayName("A guess is invalid if its length is longer than the wordToGuess")
    public void guessIsInvalid(){
        Round round = new Round("worden");
        assertThrows( InvalidFeedbackException.class, () -> round.guess("woorden"));
    }

    @Test
    @DisplayName("When 5 incorrect guesses are made then the game is over")
    public void tooManyAttempts(){
        Round round = new Round("bergen");
        round.guess("bommen");
        round.guess("bommen");
        round.guess("bommen");
        round.guess("bommen");
        round.guess("bommen");
        round.guess("bommen");
        assertEquals(Round_States.ELIMINATED, round.getRound_status());
    }

    @Test
    @DisplayName("After a guess a list of characters will be shown")
    public void testCorrectFeedback(){
        Round round = new Round("gitaar");
        round.guess("gieter");
        assertEquals(List.of('g', 'i', '.', '.', '.', 'r'), round.showFeedback());
        assertNotEquals(List.of('g', '.', '.', '.', '.', '.'), round.showFeedback());
    }

    @ParameterizedTest
    @DisplayName("When a wordToGuess and a Guess is present then a list of marks will be returned ")
    @MethodSource("provideFeedbackExamples")
    public void marksIsGenerated(String guess, String wordToGuess, List<Mark> expectedMarks){
        Round round = new Round(wordToGuess);
        assertEquals(expectedMarks, round.generateMarks(guess));
    }

    private static Stream<Arguments> provideFeedbackExamples(){
        return Stream.of(
                Arguments.of("droom","dozen",
                        List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT,Mark.ABSENT,Mark.ABSENT)),
                Arguments.of("azbde","abcde",
                        List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT,Mark.CORRECT,Mark.CORRECT)),
                Arguments.of("gitaar","graten",
                        List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT, Mark.PRESENT, Mark.ABSENT, Mark.PRESENT)),
                Arguments.of("bloemen","blaffen",
                        List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT))
        );
    }

    @Test
    @DisplayName("when a word is guessed a score will be given")
    public void correctScore(){
        Round round = new Round("druif");
        round.guess("druip");
        round.guess("druif");
        assertEquals(20, round.calculateScore());
    }
}