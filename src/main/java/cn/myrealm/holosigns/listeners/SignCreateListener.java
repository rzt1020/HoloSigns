package cn.myrealm.holosigns.listeners;

import cn.myrealm.holosigns.HoloSigns;
import cn.myrealm.holosigns.managers.LanguageManager;
import cn.myrealm.holosigns.managers.SignManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @program: HoloSigns
 * @description: For players to enter sign content
 * @author: rzt1020
 * @create: 2022/09/29
 **/
public class SignCreateListener {
    
    /**
     * @Description: Register a listener that listens to the content of the player's input sign
     * @Param: [player]
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/29
    **/
    public static void registerListener(Player player) {
        int timeLimit = HoloSigns.instance.getConfig().getInt("time_limit",-1);
        Listener listener = new Listener() {
            /**
             * @Description: Create Sign when player chat
             * @Param: [event]
             * @return: void
             * @Author: rzt1020
             * @Date: 2022/9/29
            **/
            @EventHandler
            public void onChat(AsyncPlayerChatEvent event) {
                if (player.equals(event.getPlayer())) {
                    String text = event.getMessage();
                    if (text.equals("cancel")) {
                        HandlerList.unregisterAll(this);
                        player.sendMessage(LanguageManager.instance.getText("create-cancel"));
                    } else {
                        text = HoloSigns.parseColor(text);
                        SignManager.instance.create(text,player.getLocation(),player);
                    }
                }
            }
            
            /**
             * @Description: Cancel handles when the player changes world
             * @Param: [event]
             * @return: void
             * @Author: rzt1020
             * @Date: 2022/9/29
            **/
            @EventHandler
            public void onChangeWorld(PlayerChangedWorldEvent event) {
                if (player.equals(event.getPlayer())) {
                    HandlerList.unregisterAll(this);
                    player.sendMessage(LanguageManager.instance.getText("create-cancel"));
                }
            }

            /**
             * @Description: Cancel handles when the player quit
             * @Param: [event]
             * @return: void
             * @Author: rzt1020
             * @Date: 2022/9/29
            **/
            @EventHandler
            public void onQuit(PlayerQuitEvent event) {
                if (player.equals(event.getPlayer())) {
                    HandlerList.unregisterAll(this);
                    player.sendMessage(LanguageManager.instance.getText("create-cancel"));
                }
            }

            /**
             * @Description: Cancel handles when the player death
             * @Param: [event]
             * @return: void
             * @Author: rzt1020
             * @Date: 2022/9/29
            **/
            @EventHandler
            public void onDeath(PlayerDeathEvent event) {
                if (player.equals(event.getEntity())) {
                    HandlerList.unregisterAll(this);
                    player.sendMessage(LanguageManager.instance.getText("create-cancel"));
                }
            }

            /**
             * @Description: Cancel handles when the player move
             * @Param: [event]
             * @return: void
             * @Author: rzt1020
             * @Date: 2022/9/29
            **/
            @EventHandler
            public void onMove(PlayerMoveEvent event){
                if (player.equals(event.getPlayer())) {
                    Location from = event.getFrom(),
                             to = event.getTo();
                    assert to != null;
                    if (!from.getBlock().equals(to.getBlock())) {
                        HandlerList.unregisterAll(this);
                        player.sendMessage(LanguageManager.instance.getText("create-cancel"));
                    }
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(listener, HoloSigns.instance);

        /*
         @Description: Cancel handles when the time limit is exceeded.
         @Author: rzt1020
         @Date: 2022/9/29
        */
        if (timeLimit != -1) {
            Bukkit.getScheduler().runTaskLater(HoloSigns.instance, ()->{
                HandlerList.unregisterAll(listener);
                player.sendMessage(LanguageManager.instance.getText("time-run-out"));
                player.sendMessage(LanguageManager.instance.getText("create-cancel"));
            }, timeLimit * 20L);
        }
    }

}
