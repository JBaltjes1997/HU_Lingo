package nl.hu.cisq1.lingo.trainer.presentation;


import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GameDTO;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/LingoTrainer")
public class TrainerController {
    private final TrainerService service;

    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @PostMapping
    public GameDTO startGame(){
        return this.service.startGame();
    }

    @PostMapping("/{id}/playNewRound")
    public GameDTO playNewRound(@PathVariable Long id){
        return service.playNewRound(id);
    }

    @GetMapping("/{id}/guess")
    public GameDTO guess(@PathVariable Long id){
        return service.startGame();
    }

}

