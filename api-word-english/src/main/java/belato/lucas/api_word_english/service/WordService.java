package belato.lucas.api_word_english.service;

import belato.lucas.api_word_english.dto.CreateWordDtoRequest;
import belato.lucas.api_word_english.dto.UpdateWordDtoRequest;
import belato.lucas.api_word_english.entity.Word;
import belato.lucas.api_word_english.repository.WordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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
        Word word = new Word(createWordDtoRequest.word(), OffsetDateTime.now(), createWordDtoRequest.wordBr());
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

    public List<Word> getAllWordByDay(Long daysAgo) {
        ZoneId zone = ZoneId.of("America/Sao_Paulo");

        // Pega a data de "hoje - daysAgo" (ex: hoje - 3 dias = 17/09/2025)
        LocalDate targetDate = LocalDate.now(zone).minusDays(daysAgo);

        // Início do dia → 00:00:00
        OffsetDateTime startOfDay = targetDate.atStartOfDay(zone).toOffsetDateTime();

        // Fim do dia → próximo dia 00:00:00 (exclusivo)
        OffsetDateTime endOfDay = targetDate.plusDays(1).atStartOfDay(zone).toOffsetDateTime();

        System.out.println("Início: " + startOfDay + " | Fim: " + endOfDay);

        return wordRepository.findAllByCreationTimestampBetween(startOfDay, endOfDay);
    }

    public Word updateWord(String wordId, UpdateWordDtoRequest wordDto) {
        var id = UUID.fromString(wordId);
        var wordEntity = wordRepository.findById(id);

        if(wordEntity.isEmpty()) {
            return null;
        }

        var word =  wordEntity.get();
        if(wordDto.word() != null) {
            word.setWord(wordDto.word());
        }

        if(wordDto.wordBr() != null) {
            word.setWordBr(wordDto.wordBr());
        }

        return  word;
    }

    public void deleteWord(String wordId) {
        var id = UUID.fromString(wordId);

        var wordExists = wordRepository.existsById(id);

        if(wordExists) {
            wordRepository.deleteById(id);
        }
    }
}
