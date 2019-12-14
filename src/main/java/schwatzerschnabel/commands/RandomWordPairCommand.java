package schwatzerschnabel.commands;

import com.jakewharton.fliptables.FlipTable;
import schwatzerschnabel.Utils;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;

import java.util.ArrayList;
import java.util.List;

public class RandomWordPairCommand extends Command {
    public void execute(String authorId)  {
        List<WordPair> allPairs = Dao.getWordPairsByAuthorId(authorId);
        if (allPairs.size()<=0)  {
            event.getChannel().sendMessage("*Sorry, you have no pairs*").queue();
            return;
        }

        WordPair randomPair = allPairs.get(Utils.generateRandom(0, allPairs.size()-1));
        String[] headers = {"N", "foreign word", "translation"};

        List<List<String>> dataList = new ArrayList<>();
        String[][] data = new String[1][headers.length];
        data[0][0] = String.valueOf(1);
        data[0][1] = randomPair.getForeignWord();
        data[0][2] = randomPair.getTranslation();

        System.out.println(FlipTable.of(headers, data));
        event.getChannel().sendMessage("**" +
                event.getJDA().getUserById(event.getMessage().getAuthor().getId()).getAsTag() +
                "'s** *random word pair*:\n" + "```" + FlipTable.of(headers, data) + "```").queue();
    }

    @Override
    public void execute()  {
        execute(event.getMessage().getAuthor().getId());
    }
}
