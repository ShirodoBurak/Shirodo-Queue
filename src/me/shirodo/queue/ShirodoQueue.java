package me.shirodo.queue;


import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import me.shirodo.commands.COMMAND_liste;
import me.shirodo.discord.MessageListener;
import me.shirodo.queue.data.DataInspector;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import me.shirodo.queue.data.Lists;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.security.auth.login.LoginException;

import static me.shirodo.discord.MessageListener.botEnable;

public class ShirodoQueue extends Plugin implements Listener{

    private int advert = 0;
    public static String tellMsg;
    public static String prefix_Info = ChatColor.DARK_GRAY+""+ChatColor.BOLD+"["+ChatColor.RESET+"Shirodo"+ChatColor.DARK_GRAY+ChatColor.BOLD+"]" + ChatColor.BLUE + " ";
    public static String prefix_Warn = ChatColor.DARK_GRAY+""+ChatColor.BOLD+"["+ChatColor.RESET+"Shirodo"+ChatColor.DARK_GRAY+ChatColor.BOLD+"]" + ChatColor.YELLOW + " ";
    public static String prefix_Danger = ChatColor.DARK_GRAY+""+ChatColor.BOLD+"["+ChatColor.RESET+"Shirodo"+ChatColor.DARK_GRAY+ChatColor.BOLD+"]" + ChatColor.RED + " ";
    public static String prefix_Error = ChatColor.DARK_GRAY+""+ChatColor.BOLD+"["+ChatColor.RESET+"Shirodo"+ChatColor.DARK_GRAY+ChatColor.BOLD+"]" + ChatColor.DARK_RED + " ";
    public static String prefix_Success = ChatColor.DARK_GRAY+""+ChatColor.BOLD+"["+ChatColor.RESET+"Shirodo"+ChatColor.DARK_GRAY+ChatColor.BOLD+"]" + ChatColor.GREEN + " ";

    @Override
    public void onEnable(){
        try {
            botEnable();
        } catch (LoginException e) {
            getLogger().log(Level.SEVERE, e.toString(), prefix_Danger);
        }
        RegisterCommands();
        getProxy().getPluginManager().registerListener(this, this);
        ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
            @Override
            public void run() {
                int i = 0;
                if (!Lists.queue.isEmpty()) {
                    for (String pname : Lists.queue) {
                        i++;
                        getProxy().getPlayer(pname).sendMessage(new TextComponent(prefix_Info+ChatColor.GRAY+"Sıra bilgisi : "+ChatColor.GRAY+"["+i+"/"+Lists.queue.size()+"]" ));
                        if (advert == 6) {
                            getProxy().getPlayer(pname).sendMessage(new TextComponent(prefix_Warn+"Sunucumuza 10TL ve üzeri bağış yaparak öncelikli sıraya girebilir ve çok daha hızlı bir şekilde oyuna katılabilirsin! (Her ay yenilenmelidir)"));
                            advert = 0;
                        }
                        advert++;
                    }
                }
                else{
                    getLogger().log(Level.INFO, "S\u0131ra tamamen bo\u015f. Mesaj g\u00f6nderilecek \u00fcye yok.", prefix_Info);
                }
                int j = 0;
                if (!Lists.prioQueue.isEmpty()) {
                    for (String pname : Lists.prioQueue) {
                        j++;
                        getProxy().getPlayer(pname).sendMessage(new TextComponent(prefix_Info+ChatColor.RESET+"Öncelikli sıra bilgisi : "+ChatColor.GOLD+"["+j+"/"+Lists.prioQueue.size()+"]" ));
                    }
                }
                else{
                    getLogger().log(Level.INFO, "\u00d6ncelikli s\u0131ra tamamen bo\u015f. Mesaj g\u00f6nderilecek \u00fcye yok.", prefix_Info);
                }
                sendPlayerToGame();
            }
        }, 20, 20, TimeUnit.SECONDS);
    }
    @Override
    public void onDisable(){

    }
    /**
     *
     */
    public void sendPlayerToGame(){
        ProxiedPlayer prioPlayer = null;
        ProxiedPlayer player = null;
        if (!Lists.queue.isEmpty()) {
            String pl = Lists.queue.get(0);
            player = getProxy().getPlayer(pl);
            DataInspector.removeFromQueue(pl);
        }
        if (!Lists.prioQueue.isEmpty()) {
            String prpl = Lists.prioQueue.get(0);
            prioPlayer = getProxy().getPlayer(prpl);
            DataInspector.removeFromQueue(prpl);
        }

        ServerInfo anasunucu = ProxyServer.getInstance().getServerInfo("actualserver");
        if (prioPlayer !=null) {
            prioPlayer.connect(anasunucu);
            prioPlayer.sendMessage(new TextComponent(ChatColor.GOLD +""+ ChatColor.BOLD+"Sunucuya aktarılıyorsun!"));
        }
        if (player!=null) {
            player.connect(anasunucu);
            player.sendMessage(new TextComponent(ChatColor.BOLD+"Sunucuya aktarılıyorsun!"));
        }

    }
    public static void getMessage(String message){
        tellMsg = message;
    }
    public void tellConsole(){
        if (!tellMsg.isEmpty()) {
            getLogger().info(tellMsg);
            getMessage(null);
        }
    }
    public void tellLogger(String msg){
        getLogger().info(msg);
    }

    /*+Events+*/
    @EventHandler
    public void onPostLogin(PostLoginEvent joined) {
        try{
            if (joined.getPlayer().hasPermission("shirodo.queue.priority")) {

                DataInspector.toPriority(joined.getPlayer().getName());
            }
            else{
                DataInspector.toQueue(joined.getPlayer().getName());
            }
        }
        catch(NullPointerException ex){
            tellLogger("HATA, NULLPOINTEREXCEPTION : " + joined);
        }
    }

    @EventHandler
    public static void onPlayerDisconnect(PlayerDisconnectEvent quit){
        DataInspector.removeFromQueue(quit.getPlayer().getName());
    }
    /*-Events-*/

    private void RegisterCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_liste("liste"));
        getLogger().info(prefix_Info + "Commands registered!");
    }
}