package schwatzerschnabel.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {
    protected MessageReceivedEvent event;
    protected String rawMessage;

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public void setEvent(MessageReceivedEvent event) {
        this.event = event;
    }

    public abstract void execute();
}
