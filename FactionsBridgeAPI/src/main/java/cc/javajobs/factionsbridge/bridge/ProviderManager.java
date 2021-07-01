package cc.javajobs.factionsbridge.bridge;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.util.Communicator;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The Provider Manager class handles all methodology to locate/identify the API-provider for the plugin.
 * <p>
 *     This class discovers the plugin which will be used to provide API functionality to the bridge.
 *     <br>Using the {@link Provider} enumeration class and data manually added, this class compares
 *     plugins loaded on the server. If a match is found, the plugin is returned.
 * </p>
 *
 * @author Callum Johnson
 * @since 25/02/2021 - 16:48
 */
public class ProviderManager implements Communicator {

    /**
     * API variable set if/when the provider is found.
     */
    private FactionsAPI fapi;

    /**
     * Provider variable set for Metrics.
     */
    private Provider hookedProvider;

    /**
     * Method to perform all functionality of the ProviderManager class.
     *
     * @return the Plugin which will be used or {@code null}.
     * @see ProviderManager
     */
    public Plugin discover() {
        for (Provider provider : Provider.values()) {
            Plugin plugin = provider.getPlugin();
            if (plugin == null) continue;
            boolean hook = false;
            boolean[] authors = provider.authorsMatch(new ArrayList<>(plugin.getDescription().getAuthors()));
            List<String> providerAuthors = provider.getAuthors();
            int auth = (int) IntStream.range(0, providerAuthors.size()).filter(i -> authors[i]).count();
            if (providerAuthors.size() == auth) hook = true;
            if (hook) {
                log("Found " + provider.name() + "!");
                spacer(ChatColor.AQUA);
                log("Hooking into API for " + provider.fancy() + "!");
                spacer(ChatColor.AQUA);
                fapi = provider.getAPI();
                hookedProvider = provider;
                return plugin;
            }
        }
        return null;
    }

    /**
     * Method to return the FactionsAPI implementation.
     *
     * @return FactionsAPI implementation.
     */
    public FactionsAPI getAPI() {
        return fapi;
    }

    /**
     * Method to obtain the Hooked Provider.
     *
     * @return {@link Provider} object.
     */
    public Provider getHookedProvider() {
        return hookedProvider;
    }

}

