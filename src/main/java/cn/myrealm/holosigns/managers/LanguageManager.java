package cn.myrealm.holosigns.managers;

import cn.myrealm.holosigns.HoloSigns;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * @program: HoloSigns
 * @description: Managing Language Information
 * @author: rzt1020
 * @create: 2022/09/28
 **/
public class LanguageManager extends Manager{
    // vars
    public static LanguageManager instance;
    private final YamlConfiguration langYml;
    
    /**
     * @Description: Constructor
     * @Param: []
     * @return: 
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public LanguageManager() {
        instance = this;
        String lang = HoloSigns.instance.getConfig().getString("language", "english");
        File file = new File(HoloSigns.instance.getDataFolder(), "language/" + lang + ".yml");
        langYml = YamlConfiguration.loadConfiguration(file);
    }
    
    /**
     * @Description: Get text from language file
     * @Param: [key]
     * @return: java.lang.String
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public String getText(@NonNull String key) {
        if (langYml.contains(key)) {
            String text = langYml.getString(key,"");
            return HoloSigns.parseColor(text);
        }
        throw new IllegalArgumentException("No such language key");
    }
    
    /**
     * @Description: Adding variables to text
     * @Param: [key, varMap]
     * @return: java.lang.String
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public String getVarText(@NonNull String key, Map<String,String> var_map) {
        String text = getText(key);
        if (Objects.isNull(var_map)) {
            return text;
        }
        for (String var : var_map.keySet()) {
            if (Objects.nonNull(var_map.get(var))) {
                text = text.replace("%"+var+"%",var_map.get(var));
            }
        }
        return text;
    }
    
    /**
     * @Description: Adding variables to text
     * @Param: [key, var, content]
     * @return: java.lang.String
     * @Author: rzt1020
     * @Date: 2022/9/29
    **/
    public String getVarText(@NonNull String key, String var, String content) {
        String text = getText(key);
        if (Objects.isNull(var) || Objects.isNull(content)) {
            return text;
        }
        text = text.replace("%"+var+"%",content);
        return text;
    }
}
