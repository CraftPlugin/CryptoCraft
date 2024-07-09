package kr.acog.crypto;

import io.typst.bukkit.view.BukkitView;
import io.typst.bukkit.view.ChestView;
import io.typst.bukkit.view.page.PageViewLayout;
import kr.acog.crypto.view.PluginView;
import kr.acog.crypto.view.ViewFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * Create by. Acog
 */
public class PluginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        CryptoCraftPlugin plugin = CryptoCraftPlugin.inst;
        ChestView view = PluginView.getCryptoView(plugin, ViewFilter.ALL, player.getUniqueId());
        BukkitView.openView(view, player, plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                ChestView updateView = PluginView.getCryptoView(plugin, ViewFilter.ALL, player.getUniqueId());
                boolean state = BukkitView.updateView(updateView, player);

                if (!state) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20L);
        return true;
    }

}
