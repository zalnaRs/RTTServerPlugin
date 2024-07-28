package app.netlify.randomttips.rttserverplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public final class RTTServerPlugin extends JavaPlugin {

    private final String text = "\n-----------------\n§fI want to thank everyone who donated/sent gifts and played on my server.\n§f-----------------\n§dTikTok§8/§cYouTube§f: @randomttips\nWebsite: https://randomttips.netlify.app\n-----------------\n";
    private final int maxPlayersCount = getServer().getMaxPlayers();
    private final long maxRam = Runtime.getRuntime().totalMemory() / 1048576;
    private final long criticalRam = maxRam-1000;

    @Override
    public void onEnable() {
        // Plugin startup logic
        BukkitScheduler scheduler = this.getServer().getScheduler();

        scheduler.scheduleSyncRepeatingTask(this, () -> getServer().broadcast(Component.text(text)), 0, 36000/*120*/);


        new BukkitRunnable() {
            @Override
            public void run() {
                double tps = Math.round(Bukkit.getTPS()[0] * 100.0) / 100.0;
                long usedRam = maxRam-(Runtime.getRuntime().freeMemory() / 1048576);
                int playerCount = getServer().getOnlinePlayers().size();

                String playerColor;
                if (playerCount >= 19) {
                    playerColor = "§e";
                } else {
                    playerColor = "§f";
                }

                String heading = "§f§lRandomTechTips' server§r§7\n  https://randomttips.netlify.app/server§r";
                String playerCounter = STR."§7Players online: \{playerColor}\{playerCount}§7/§f\{maxPlayersCount}";

                String ramColor;
                if (usedRam >= criticalRam) {
                    ramColor = "§e";
                } else {
                    ramColor = "§f";
                }

                String tpsColor;
                if (tps <= 19) {
                    tpsColor = "§e";
                } else {
                    tpsColor = "§f";
                }

                String tpsText = STR."§7TPS:\{tpsColor} \{tps}";
                String ram = STR."§7RAM:\{ramColor} \{usedRam}§7MB/§f\{maxRam}§7MB";

                getServer().sendPlayerListHeaderAndFooter(
                        Component.text(STR."\n  \{heading}  \n  \{playerCounter}  \n"),
                        Component.text(STR."\n  \{tpsText}  \n  \{ram}  \n")
                );
            }
        }.runTaskTimer(this, 0, 25);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
