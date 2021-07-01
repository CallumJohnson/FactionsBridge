package cc.javajobs.factionsbridge;

import cc.javajobs.factionsbridge.bridge.Provider;
import cc.javajobs.factionsbridge.bridge.ProviderManager;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeAlreadyConnectedException;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.util.Communicator;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsBridge API.
 * <p>
 *     This API acts as a bridge of multiple implementations of Factions,
 *     combining their functionality into one core API.
 *     <br>This class dismisses deprecation as I have to support both Java 8 and Java 16.
 * </p>
 * @author Callum Johnson
 * @since 25/02/2021 - 09:05
 */
@SuppressWarnings("deprecation")
public class FactionsBridge implements Communicator {

    private static final String version;
    private static FactionsBridge instance = null;
    private static FactionsAPI factionapi = null;
    public boolean registered = false;
    public boolean catch_exceptions;
    private Plugin development_plugin = null;

    static {
        try {
            final Class<?> version_class = Class.forName("cc.javajobs.factionsbridge.Version");
            final Field field = version_class.getField("build_version");
            if (!field.isAccessible()) field.setAccessible(true);
            version = (String) field.get(null);
        } catch (ReflectiveOperationException ex) {
            throw new BridgeMethodException(FactionsBridge.class, "version_getter", "Failed to get Version from class");
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
     * Method to 'connect' the Bridge for a plugin.
     * @param plugin to connect for.
     * @throws BridgeAlreadyConnectedException if the bridge is already setup.
     */
    public void connect(@NotNull JavaPlugin plugin) throws BridgeAlreadyConnectedException {
        connect(plugin, true, true, true);
    }

    /**
     * Method to 'connect' the Bridge for a plugin.
     * <p>
     *     Specifying "requiresFactions", throws exceptions.<br>
     *     If "requiresFactions" is false, then console output is done based on "consoleOutput".
     * </p>
     *
     * @param plugin to connect for.
     * @param consoleOutput {@code true} if console output should be shown.
     * @param requiresFactions {@code true} if your plugin needs factions to work.
     * @param catchExceptions {@code true} if you want to reduce exceptions when a function isn't supported.
     * @throws BridgeAlreadyConnectedException if the bridge is already setup.
     */
    public void connect(JavaPlugin plugin, boolean consoleOutput, boolean requiresFactions, boolean catchExceptions)
            throws BridgeAlreadyConnectedException {
        if (plugin == null) {
            if (requiresFactions) {
                throw new IllegalStateException("Plugin cannot be null.");
            } else {
                if (consoleOutput) {
                    error("Plugin cannot be null.");
                }
            }
            return;
        }
        if (instance != null && instance.development_plugin != null) {
            if (requiresFactions) {
                throw new BridgeAlreadyConnectedException(
                        plugin.getName() + " has tried to connect to the already instantiated FactionsBridge"
                );
            } else {
                if (consoleOutput) {
                    error(
                            plugin.getName() + " has tried to connect to the already instantiated FactionsBridge"
                    );
                }
            }
        }
        long start = System.currentTimeMillis();
        if (instance == null) instance = this;
        instance.development_plugin = plugin;
        ProviderManager manager = new ProviderManager();
        Plugin provider = manager.discover();
        factionapi = manager.getAPI();
        String status = "without";
        if ((provider == null || factionapi == null) && consoleOutput) {
            spacer(ChatColor.RED);
            warn("-> Failed to find Provider for the Server.");
            generateReport();
            warn("-> End of Report. (Send a Screenshot of this to 'C A L L U M#4160' on Discord)");
            spacer(ChatColor.RED);
            status = "with";
        }
        this.catch_exceptions = catchExceptions;
        if (factionapi != null) {
            registered = factionapi.register();
            if (!registered) {
                status = "with";
            } else {
                if (consoleOutput) {
                    spacer(ChatColor.AQUA);
                    log("This plugin uses bStats to track non-specific server data.");
                    log("Go to '/plugins/bStats/' to change this if you do not consent.");
                    log("With thanks to: &bhttps://bstats.org");
                    error("Thank you, Callum");
                    spacer(ChatColor.AQUA);
                }
                final Metrics metrics = new Metrics(plugin, 11893);
                metrics.addCustomChart(new SimplePie("factions_implementation_used",
                        () -> manager.getHookedProvider().name()));
                metrics.addCustomChart(new SimplePie("standalone_or_shaded",
                        () -> String.valueOf(isFactionsBridge(plugin))));
            }
        }
        long diff = System.currentTimeMillis()-start;
        if (consoleOutput) log("FactionsBridge started in " + diff + " milliseconds " + status + " errors.");
    }

    /**
     * Method to determine if the plugin connecting is FactionsBridge or not.
     *
     * @param plugin to test.
     * @return {@code true} if it is.
     */
    private boolean isFactionsBridge(@NotNull JavaPlugin plugin) {
        final PluginDescriptionFile description = plugin.getDescription();
        return  description.getName().equals("FactionsBridge") &&
                description.getVersion().equals(version) &&
                description.getMain().equals("cc.javajobs.factionsbridge.BridgePlugin");
    }

    /**
     * Method to obtain the Development plugin (FactionsBridge or ElitePets for example).
     * @return {@link Plugin} which connected the Bridge.
     */
    public Plugin getDevelopmentPlugin() {
        return development_plugin;
    }

    /**
     * Method to determine if the Bridge has successfully connected or not.
     * @return {@code true} it it has.
     */
    public boolean connected() {
        return factionapi != null && development_plugin != null && registered;
    }

    /**
     * Method to obtain the {@link FactionsAPI} implementation.
     * @return {@link FactionsAPI} implementation.
     */
    public static FactionsAPI getFactionsAPI() {
        return factionapi;
    }

    /**
     * Method to obtain the Version of FactionsBridge for the commands.
     * @return String version.
     */
    public static String getVersion() {
        return version;
    }

}
