/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.shirodo.commands;

import java.util.concurrent.TimeUnit;
import me.shirodo.queue.ShirodoQueue;
import static me.shirodo.queue.ShirodoQueue.prefix_Error;
import static me.shirodo.queue.ShirodoQueue.prefix_Success;
import static me.shirodo.queue.ShirodoQueue.prefix_Warn;
import me.shirodo.queue.data.Lists;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_liste extends Command {
    
    public COMMAND_liste(String liste) {
        super(liste);
    }  
    @Override
    public void execute(CommandSender sender, String[] args) {
        String arg = "normal";
        if (args.length==0) {
            sender.sendMessage(new TextComponent(prefix_Warn+"Komut yanlış girildi. Doğru kullanım : \n/liste "+ChatColor.GREEN+"normal "+ChatColor.DARK_GRAY+"|"+ChatColor.GOLD+" priority"));
        }else{
            arg = args[0];
            if (args.length==0 || !arg.equals("normal") && !arg.equals("priority")) {
                sender.sendMessage(new TextComponent(prefix_Warn+"Komut yanlış girildi. Doğru kullanım : \n/list "+ChatColor.GREEN+"normal "+ChatColor.DARK_GRAY+"|"+ChatColor.GOLD+" priority"));
            }
            else
            {
                if (arg.equalsIgnoreCase("normal")) {
                    if (!Lists.queue.isEmpty()) {
                        String liste = prefix_Success+"Sıra listesindeki üyeler\n";
                        int i=1;
                        for(String name : Lists.queue){
                            liste += ChatColor.DARK_GRAY + "- "+ i + " " + ChatColor.GREEN + name +"\n";
                            i++;
                        }
                        liste += prefix_Success+" Liste sonu.";
                        sender.sendMessage(new TextComponent(liste));
                    }
                    else{
                        sender.sendMessage(new TextComponent(prefix_Error + "Liste boş!"));
                    }
                }else if(arg.equalsIgnoreCase("priority"))
                {
                    if (!Lists.prioQueue.isEmpty()) {
                        String liste = prefix_Success+"Öncelikli sıra listesindeki üyeler\n";
                        int i=1;
                        for(String name : Lists.prioQueue){
                            liste += ChatColor.BOLD + "- "+ i + " " + ChatColor.GOLD + name +"\n";
                            i++;
                        }
                        liste += prefix_Success+" Liste sonu.";
                        sender.sendMessage(new TextComponent(liste));
                    }else{
                    sender.sendMessage(new TextComponent(prefix_Error + "Liste boş!"));
                    }
                }
            }
        }
    }
}
