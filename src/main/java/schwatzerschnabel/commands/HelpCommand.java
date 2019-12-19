package schwatzerschnabel.commands;

public class HelpCommand extends Command {
    @Override
    public void execute()  {
        String helpMessage = "";
        helpMessage += "__You can use these commands to interact with the bot:__\n";
        helpMessage += "*Note: all commands also accept a shortened version that " +
                "renders the first word to its first letter*\n\n";

        helpMessage += "**!view pairs**: *to view all your added words*\n";
        helpMessage += "**!view pairs -t 7**: *to view last week's worth of pairs in a table format*\n";

        helpMessage += "**!pair details \"Der Hund\"**: *to view details of a certain pair, by foreign word*\n";
        helpMessage += "**!add pair**: *to add a new word pair*\n";
        helpMessage += "**!edit pair \"Der Hund\": pos=noun, translation=dog**: *to edit an existing pair*\n";

        helpMessage += "**!guess fw**: *to guess/answer a foreign word*\n";
        helpMessage += "**!guess tr**: *to guess/answer a translation*\n";

        helpMessage += "**!schnabel help**: *to view help message*\n";
        event.getChannel().sendMessage(helpMessage).queue();
    }
}
