package cc.javajobs.factionsbridge.bridge;

import cc.javajobs.factionsbridge.util.Communicator;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * The Provider Manager class handles all methodology to locate/identify the API-provider for the plugin.
 * <p>
 *     This class discovers the plugin which will be used to provide API functionality to the bridge.
 *     <br>Using the {@link Provider} enumeration class and data manually added, this class compares
 *     plugins loaded on the server. If a match is found, the plugin is returned.
 * </p>
 * @author Callum Johnson
 * @version 1.0
 * @since 25/02/2021 - 16:48
 */
public class ProviderManager implements Communicator {

    private IFactionsAPI fapi;

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
            log("Plugin found for " + provider.name());
            boolean hook = false;
            boolean[] authors = provider.authorsMatch(plugin.getDescription().getAuthors());
            List<String> providerAuthors = provider.getAuthors();
            int auth = 0;
            StringBuilder bs = new StringBuilder();
            for (int i = 0; i < providerAuthors.size(); i++) {
                String author = providerAuthors.get(i);
                bs.append("Author:\t").append(author).append("\t(").append(authors[i]).append(")");
                if (i != providerAuthors.size()-1) {
                    bs.append(", ");
                }
                if (authors[i]) auth++;
            }
            if (providerAuthors.size() == auth) hook = true;
            log(bs.toString());
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
     * Method to return the IFactionAPI implementation.
     * @return IFactionAPI implementation.
     */
    public IFactionsAPI getAPI() {
        return fapi;
    }

}

