package cn.myrealm.holosigns.util;

import cn.myrealm.holosigns.HoloSigns;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * @program: HoloSigns
 * @description: Sign for storing hologram information
 * @author: rzt1020
 * @create: 2022/09/28
 **/
public class Sign {
    // vars
    private Hologram holo_display, // Used to save the holo displayed when the player is not close
                     holo_hidden; // Used to save the holo displayed when the player is close
    private final VisibilityManager display_visibility_manager, // Visibility manager for holo_display
                                    hidden_visibility_manager; // Visibility manager for holo_hidden
    private final Set<Player> visible_players = new HashSet<>(); // All player can see this sign
    private final Location loc; // Sign location

    /**
     * @Description: Constructor
     * @Param: [msg, loc, player]
     * @return:
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public Sign(@NonNull String msg,@NonNull Location loc,@Nullable Player author) {
        @NonNull Location loc_handled = locHandle(loc);
        Bukkit.getScheduler().runTask(HoloSigns.instance,()->{
            holo_display = HologramsAPI.createHologram(HoloSigns.instance, loc_handled);
            holo_hidden = HologramsAPI.createHologram(HoloSigns.instance, loc_handled);
        });

        this.loc = loc_handled;

        holo_display.appendTextLine("✉");
        if (Objects.nonNull(author)) {
            holo_hidden.appendTextLine(author.getName() + " said:");
        }
        holo_hidden.appendTextLine(msg);
        display_visibility_manager = holo_display.getVisibilityManager();
        hidden_visibility_manager = holo_hidden.getVisibilityManager();
        hidden_visibility_manager.setVisibleByDefault(false);
    }
    
    /**
     * @Description: Reset a sign's holo
     * @Param: []
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/30
    **/
    public void resetSign() {
        visible_players.clear();
        display_visibility_manager.resetVisibilityAll();
        hidden_visibility_manager.resetVisibilityAll();
    }
    
    /**
     * @Description: Show a sign's holo for a player
     * @Param: [player]
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/30
    **/
    public void showSign(@NonNull Player player) {
        if (visible_players.contains(player)) return;
        visible_players.add(player);
        display_visibility_manager.hideTo(player);
        hidden_visibility_manager.showTo(player);
    }

    /**
     * @Description: Hide a sign's holo for a player
     * @Param: [player]
     * @return: void
     * @Author: rzt1020
     * @Date: 2022/9/30
    **/
    public void hideSign(@NonNull Player player) {
        if (!visible_players.contains(player)) return;
        visible_players.remove(player);
        display_visibility_manager.showTo(player);
        hidden_visibility_manager.showTo(player);
    }
    
    /**
     * @Description: Detects if the player is within the range where Sign can be seen. Players must be in the same world as Sign
     * @Param: [player]
     * @return: boolean
     * @Author: rzt1020
     * @Date: 2022/9/30
    **/
    public boolean isPlayerInArea(@NonNull Player player) {
        final Location[] player_loc = new Location[1];
        final Boolean[] b = new Boolean[1];
        Bukkit.getScheduler().runTask(HoloSigns.instance, () -> {
            player_loc[0] = player.getLocation();
            b[0] = loc.toVector().distance(player_loc[0].toVector()) <= HoloSigns.instance.getConfig().getDouble("visible_distance",3);
        });
        return b[0];
    }
    /**
     * @Description: Get the Location of the previous square of the current player's Location
     * @Param: [loc]
     * @return: org.bukkit.Location
     * @Author: rzt1020
     * @Date: 2022/9/29
    **/
    private Location locHandle(@NonNull Location loc) {
        return loc.getBlock().getRelative(BlockFace.UP).getLocation();
    }
}
