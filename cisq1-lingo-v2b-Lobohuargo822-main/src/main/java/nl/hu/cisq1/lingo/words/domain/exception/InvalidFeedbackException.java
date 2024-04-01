package nl.hu.cisq1.lingo.words.domain.exception;

public class InvalidFeedbackException extends RuntimeException{
    public InvalidFeedbackException(String woord) {super("Dit is fout: " + woord);}
}
