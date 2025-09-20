package belato.lucas.api_word_english.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "words")
public class Word {
    @Id
    private UUID id = UUID.randomUUID();

    private String word;
    private String wordBr;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private OffsetDateTime creationTimestamp;

    public Word(){

    }

    public Word(String word, OffsetDateTime creationTimestamp, String wordBr) {
        this.word = word;
        this.creationTimestamp = creationTimestamp;
        this.wordBr = wordBr;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @JsonIgnore()
    public OffsetDateTime getDateCreate() {
        return creationTimestamp;
    }

    public void setDateCreate(OffsetDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getWordBr() {
        return wordBr;
    }

    public void setWordBr(String wordBr) {
        this.wordBr = wordBr;
    }
}
