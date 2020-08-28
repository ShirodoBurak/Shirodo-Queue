package me.shirodo.queue.data;


import java.util.ListIterator;
import me.shirodo.queue.ShirodoQueue;
import net.md_5.bungee.api.ChatColor;

public class DataInspector {

    public static void toQueue(String player){
        try{
            Lists.queue.add(player);
            
            ShirodoQueue.getMessage(ChatColor.GREEN+"- "+player+ChatColor.GRAY+" isimli üye sıraya eklendi.");

        }
        catch(Exception ex){
            ShirodoQueue.getMessage(ChatColor.DARK_RED+" 29. Satır DataInspector.java\n" + ex);
        }

    }
    public static void toPriority(String player){
        try{
            Lists.prioQueue.add(player);
        ShirodoQueue.getMessage(ChatColor.GRAY+"- "+player+ChatColor.GOLD+" isimli üye öncelikli sıraya eklendi.");
            
        }catch(Exception ex){
            ShirodoQueue.getMessage(ChatColor.DARK_RED+" 39. Satır DataInspector.java\n" + ex);
        } 
    }
    
    public static void removeFromQueue(String player){
        ListIterator<String> Iterator = Lists.queue.listIterator();
        ListIterator<String> prioIterator = Lists.prioQueue.listIterator();
        while(Iterator.hasNext()){
                String playerName = Iterator.next();
                if (playerName.equals(player)) {
                Iterator.remove();         
                ShirodoQueue.getMessage(ChatColor.RED+player+ChatColor.GRAY+" isimli üye sıradan çıkartıldı.");
            }
        }
        while(prioIterator.hasNext()){
                String playerName = prioIterator.next();
                if (playerName.equals(player)) {
                prioIterator.remove();
                ShirodoQueue.getMessage(ChatColor.RED+player+ChatColor.GRAY+" isimli üye sıradan çıkartıldı.");
            }
        }
    }
}
