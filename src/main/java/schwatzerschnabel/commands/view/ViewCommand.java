package schwatzerschnabel.commands.view;

import com.jakewharton.fliptables.FlipTable;
import schwatzerschnabel.commands.Command;
import schwatzerschnabel.database.entities.WordPair;
import schwatzerschnabel.utils.TextWizard;

import java.util.*;

public abstract class ViewCommand extends Command {
    protected String pos = "";
    protected boolean isTable;
    protected int limit = 0;

    public String wordPairToMessage(String foreignWord, String translation, boolean isTable)  {
        if (isTable)  {
            String[] headers = {"N", "foreign word", "translation"};
            List<List<String>> dataList = new ArrayList<>();
            String[][] data = new String[1][headers.length];
            data[0][0] = String.valueOf(1);
            data[0][1] = foreignWord;
            data[0][2] = translation;

            System.out.println(FlipTable.of(headers, data));

            return "```" + FlipTable.of(headers, data) + "```";
        }
        else  {
            return  "**" + foreignWord +
                    "** = *" + translation + "*";
        }
    }

    public String wordPairToMessage(WordPair wordPair, boolean isTable)  {
      return wordPairToMessage(wordPair.getForeignWord(), wordPair.getTranslation(), isTable);
    }

    public List<String> wordPairsToMessage(List<WordPair> wordPairs, boolean isTable)  {
        List<String> result = new ArrayList<>();

        if (isTable) {
            String[] headers = {"N", "foreign word", "translation"};
            List<List<String>> dataList = new ArrayList<>();
            String[][] data = new String[wordPairs.size()][headers.length];
            int count = 0;
            int start = 0;
            for (WordPair pair : wordPairs) {
                data[count][0] = String.valueOf(count + 1);
                data[count][1] = pair.getForeignWord();
                data[count][2] = pair.getTranslation();
                count++;

                String m = FlipTable.of(headers, Arrays.copyOfRange(data, start, count));
                if (m.length() >= 1500)  {
                    result.add("```" + m + "```");
                    start = count;
                }
            }

            if (start < wordPairs.size())  {
                result.add("```" + FlipTable.of(headers, Arrays.copyOfRange(data, start, count)) + "```");
            }
        //    System.out.println(FlipTable.of(headers, data));

            return result;
        }
        else  {
            String message = "";
            int count = 0;
            for (WordPair pair: wordPairs)  {
                message += (++count) + ". " + "**" + pair.getForeignWord() +
                        "** = *" + pair.getTranslation() + "*\n";
                if (message.length() >= 1500)  {
                    result.add(message);
                    message = "";
                }
             }

             if (!message.equalsIgnoreCase(""))  {
                result.add(message);
             }

        }

        return result;
    }

    public void setFlags()  {
        rawMessage = rawMessage.trim();

        pos = "";
        for (String key: TextWizard.getEnglishPoses().keySet())  {
            if (rawMessage.contains(key))  {
                pos = key;
                rawMessage = rawMessage.replace(key, "").trim();
            }
        }

        isTable = false;
        if (rawMessage.contains("-t"))  {
            isTable = true;
            rawMessage = rawMessage.replace("-t", "").trim();
        }
        else if (rawMessage.contains("--table"))  {
            isTable = true;
            rawMessage = rawMessage.replace("--table", "").trim();
        }

        limit = 0;
        try  {
            limit = Integer.valueOf(rawMessage);
        }
        catch (Exception e)  {
            e.printStackTrace();
        }

    }
}
