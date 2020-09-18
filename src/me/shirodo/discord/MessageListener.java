package me.shirodo.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class MessageListener extends ListenerAdapter
{
    public void enable()
            throws LoginException
    {
        JDA jda = JDABuilder.createDefault("tokenpls").build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.addEventListener(new MessageListener());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!liste"))
        {


            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            event.getMessage().addReaction("\uD83D\uDC4D").queue();
            channel.sendMessage("Hazırım! Listeyi gönderiyorum.").queue();
        }
    }
}
