package cn.myrealm.holosigns.managers;

import cn.myrealm.holosigns.util.Sign;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: HoloSigns
 * @description: Managing Signs
 * @author: rzt1020
 * @create: 2022/09/28
 **/
public class SignManager extends Manager{
    // vars
    public static SignManager instance;
    private static List<Sign> signs;
    /**
     * @Description: Constructor
     * @Param: []
     * @return: 
     * @Author: rzt1020
     * @Date: 2022/9/29
    **/
    public SignManager() {
        instance = this;
        signs = new ArrayList<>();
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
        signs.add(sign);
    }
    
}
