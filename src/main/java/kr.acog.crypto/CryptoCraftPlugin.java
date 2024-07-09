package kr.acog.crypto;

import io.typst.bukkit.view.BukkitView;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Create by. Acog
 */
public class CryptoCraftPlugin extends JavaPlugin {

    public static CryptoCraftPlugin inst;

    private CoinFetcher fetcher;
    public Map<String, Coin> coins = new HashMap<>();
    public Map<UUID,Map<Coin, Integer>> players = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        int updateInterval = getConfig().getInt("update-interval");
        List<String> list = getConfig().getStringList("coin_list");

        inst = this;
        coins = CoinConfiguration.init(this, list);
        fetcher = CoinFetcher.of(coins);

        BukkitView.register(this);
        getCommand("암호화폐").setExecutor(new PluginCommand());
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            this.coins = fetcher.fetch();
        }, 20L, updateInterval * 20L);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
