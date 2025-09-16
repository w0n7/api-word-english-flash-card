package belato.lucas.api_word_english.controller;


import belato.lucas.api_word_english.dto.CreateWordDtoRequest;
import belato.lucas.api_word_english.entity.Word;
import belato.lucas.api_word_english.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/word")
public class WordController {
    private WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<?> createWord(@RequestBody CreateWordDtoRequest body) {
        try {
            Word word = wordService.saveWord(body);

            return new ResponseEntity<>(word, HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>("Invalid parameters", HttpStatus.BAD_REQUEST);
        }
    }
}
