package cc.javajobs.factionsbridge.bridge;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.util.Communicator;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
     * @return the Plugin which will be used or {@link Optional#empty()}.
     * @see ProviderManager
     */
    public Optional<Plugin> discover(@Nullable String forcedProvider) {
        return Arrays.stream(Provider.values())
            .filter(provider -> {
                final Plugin plugin = provider.getPlugin();
                return plugin != null;
            })
            .map(provider -> {
                final boolean isForcedProvider = provider.name().equalsIgnoreCase(forcedProvider) || provider.fancy().equalsIgnoreCase(forcedProvider);
                final Plugin plugin = provider.getPlugin();
                final PluginDescriptionFile description = plugin.getDescription();
                final List<String> authors = description.getAuthors();
                final String version = description.getVersion();
                // Try version and author matching first
                Optional<AuthorConfiguration> configuration = provider.findMatchingAuthorConfiguration(version, authors, isForcedProvider);
                if (configuration.isPresent()) {
                    return Optional.of(confirmHook(configuration.get(), provider, plugin));
                }
                // Fallback to author-only matching
                AuthorConfiguration authorConfiguration = provider.authorsMatch(authors);
                if (authorConfiguration != null) {
                    return Optional.of(confirmHook(authorConfiguration, provider, plugin));
                }
                return Optional.<Plugin>empty();
            })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst();
    }

    private Plugin confirmHook(AuthorConfiguration configuration, Provider provider, Plugin plugin) {
        log("Found " + provider.name() + "!");
        log("Matched '" + configuration + "'!");
        spacer(ChatColor.AQUA);
        log("Hooking into API for " + provider.fancy() + "!");
        spacer(ChatColor.AQUA);
        fapi = provider.getAPI();
        hookedProvider = provider;
        return plugin;
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

