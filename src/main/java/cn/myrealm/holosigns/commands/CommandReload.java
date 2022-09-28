package cn.myrealm.holosigns.commands;

import cn.myrealm.holosigns.HoloSigns;
import cn.myrealm.holosigns.managers.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @program: HoloSigns
 * @description: Reload the plugin
 * @author: rzt1020
 * @create: 2022/09/28
 **/
public class CommandReload implements CommandExecutor {
    /**
     * @Description: Reload command executor
     * @Param: [commandSender, command, s, strings]
     * @return: boolean
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission("holosigns.command.reload")) {
            HoloSigns.instance.reload();
        } else {
            commandSender.sendMessage(LanguageManager.instance.getText("do-not-have-permission"));
        }
        return true;
    }
}
