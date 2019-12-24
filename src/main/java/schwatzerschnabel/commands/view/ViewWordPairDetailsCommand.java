package schwatzerschnabel.commands.view;

import com.jakewharton.fliptables.FlipTable;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;

import java.util.Optional;

public class ViewWordPairDetailsCommand extends ViewCommand {
    @Override
    public void execute()  {
        setFlags();
        int index1 = rawMessage.indexOf("\"");
        int index2 = rawMessage.lastIndexOf("\"");

        String foreignWord = rawMessage;

        if (index1 > 0 && index2 > 0)  {
            foreignWord = rawMessage.substring(index1 + 1, index2);
        }

        WordPair pair = Dao.getWordPairByForeignWordAndAuthor(foreignWord, event.getMessage().getAuthor().getId());

        String message = "";
        if (isTable)  {
            String[] headers = {"foreign word", "translation", "pos", "added"};
            String[][] data = new String[1][headers.length];
            data[0][0] = String.valueOf(pair.getForeignWord());
            data[0][1] = pair.getTranslation();
            data[0][2] = Optional.ofNullable(pair.getPos()).orElse("");
            data[0][3] = pair.getAddDate().toString();

            message = "```" + FlipTable.of(headers, data) + "```";
        }
        else  {
            message = "_**Word pair details**_:\n" + wordPairToMessage(pair, false) +
                        "\n__pos__: " + pair.getPos() +
                        "\n__added__: " + pair.getAddDate();
        }

        event.getChannel().sendMessage(message).queue();
    }
}
