package cc.javajobs.factionsbridge;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.ProviderManager;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.util.Communicator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsBridge plugin.
 * <p>
 *     This plugin acts as a middleground between Factions plugins and plugins which require API
 *     functionality of said Factions plugins.
 * </p>
 * @author Callum Johnson
 * @since 25/02/2021 - 09:05
 */
public class FactionsBridge extends JavaPlugin implements Communicator {

    private static String version;

    private static FactionsBridge instance;
    private static IFactionsAPI factionapi;
    public boolean registered;
    private Plugin provider;

    /**
     * Method to 'enable' the plugin.
     * <p>
     *     This method finds the API provider and links it to {@link FactionsBridge#factionapi}.
     * </p>
     */
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();
        ProviderManager manager = new ProviderManager();
        this.provider = manager.discover(false);
        factionapi = manager.getAPI();
        version = getDescription().getVersion();
        String status = "without";
        long diff = System.currentTimeMillis()-start;
        if (provider == null) {
            spacer(ChatColor.RED);
            warn("-> Failed to find Provider for the Server.");
            generateReport();
            warn("-> End of Report. (Send a Screenshot of this to 'C A L L U M#4160' on Discord)");
            spacer(ChatColor.RED);
            status = "with";
        }
        log("FactionsBridge started in " + diff + " milliseconds " + status + " errors.");
        if (factionapi != null) {
            factionapi.register();
            int loaded = factionapi.getAllFactions().size();
            warn(loaded + " factions have been loaded.");
        }
    }

    /**
     * Method to generate the report for debugging or error checking when the Plugin to be linked to isn't found.
     */
    private void generateReport() {
        spacer(ChatColor.WHITE);
        warn("Printing Report§7:");
        warn("NMS Version§7:\t§f" + Bukkit.getServer().getClass().getPackage());
        warn("Bukkit Version§7:\t§f" + Bukkit.getVersion() + "/" + Bukkit.getBukkitVersion());
        PluginManager manager = Bukkit.getPluginManager();
        int pluginCount = manager.getPlugins().length;
        warn("FactionsBridge Version§7:\t§f" + version);
        warn("Plugins§7:");
        int width = Math.min(5, pluginCount);
        int rows = ((int) Math.ceil(((double) pluginCount / width)));
        List<String> plugins = Arrays.stream(manager.getPlugins()).map(Plugin::getName).sorted().collect(Collectors.toList());
        int index = 0;
        for (int i = 0; i < rows; i++) {
            if (plugins.size() == index) break;
            StringBuilder sb = new StringBuilder("\t\t");
            for (int j = 0; j < width; j++) {
                String pluginName = plugins.get(index);
                sb.append(pluginName.contains("Faction") || pluginName.contains("Kingdom") ? "§b" : "§f");
                sb.append(pluginName);
                if (index != pluginCount-1) {
                    sb.append("§c, ");
                }
                index++;
                if (plugins.size() == index) break;
            }
            log(sb.toString());
        }
        warn("Plugin Count§7:\t\t§f" + pluginCount);
        warn("Factions Found§7:\t\t§f" + (Bukkit.getPluginManager().getPlugin("Factions") != null));
        warn("FactionsBlue Found§7:\t§f" + (Bukkit.getPluginManager().getPlugin("FactionsBlue") != null));
        warn("FactionsX Found§7:\t§f" + (Bukkit.getPluginManager().getPlugin("FactionsX") != null));
        warn("Kingdoms Found§7:\t\t§f" + (Bukkit.getPluginManager().getPlugin("Kingdoms") != null));
        spacer(ChatColor.WHITE);
    }

    /**
     * Method to get a static instance of FactionsBridge.
     * @return FactionsBridge instance.
     */
    public static FactionsBridge get() {
        return instance;
    }

    /**
     * Method to obtain the Providing plugin (Factions or Kindgsoms for example).
     * @return {@link Plugin} which provides {@link IFactionsAPI}.
     */
    public Plugin getProvider() {
        return provider;
    }

    /**
     * Method to obtain the {@link IFactionsAPI} implementation.
     * @return {@link IFactionsAPI} implementation.
     */
    public static IFactionsAPI getFactionsAPI() {
        return factionapi;
    }

}
