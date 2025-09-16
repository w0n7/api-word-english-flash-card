package belato.lucas.api_word_english.service;

import belato.lucas.api_word_english.dto.CreateWordDtoRequest;
import belato.lucas.api_word_english.dto.UpdateWordDtoRequest;
import belato.lucas.api_word_english.entity.Word;
import belato.lucas.api_word_english.repository.WordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class WordService {
    private WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word saveWord(CreateWordDtoRequest createWordDtoRequest) {
        Word word = new Word(createWordDtoRequest.word(), Instant.now());
        wordRepository.save(word);
        return word;
    }

    public List<Word> getAllWords() {
        return wordRepository.findAll();

    }

    public Word getWordById(String id) {
        Word word = wordRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Word not found"));

        return word;
    }

    public List<Word> getAllWordByDay(Long day) {
        Instant limit = Instant.now().minus(day, ChronoUnit.DAYS);

        return wordRepository.findAllByCreationTimestampAfter(limit);
    }

    public void updateWord(String wordId, UpdateWordDtoRequest wordDto) {
        var id = UUID.fromString(wordId);
        var wordEntity = wordRepository.findById(id);

        if(wordEntity.isPresent()) {
            var word = wordEntity.get();

            if(wordDto.word() != null) {
                word.setWord(wordDto.word());
            }

            wordRepository.save(word);
        }
    }

    public void deleteWord(String wordId) {
        var id = UUID.fromString(wordId);

        var wordExists = wordRepository.existsById(id);

        if(wordExists) {
            wordRepository.deleteById(id);
        }
    }
}
