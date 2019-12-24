package schwatzerschnabel.commands.edit;

import schwatzerschnabel.commands.Command;
import schwatzerschnabel.database.Dao;
import schwatzerschnabel.database.entities.WordPair;
import schwatzerschnabel.utils.Utils;

//!edit pair \"Der Hund\": pos=noun, translation=dog
public class EditWordPairCommand extends Command {
    @Override
    public void execute()  {
        rawMessage = rawMessage.trim();
        int index1 = rawMessage.indexOf("\"");
        int index2 = rawMessage.lastIndexOf("\"");
        int index3 = rawMessage.indexOf(":");

        String foreignWord = rawMessage;

        if (index1 > 0 && index2 > 0)  {
            foreignWord = rawMessage.substring(index1 + 1, index2);
        }
        else  {
            foreignWord = rawMessage.substring(0, index3);
        }

        WordPair pair = Dao.getWordPairByForeignWordAndAuthor(foreignWord, event.getMessage().getAuthor().getId());
        rawMessage = rawMessage.substring(index3 +1).trim();
        int posIndex = rawMessage.indexOf("pos");
        int translationIndex = rawMessage.indexOf("translation");

        boolean isChanged = false;
        if (posIndex >= 0) {
            String posString;
            if (translationIndex >= 0 && translationIndex > posIndex) {
                posString = rawMessage.substring(posIndex, translationIndex).trim();
            } else {
                posString = rawMessage.substring(posIndex);
            }
            posString = Utils.trimCommas(posString);
            String posValue = posString.substring(posString.indexOf("=")+1).trim();

            if (!posValue.equalsIgnoreCase(""))  {
                isChanged = true;
                pair.setPos(posValue);
            }
        }

        if (translationIndex >= 0) {
            String translationString;
            if (posIndex >= 0 && posIndex > translationIndex) {
                translationString = rawMessage.substring(translationIndex, posIndex).trim();
            } else {
                translationString = rawMessage.substring(translationIndex).trim();
            }
            translationString = Utils.trimCommas(translationString);
            String translationValue = translationString.substring(
                    translationString.indexOf("=") + 1).trim();
            if (!translationValue.equalsIgnoreCase(""))  {
                isChanged = true;
                pair.setTranslation(translationValue);
            }
        }

        if (isChanged)  {
            Dao.updateWordPair(pair);
            event.getChannel().sendMessage("*Successful edit!*").queue();
        }
        else  {
            event.getChannel().sendMessage("*Didn't edit!*").queue();
        }
    }
}
