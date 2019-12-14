package schwatzerschnabel.commands;

import com.jakewharton.fliptables.FlipTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ViewAllWordPairsCommand extends  Command {
    public static void main(String[] args) {
        ViewAllWordPairsCommand command = new ViewAllWordPairsCommand();
        command.getMessage("506795038315905039");
    }

    public String getMessage(String authorId)  {
        List<WordPair> results = Dao.getWordPairsByAuthorId(authorId);

        String[] headers = {"N", "foreign word", "translation"};
        List<List<String>> dataList = new ArrayList<>();
        String[][] data = new String[results.size()][headers.length];
        int count = 0;
        for (WordPair pair: results)  {
            data[count][0] = String.valueOf(count+1);
            data[count][1] = pair.getForeignWord();
            data[count][2] = pair.getTranslation();
            count++;
        }

        System.out.println(FlipTable.of(headers, data));
        return "**" + event.getJDA().getUserById(event.getMessage().getAuthor().getId()).getAsTag() + "'s** *word pairs*:\n" +
                "```" + FlipTable.of(headers, data) + "```";
    }

    @Override
    public void execute()  {
        event.getChannel().sendMessage(getMessage(event.getMessage().getAuthor().getId())).queue();
    }
}
