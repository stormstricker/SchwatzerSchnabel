package schwatzerschnabel.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "word_pairs")
public class WordPair {
    @Id @GeneratedValue
    private int id;

    @Column(unique = true, name = "foreign_word")
    private String foreignWord;
    @Column(name = "translation")
    private String translation;
    @Column(name = "author_id")
    private String authorId;  //discord id
    @Column(name = "addDate")
    private Instant addDate;

    public WordPair()  {}

    public WordPair(String foreignWord, String translation, String authorId, Instant addDate) {
        this.foreignWord = foreignWord;
        this.translation = translation;
        this.authorId = authorId;
        this.addDate = addDate;
    }

    public int getId() {
        return id;
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public String getTranslation() {
        return translation;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Instant getAddDate() {
        return addDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setAddDate(Instant addDate) {
        this.addDate = addDate;
    }
}
