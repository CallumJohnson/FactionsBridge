package cc.javajobs.factionsbridge.bridge;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.util.Communicator;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.math.BigDecimal;
import java.math.MathContext;
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
 * @author Callum Johnson
 * @since 25/02/2021 - 16:48
 */
public class ProviderManager implements Communicator {

    private FactionsAPI fapi;

    /**
     * Method to perform all functionality of the ProviderManager class.
     * @see ProviderManager
     * @param classLevel should the method scan for classes? (an unused implementation as of 26/02/2021).
     * @return the Plugin which will be used or {@code null}.
     */
    public Plugin discover(boolean classLevel) {
        for (Provider provider : Provider.values()) {
            Plugin plugin = provider.getPlugin();
            if (plugin == null) continue;
            boolean hook = false;

            boolean[] authors = provider.authorsMatch(new ArrayList<>(plugin.getDescription().getAuthors()));
            List<String> providerAuthors = provider.getAuthors();
            int auth = (int) IntStream.range(0, providerAuthors.size()).filter(i -> authors[i]).count();
            if (providerAuthors.size() == auth) hook = true;
            if (classLevel) {
                int classes = provider.areClassesLoaded();
                int totalClasses = provider.getClassCount();
                log("Classes Found:\t" + classes + "/" + totalClasses);
                double per = (double) (classes*100)/totalClasses;
                per = new BigDecimal(per).round(new MathContext(2)).doubleValue();
                log("Percentage Found:\t" + per+ "%");
                if (per < 75 && hook) hook = false;
            }

            if (hook) {
                log("Found " + provider.name() + "!");
                spacer(ChatColor.AQUA);
                log("Hooking into API for " + provider.fancy() + "!");
                spacer(ChatColor.AQUA);
                fapi = provider.getAPI();
                return plugin;
            }
        }
        return null;
    }

    /**
     * Method to return the FactionAPI implementation.
     * @return FactionAPI implementation.
     */
    public FactionsAPI getAPI() {
        return fapi;
    }

}

