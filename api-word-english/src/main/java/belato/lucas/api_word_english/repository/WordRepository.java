package belato.lucas.api_word_english.repository;

import belato.lucas.api_word_english.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {
    List<Word> findAllByCreationTimestampBetween(OffsetDateTime start, OffsetDateTime end);
}
