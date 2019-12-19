package schwatzerschnabel.database.entities;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import schwatzerschnabel.utils.TextWizard;

import javax.persistence.*;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

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
    @Column(name = "pos")
    private String pos;

    private String calculatePos()  {
        try {
            SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
            String[] tokens = tokenizer.tokenize(foreignWord);

            InputStream inputStreamPOSTagger =
                    WordPair.class.getResourceAsStream("/opennlp_models/de-pos-maxent.bin");
            POSModel posModel = new POSModel(inputStreamPOSTagger);
            POSTaggerME posTagger = new POSTaggerME(posModel);
            String tags[] = posTagger.tag(tokens);

            Map<String, List<String>> germanPoses = TextWizard.getGermanPoses();
            String lowestPos = "";
            int lowestPosIndex = germanPoses.size();
            int count = 0;
            for (String pos: germanPoses.keySet())  {
                for (String tag: tags)  {
                    if (germanPoses.get(pos).contains(tag))  {
                        if (count < lowestPosIndex)  {
                            lowestPos = pos;
                            lowestPosIndex = count;
                        }
                    }
                }

                count++;
            }

            return lowestPos;
        }
        catch (Exception e)  {
            e.printStackTrace();
            return "";
        }
    }

    public WordPair()  {}

    public WordPair(String foreignWord, String translation, String authorId, Instant addDate) {
        this.foreignWord = foreignWord;
        this.translation = translation;
        this.authorId = authorId;
        this.addDate = addDate;

        pos = calculatePos();
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

    public String getPos() {
        return pos;
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

    public void setPos(String pos) {
        this.pos = pos;
    }
}
