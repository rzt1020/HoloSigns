package cn.myrealm.holosigns.managers;

import cn.myrealm.holosigns.HoloSigns;
import cn.myrealm.holosigns.util.Sign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * @program: HoloSigns
 * @description: Managing Signs
 * @author: rzt1020
 * @create: 2022/09/28
 **/
public class SignManager extends Manager{
    // vars
    public static SignManager instance; // manager instance
    private static Map<World,List<Sign>> signs; // Use different worlds to distinguish Signs
    private static BukkitTask loopTask;
    /**
     * @Description: Constructor
     * @Param: []
     * @return: 
     * @Author: rzt1020
     * @Date: 2022/9/29
    **/
    public SignManager() {
        if (Objects.nonNull(loopTask)) {
            loopTask.cancel();
        }
        instance = this;
        signs =new HashMap<>();
        loopTask = Bukkit.getScheduler().runTaskTimer(HoloSigns.instance, this::doCheckLoop,0L,10L);
    }
    
    /**
     * @Description: Create a new Sign and save it
     * @Param: [msg, loc, player]
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/29
    **/
    public void create(String msg, Location loc, Player player) {
        Sign sign = new Sign(msg, loc, player);
        if (signs.containsKey(loc.getWorld())) {
            signs.get(loc.getWorld()).add(sign);
        } else {
            List<Sign> sign_list = new ArrayList<>();
            sign_list.add(sign);
            signs.put(loc.getWorld(), sign_list);
        }
    }
    
    /**
     * @Description: Detects if there is a visible Sign near each player
     * @Param: []
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/30
    **/
    public void doCheckLoop() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();
            Bukkit.getScheduler().runTaskAsynchronously(HoloSigns.instance, () -> {
               if (signs.containsKey(world)) {
                   for (Sign sign : signs.get(world)) {
                       if (sign.isPlayerInArea(player)) {
                           sign.showSign(player);
                       } else {
                           sign.hideSign(player);
                       }
                   }
               }
            });
        }
    }
}
