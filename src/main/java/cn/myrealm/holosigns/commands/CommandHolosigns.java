package cn.myrealm.holosigns.commands;

import cn.myrealm.holosigns.HoloSigns;
import cn.myrealm.holosigns.listeners.SignCreateListener;
import cn.myrealm.holosigns.managers.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: HoloSigns
 * @description: Base command
 * @author: rzt1020
 * @create: 2022/09/28
 **/
public class CommandHolosigns implements CommandExecutor {
    // vars
    private static final Map<String,CommandExecutor> subCommands = new HashMap<>();
    static {
        subCommands.put("reload",new CommandReload());
    }

    /**
     * @Description: execute the command
     * @Param: [sender]
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    private void executeCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageManager.instance.getText("only-player"));
            return;
        }
        Player player = (Player) sender;
        if (player.hasPermission("holosigns.command.holosigns")) {
            player.sendMessage(LanguageManager.instance.getVarText("create-sign","time_limit",
                            String.valueOf(HoloSigns.instance.getConfig().getInt("time_limit",-1))));
            SignCreateListener.registerListener(player);
        }
    }

    /**
     * @Description: Base command executor
     * @Param: [sender, cmd, s, args]
     * @return: boolean
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equals("holosigns")) {
            if (args.length == 0) {
                executeCommand(sender);
            } else {
                if (subCommands.containsKey(args[0])) {
                    if (args.length > 1) {
                        args = Arrays.copyOfRange(args, 1, args.length);
                    } else {
                        args = new String[0];
                    }
                    subCommands.get(args[0]).onCommand(sender,cmd,s,args);
                } else {
                    sender.sendMessage(LanguageManager.instance.getText("no-such-command"));
                }
            }
            return true;
        }
        return false;
    }
}
