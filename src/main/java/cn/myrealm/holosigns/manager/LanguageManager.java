package cn.myrealm.holosigns.manager;

import cn.myrealm.holosigns.HoloSigns;
import org.bukkit.Bukkit;
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
    private final String lang;
    private final File file;
    private final YamlConfiguration langYml;
    /**
     * @Description: Constructor
     * @Param:
     * @return:
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public LanguageManager() {
        super();
        lang = HoloSigns.instance.getConfig().getString("language", "english");
        file = new File(HoloSigns.instance.getDataFolder(),"lang/" + lang + ".yml");
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
        throw new IllegalArgumentException("No Such Language Key");
    }
    
    /**
     * @Description: Adding variables to text
     * @Param: [key, varMap]
     * @return: java.lang.String
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public String getVarText(String key, Map<String,String> varMap) {
        String text = getText(key);
        for (String var : varMap.keySet()) {
            if (Objects.nonNull(varMap.get(var))) {
                text.replace("%"+var+"%",varMap.get(var));
            }
        }
        return text;
    }
}
