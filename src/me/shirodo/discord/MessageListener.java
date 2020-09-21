package me.shirodo.discord;

import me.shirodo.queue.data.Lists;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import javax.security.auth.login.LoginException;
import java.awt.*;

public class MessageListener extends ListenerAdapter
{
    public static void botEnable() throws LoginException {
        JDA jda = JDABuilder.createDefault("Your bot token").build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.addEventListener(new MessageListener());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!liste normal"))
        {
            if (Lists.queue.size() !=0){
                String list = "Şu an listede "+Lists.queue.size()+" adet kişi var. Bunlar sırayla : \n\n";
                int i = 0;
                for (String pname : Lists.queue) {
                    list += (i+1)+" - "+pname+"\n";
                    i++;
                }
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Queue List");
                eb.setColor(Color.BLUE);
                eb.addField("Queue", list, false);
                eb.setAuthor("Shirodo", "https://github.com/ShirodoBurak", "https://avatars1.githubusercontent.com/u/64273202?s=460&v=4");
                event.getGuild().getTextChannelById("755073578067820684").sendMessage(eb.build()).queue();
                eb.clear();
            }else{
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Queue List");
                eb.setColor(Color.BLUE);
                eb.addField("Queue", "Sırada kimse yok.", false);
                eb.setAuthor("Shirodo", "https://github.com/ShirodoBurak", "https://avatars1.githubusercontent.com/u/64273202?s=460&v=4");
                event.getGuild().getTextChannelById("755073578067820684").sendMessage(eb.build()).queue();
                eb.clear();
            }

            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            event.getMessage().addReaction("\uD83D\uDC4D").queue();
            channel.sendMessage("Hazırım! Listeyi gönderiyorum.").queue();
        }
        else if (msg.getContentRaw().equals("!liste priority"))
        {
            if (Lists.queue.size() !=0){
                String list = "Şu an öncelikli listede "+Lists.prioQueue.size()+" adet kişi var. Bunlar sırayla : \n\n";
                int i = 0;
                for (String pname : Lists.prioQueue) {
                    list += (i+1)+" - "+pname+"\n";
                    i++;
                }
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Prio Queue List");
                eb.setColor(Color.CYAN);
                eb.addField("Prio Queue", list, false);
                eb.setAuthor("Shirodo", "https://github.com/ShirodoBurak", "https://avatars1.githubusercontent.com/u/64273202?s=460&v=4");
                event.getGuild().getTextChannelById("755073578067820684").sendMessage(eb.build()).queue();
                eb.clear();
            }else{
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Prio Queue List");
                eb.setColor(Color.CYAN);
                eb.addField("Prio Queue", "Öncelikli sırada kimse yok.", false);
                eb.setAuthor("Shirodo", "https://github.com/ShirodoBurak", "https://avatars1.githubusercontent.com/u/64273202?s=460&v=4");
                event.getGuild().getTextChannelById("755073578067820684").sendMessage(eb.build()).queue();
                eb.clear();
            }

            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            event.getMessage().addReaction("\uD83D\uDC4D").queue();
            channel.sendMessage("Hazırım! Listeyi gönderiyorum.").queue();
        }
        else if (msg.getContentRaw().equals("!liste"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            event.getMessage().addReaction("\uD83D\uDC4D").queue();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Hatalı kullanım");
            eb.setColor(Color.RED);
            eb.addField("Doğru kullanım şekilileri", "\n!liste normal - Normal sırayı listeler\n!liste priority - Öncelikli sırayı listeler", false);
            eb.setAuthor("Shirodo", "https://github.com/ShirodoBurak", "https://avatars1.githubusercontent.com/u/64273202?s=460&v=4");
            channel.sendMessage(eb.build()).queue();
            eb.clear();
        }
    }
}
