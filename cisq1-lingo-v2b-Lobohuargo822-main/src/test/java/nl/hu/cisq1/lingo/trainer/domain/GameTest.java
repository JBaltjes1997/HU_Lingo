package nl.hu.cisq1.lingo.trainer.domain;

import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import nl.hu.cisq1.lingo.trainer.domain.enumerations.Game_States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    @DisplayName("when a new Game is started then a new round will be made and Game_status will be 'playing'")
    void isGameStarted(){
        Game game = new Game();
        game.startGame("banaan");
        assertEquals(Game_States.PLAYING, game.getStatus());
        assertEquals(1 , game.getRounds().size());
    }

    @Test
    @DisplayName("When a game is playing then the game state will be 'PLAYING'")
    void isGameStateCorrect(){
        Game game = new Game();
        assertEquals(Game_States.WAITING, game.getStatus());
        game.startGame("planten");
        assertEquals(Game_States.PLAYING, game.getStatus());
    }

    @Test
    @DisplayName("When the player wants it then a new game will be started")
    void newGameStarted(){
        Game game = new Game();
        game.startGame("pennen");
        game.playNewRound("pillen");
        assertEquals(2 , game.getRounds().size());
    }

    @Test
    @DisplayName("When a game is played then only the last round will be called upon")
    void testCurrentRound(){
        Game game = new Game();
        assertNull(game.getCurrentRound());
        game.startGame("pauze");
        List<Round> alleRounds = game.getRounds();
        int laatsteRounde = alleRounds.size()-1;
        assertEquals(alleRounds.get(laatsteRounde), game.getCurrentRound());
    }

    @Test
    @DisplayName("When a player wins the game then a certain amount of points will be awarded")
    void testScore(){
        Game game = new Game();
        game.startGame("publiek");
        game.guess("publiek");
        assertEquals(25, game.getScore());


    }
}