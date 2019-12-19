package schwatzerschnabel.commands.guess;

import schwatzerschnabel.commands.Command;
import schwatzerschnabel.commands.view.ViewCommand;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;
import schwatzerschnabel.utils.Utils;

import java.util.List;

public class GuessTranslationCommand extends GuessCommand {
    @Override
    public void execute()  {
        super.execute(event.getMessage().getAuthor().getId());
    }

    @Override
    public void sendRiddle()  {
        String message = "*What's the translation?*\n";
        message += wordPairToMessage(currentPair.getForeignWord(), "?",isTable);
        event.getChannel().sendMessage(message).queue();
    }

    @Override
    public boolean checkAnswer()  {
        return currentPair.getTranslation().contains(rawMessage);
    }

    @Override
    public String getAnswer()  {
        return currentPair.getTranslation();
    }
}
