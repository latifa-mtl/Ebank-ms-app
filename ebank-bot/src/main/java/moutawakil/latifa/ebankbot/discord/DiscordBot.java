package moutawakil.latifa.ebankbot.discord;

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import moutawakil.latifa.ebankbot.agents.EbankAIAgent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.Prompt;

@DiscordController
public class DiscordBot {
//    private EbankAIAgent ebankAIAgent;
//
//    public DiscordBot(EbankAIAgent ebankAIAgent) {
//        this.ebankAIAgent = ebankAIAgent;
//    }
//
//    @DiscordMapping
//    private void perform(MessageReceivedEvent event){
//        if (event.getAuthor().isBot()) return;
//        String query = event.getMessage().getContentRaw();
//        String response = ebankAIAgent.chat(query);
//        event.getChannel().sendMessage(response).queue();
//    }

    private static final Logger log =
            LoggerFactory.getLogger(DiscordBot.class);

    private final EbankAIAgent ebankAIAgent;

    public DiscordBot(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event) {

        System.out.println("MESSAGE RECEIVED: "
                + event.getMessage().getContentRaw());

        if (event.getAuthor().isBot()) {
            System.out.println("Ignored bot message");
            return;
        }

        String query = event.getMessage().getContentRaw();

        try {
            System.out.println("Calling AI agent...");

            String response = ebankAIAgent.chat(new Prompt(query));

            System.out.println("AI RESPONSE: " + response);

            event.getChannel().sendMessage(response).queue(
                    success -> System.out.println("Message sent to Discord"),
                    error -> System.err.println(
                            "Failed to send Discord message: " + error.getMessage()
                    )
            );

        } catch (Exception e) {
            System.err.println("AI ERROR: " + e.getMessage());
            e.printStackTrace();

            event.getChannel().sendMessage(
                    "The AI service is temporarily unavailable. Please try again."
            ).queue(
                    success -> System.out.println("Fallback message sent"),
                    error -> System.err.println(
                            "Failed to send fallback: " + error.getMessage()
                    )
            );
        }
    }


}
