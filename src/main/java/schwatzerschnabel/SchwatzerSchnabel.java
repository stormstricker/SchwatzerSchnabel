package schwatzerschnabel;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import schwatzerschnabel.commands.*;
import schwatzerschnabel.commands.edit.AddWordPairCommand;
import schwatzerschnabel.commands.edit.EditWordPairCommand;
import schwatzerschnabel.commands.guess.GuessForeignWordCommand;
import schwatzerschnabel.commands.guess.GuessTranslationCommand;
import schwatzerschnabel.commands.view.ViewRandomWordPairCommand;
import schwatzerschnabel.commands.view.ViewAllWordPairsCommand;
import schwatzerschnabel.commands.view.ViewWordPairDetailsCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SchwatzerSchnabel extends ListenerAdapter {
   // private final SharedData sharedData = new SharedData();
    private String wordOfTheDayChannelId = "612832143097200651";


    private Map<String, Command> commands = new HashMap<>();

    {
        commands.put("!v pairs", new ViewAllWordPairsCommand());
        commands.put("!view pairs", new ViewAllWordPairsCommand());

        commands.put("!a pair", new AddWordPairCommand());
        commands.put("!add pair", new AddWordPairCommand());

        commands.put("!r pair", new ViewRandomWordPairCommand());
        commands.put("!random pair", new ViewRandomWordPairCommand());

        commands.put("!p details", new ViewWordPairDetailsCommand());
        commands.put("!pair details", new ViewWordPairDetailsCommand());

        commands.put("!e pair", new EditWordPairCommand());
        commands.put("!edit pair", new EditWordPairCommand());

        GuessForeignWordCommand guessFwCommand = new GuessForeignWordCommand();
        GuessTranslationCommand guessTrCommand = new GuessTranslationCommand();

        commands.put("!g fw", guessFwCommand);
        commands.put("!g tr", guessTrCommand);

        //commands.put("!g", )

        commands.put("!guess fw", guessFwCommand);
        commands.put("!guess tr", guessTrCommand);

        commands.put("!sch help", new HelpCommand());
        commands.put("!schnabel help", new HelpCommand());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Inside SchwatzerSchnabel's main");

        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        SchwatzerSchnabel.class.getResourceAsStream("/tokens/SchwatzerSchnabel.token")));
        String token = br.readLine();
        br.close();

        System.out.println("token: " + token);

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        JDA jda = builder.build();
        jda.awaitReady();

        jda.addEventListener(new SchwatzerSchnabel());
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String[] words = message.split(" ");
        System.out.println("inside SchwatzerSchnabel");

        if (event.getAuthor().isBot())  {
                return;
        }

        if (!message.startsWith("!") && !event.getChannel().getId().equals(wordOfTheDayChannelId)) {
            return;
        }

        Command command = null;
        for (Map.Entry<String, Command> e : commands.entrySet()) {
            if (message.startsWith(e.getKey())) {
                command = commands.get(e.getKey());
                command.setRawMessage(message.replace(e.getKey(), ""));
                break;  //pick the first fitting
            }
        }

        if (command==null && ((message.contains("=") ||
                message.contains(":") || message.contains("-")) &&
                words.length <= 10) && event.getChannel().getId().equals(wordOfTheDayChannelId))  {
            command = new AddWordPairCommand();
            command.setRawMessage(message);
        }

        if (command!=null)  {
            //command.setData(sharedData);
            command.setEvent(event);
            command.execute();
        }
    }
}

