package schwatzerschnabel.commands.view;

import schwatzerschnabel.utils.Utils;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;

import java.util.List;

public class ViewRandomWordPairCommand extends ViewCommand {
    public void execute(String authorId, boolean isTable)  {
        List<WordPair> allPairs = Dao.getAllWordPairsByAuthorId(authorId);
        if (allPairs.size()<=0)  {
            event.getChannel().sendMessage("*Sorry, you have no pairs*").queue();
            return;
        }

        WordPair randomPair = allPairs.get(Utils.generateRandom(0, allPairs.size()-1));
        event.getChannel().sendMessage("**" +
                event.getJDA().getUserById(event.getMessage().getAuthor().getId()).getAsTag() +
                "'s** *random word pair*:\n" + wordPairToMessage(randomPair, isTable)).queue();
    }

    @Override
    public void execute()  {
        setFlags();
        execute(event.getMessage().getAuthor().getId(), isTable);
    }
}
