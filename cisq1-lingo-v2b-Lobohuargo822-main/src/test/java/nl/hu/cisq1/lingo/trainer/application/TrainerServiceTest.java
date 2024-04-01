package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringTrainerRepository;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class TrainerServiceTest {
    WordService wordService = mock(WordService.class);
    SpringTrainerRepository trainerRepository = mock(SpringTrainerRepository.class);
    TrainerService service = new TrainerService(wordService, trainerRepository);

    @Test
    @DisplayName("When a player starts a game then a new round will be made")
    void StartGame(){

    }

}