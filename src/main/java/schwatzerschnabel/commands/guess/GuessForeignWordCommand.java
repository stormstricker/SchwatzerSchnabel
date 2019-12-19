package schwatzerschnabel.commands.guess;

import schwatzerschnabel.commands.Command;
import schwatzerschnabel.commands.view.ViewCommand;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;
import schwatzerschnabel.utils.Utils;

import java.util.List;

public class GuessForeignWordCommand extends GuessCommand {
    @Override
    public void execute()  {
        super.execute(event.getMessage().getAuthor().getId());
    }

    @Override
    public void sendRiddle()  {
        String message = "*What's the foreign word?*\n";
            message += wordPairToMessage("?", currentPair.getTranslation(),isTable);
        event.getChannel().sendMessage(message).queue();
    }

    @Override
    public boolean checkAnswer()  {
        return currentPair.getForeignWord().contains(rawMessage);
    }

    @Override
    public String getAnswer()  {
        return currentPair.getForeignWord();
    }
}
