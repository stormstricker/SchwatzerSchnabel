package schwatzerschnabel.commands;

public class HelpCommand extends Command {
    @Override
    public void execute()  {
        String helpMessage = "";
        helpMessage += "__You can use these commands to interact with the bot:__\n";

        helpMessage += "**!view pairs**: *to view all your added words*\n";
        helpMessage += "**!add pair**: *to add a new word pair*\n";
        event.getChannel().sendMessage(helpMessage).queue();
    }
}
