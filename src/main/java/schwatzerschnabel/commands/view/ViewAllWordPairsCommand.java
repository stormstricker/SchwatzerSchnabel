package schwatzerschnabel.commands.view;

import com.jakewharton.fliptables.FlipTable;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;

import java.util.*;

public class ViewAllWordPairsCommand extends ViewCommand {
    public static void main(String[] args) {
        ViewAllWordPairsCommand command = new ViewAllWordPairsCommand();
        command.execute("506795038315905039", 0, "", false);
    }

    public void execute(String authorId, int limit, String pos, boolean isTable) {
        List<WordPair> results = Dao.getWordPairsByAuthorId(authorId, limit, pos);

        List<String> messages = wordPairsToMessage(results, isTable);
        if (messages.size() > 0)  {
            event.getChannel().sendMessage("**" +
                    event.getJDA().getUserById(
                            event.getMessage().getAuthor().getId()).getAsTag() +
                    "'s** *word pairs*:\n").queue();
        }
        for (String message: messages) {
            event.getChannel().sendMessage(message).queue();
        }
    }

    @Override
    public void execute()  {
        setFlags();
        execute(event.getMessage().getAuthor().getId(), limit, pos, isTable);
    }
}
