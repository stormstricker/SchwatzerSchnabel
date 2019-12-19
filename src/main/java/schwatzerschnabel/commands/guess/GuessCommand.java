package schwatzerschnabel.commands.guess;

import schwatzerschnabel.commands.view.ViewCommand;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;
import schwatzerschnabel.utils.Utils;

import java.util.List;

public abstract class GuessCommand extends ViewCommand {
    protected enum State  {IDLE, GUESSING};
    protected enum Kind {TRANSLATION, FOREIGN_WORD};

    protected State state = State.IDLE;
    protected WordPair currentPair;

    public void execute(String authorId) {
        setFlags();

        if (state == State.IDLE) {
            List<WordPair> allPairs = Dao.getAllWordPairsByAuthorId(authorId);
            if (allPairs.size() <= 0) {
                event.getChannel().sendMessage("*Sorry, you have no pairs*").queue();
                return;
            }
            currentPair = allPairs.get(Utils.generateRandom(0, allPairs.size() - 1));
            sendRiddle();
            state = State.GUESSING;
        }
        else if (currentPair != null)  {
            if (!checkAnswer())  {
                event.getChannel().sendMessage("*Sorry, wrong answer!*\n" + wordPairToMessage(currentPair, isTable)).queue();
            }
            else  {
                event.getChannel().sendMessage("*Success! Correct answer!*\n" + wordPairToMessage(currentPair, isTable)).queue();
            }

            state = State.IDLE;
        }
    }

    public abstract void sendRiddle();
    public abstract boolean checkAnswer();
    public abstract String getAnswer();
}
