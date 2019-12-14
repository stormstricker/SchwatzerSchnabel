package schwatzerschnabel.commands;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import schwatzerschnabel.database.entities.WordPair;

import java.time.Instant;

public class AddWordPairCommand extends  Command  {
    @Override
    public void execute()  {
        rawMessage = rawMessage.trim();
        int delimeterIndex1 = rawMessage.indexOf(":");
        int delimeterIndex2 = rawMessage.indexOf("=");
        int delimeterIndex3 = rawMessage.indexOf("-");

        int finalDelimeterIndex = (delimeterIndex1 > 0) ? delimeterIndex1 :
                (delimeterIndex2 > 0) ? delimeterIndex2 : delimeterIndex3;
        if (finalDelimeterIndex < 0)  {
            return;
        }

        String foreignWord = rawMessage.substring(0, finalDelimeterIndex).trim();
        String translation = rawMessage.substring(finalDelimeterIndex + 1).trim();
        WordPair wordPair = new WordPair(foreignWord, translation,
                event.getMessage().getAuthor().getId(), Instant.now());
        execute(wordPair);
    }

    public void execute(WordPair wordPair)  {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(wordPair);
        session.getTransaction().commit();
        session.close();
        event.getChannel().sendMessage("A **" + wordPair.getForeignWord() +
                "** = *" + wordPair.getTranslation() + "* has been created!").queue();
    }

}
