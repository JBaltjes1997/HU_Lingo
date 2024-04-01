package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringTrainerRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GameDTO;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TrainerService {
    private final WordService wordService;
    private final SpringTrainerRepository trainerRepository;

    public TrainerService(WordService wordService, SpringTrainerRepository trainerRepository) {
        this.wordService = wordService;
        this.trainerRepository = trainerRepository;
    }

    public GameDTO startGame(){
        String wordToGuess = this.wordService.provideRandomWord(5);

        Game game = new Game();

        game.startGame(wordToGuess);

        trainerRepository.save(game);

        return new GameDTO(game.getId(),
                game.getScore(),
                game.getRounds(),
                game.getStatus());
    }

    public GameDTO guess(Long id, String guess){
        trainerRepository.findById(id);

        Game game = new Game();

        game.guess(guess);

        trainerRepository.save(game);

        return new GameDTO(game.getId(),
                game.getScore(),
                game.getRounds(),
                game.getStatus());
    }

    public GameDTO playNewRound(Long id){
        String wordToGuess = this.wordService.provideRandomWord(6);

        trainerRepository.findById(id);

        Game game = new Game();

        game.playNewRound(wordToGuess);

        trainerRepository.save(game);

        return new GameDTO(game.getId(),
                game.getScore(),
                game.getRounds(),
                game.getStatus());
    }
}
