package cn.myrealm.holosigns;

import cn.myrealm.holosigns.managers.LanguageManager;
import cn.myrealm.holosigns.managers.Manager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HoloSigns extends JavaPlugin {
    // vars
    public static HoloSigns instance;
    public static List<Manager> managers;

    /**
     * @Description: Call on enable
     * @Param: []
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    @Override
    public void onEnable() {
        instance = this;
        managers = new ArrayList<>();
        
        File file = new File(getDataFolder(),"config.yml");
        if(!file.exists()) {
            saveDefaultConfig();
            resourcesOutput();
        }
        
        managers.add(new LanguageManager());
        getLogger().info(LanguageManager.instance.getText("plugin-init"));

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe(LanguageManager.instance.getText("holo-displays-unload"));
            getLogger().severe(LanguageManager.instance.getText("plugin-disable"));
            setEnabled(false);
            return;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            getLogger().severe(LanguageManager.instance.getText("protocol-lib-unload"));
            getLogger().severe(LanguageManager.instance.getText("plugin-disable"));
            setEnabled(false);
            return;
        }

        getLogger().info(LanguageManager.instance.getText("plugin-init-completed"));
    }

    /**
     * @Description: Call on disable
     * @Param: []
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    @Override
    public void onDisable() {
        instance = null;
        managers = null;
    }

    /**
     * @Description: reload the plugin
     * @Param: []
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public void reload() {
        for (Manager manager : managers) {
            manager.reload();
        }
    }
    
    /**
     * @Description: Resources output
     * @Param: []
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public void resourcesOutput() {
        saveResource("language/chinese.yml",false);
    }

    /**
     * @Description: Color symbols and dex for strings
     * @Param: [s]
     * @return: java.lang.String
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public static String parseColor(String s){
        Pattern pattern = Pattern.compile("<#[a-fA-F0-9]{6}>");
        Matcher match = pattern.matcher(s);
        while (match.find()) {
            String color = s.substring(match.start(),match.end());
            s = s.replace(color, ChatColor.of(color.replace("<","").replace(">","")) + "");
            match = pattern.matcher(s);
        }
        return s.replace("&","§").replace("§§","&");
    }

}
