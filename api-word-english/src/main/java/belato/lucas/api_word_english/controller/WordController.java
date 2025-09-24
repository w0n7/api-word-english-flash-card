package belato.lucas.api_word_english.controller;


import belato.lucas.api_word_english.dto.CreateWordDtoRequest;
import belato.lucas.api_word_english.dto.UpdateWordDtoRequest;
import belato.lucas.api_word_english.entity.Word;
import belato.lucas.api_word_english.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> getAllWords() {
        try {
            var listWords = wordService.getAllWords();

            return new ResponseEntity<>(listWords, HttpStatus.OK);

        }catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{day}")
    public ResponseEntity<?> getAllWordsByDay(@PathVariable long day) {
        System.out.println(day);
        try {
            var listWordByDay =  wordService.getAllWordByDay(day);
            return new ResponseEntity<>(listWordByDay, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>("Invalid Day", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateWordById(@PathVariable String id, @RequestBody UpdateWordDtoRequest body) {
        try {
            var updateWord = wordService.updateWord(id, body);
            if(updateWord != null) {
               return new ResponseEntity<>(updateWord, HttpStatus.OK);
            }

            return new ResponseEntity<>("Invalid Id", HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
