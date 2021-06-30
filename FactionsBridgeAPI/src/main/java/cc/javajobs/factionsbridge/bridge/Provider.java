package cc.javajobs.factionsbridge.bridge;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Provider enumeration is a data store class for all plugins supported by this release of FactionsBridge.
 * <p>
 *     Each enum entry has a Plugin Name, Author List, {@link FactionsAPI} implementation
 *     and possibilities of adding further data in the form of a String array to be more precise
 *     if ever required.
 * </p>
 * @author Callum Johnson
 * @since 26/02/2021 - 09:54
 */
public enum Provider {

    Factions_AtlasFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "Elapsed"), "atlasfactions.AtlasFactionsAPI"),
    Factions_FactionsBlue("FactionsBlue", Collections.singletonList("NickD"), "factionsblue.FactionsBlueAPI"),
    Factions_FactionsUUID("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "CmdrKittens"), "factionsuuid.FactionsUUIDAPI"),
    Factions_FactionsX("FactionsX", Collections.singletonList("ProSavage"), "factionsx.FactionsXAPI"),
    Kingdoms_Kingdoms("Kingdoms", Collections.singletonList("Crypto Morin"), "kingdoms.KingdomsAPI"),
    Factions_LegacyFactions("LegacyFactions", Arrays.asList("Cayorion", "Madus", "Ulumulu1510", "MarkehMe", "Brettflan", "drtshoc"), "legacyfactions.LegacyFactionsAPI"),
    Factions_MassiveCoreFactions("Factions", Arrays.asList("Cayorion", "Madus", "Ulumulu1510", "MarkehMe", "Brettflan"), "massivecorefactions.MassiveCoreFactionsAPI"),
    Factions_MedievalFactions("MedievalFactions", Arrays.asList("DanTheTechMan", "Pasarus", "Caibinus"), "medievalfactions.MedievalFactionsAPI"),
    Factions_SaberFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "DroppingAnvil", "Driftay"), "saberfactions.SaberFactionsAPI"),
    Factions_SavageFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "SvenjaReißaus", "Driftay", "D3cide", "Savag3life"), "savagefactions.SavageFactionsAPI"),
    Factions_SupremeFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "Savag3life", "Lone____Wolf", "SupremeDev"), "supremefactions.SupremeFactionsAPI"),
    Factions_UltimateFactions("UltimateFactions", Collections.singletonList("Miinoo_"), "ultimatefactions.UltimateFactionsAPI");

    /**
     * The plugin name of the given Provider.
     */
    private final String pluginName;

    /**
     * The authors of the plugin of the given Provider.
     */
    private final List<String> authors;

    /**
     * The api class which will be obtainable using {@link FactionsBridge#getFactionsAPI()}.
     */
    private final String API_CLASS_NAME;

    /**
     * Constructor to initialise a Provider.
     * @param pluginName of the Provider.
     * @param authors which made the Provider.
     * @param fapiName The class name for the {@link FactionsAPI} implementation.
     */
    Provider(@NotNull String pluginName, @NotNull List<String> authors, @Nullable String fapiName) {
        this.pluginName = pluginName;
        this.authors = authors;
        this.API_CLASS_NAME = fapiName;
    }

    /**
     * Method to obtain a Provider from its API Class location.
     *
     * @param location to obtain a Provider for.
     * @return {@link Provider} or {@code null}.
     */
    @Nullable
    public static Provider getFromAPI(String location) {
        return Arrays.stream(Provider.values())
                .filter(value -> value.API_CLASS_NAME.equals(location))
                .findFirst()
                .orElse(null);
    }

    /**
     * Method to obtain the Plugin linked to the Provider.
     * @return {@link Plugin} or {@code null}.
     */
    public Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin(pluginName);
    }

    /**
     * Method to match authors to a Provider.
     * @param authors of the Plugin identified at runtime.
     * @return boolean[] of true/false based on matches.
     */
    public boolean[] authorsMatch(List<String> authors) {
        if (authors == null) return new boolean[0];
        if (this.authors.isEmpty() || authors.isEmpty()) return new boolean[0];
        Collections.sort(authors);
        Collections.sort(this.authors);
        boolean[] matches = new boolean[this.authors.size()];
        for (int i = 0; i < this.authors.size(); i++) {
            matches[i] = authors.contains(this.authors.get(i));
        }
        return matches;
    }

    /**
     * Method to get the preconfigured authors of the Provider.
     *
     * @return {@link List} of authors.
     */
    @NotNull
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Method to obtain the API class for the given provider.
     *
     * @return {@link FactionsAPI} implementation.
     */
    @Nullable
    public FactionsAPI getAPI() {
        FactionsAPI api = null;
        try {
            final Class<?> apiClass = Class.forName(API_CLASS_NAME);
            if (FactionsAPI.class.isAssignableFrom(apiClass)) {
                api = (FactionsAPI) apiClass.getConstructor().newInstance();
            } else throw new ClassNotFoundException("API class not found.");
        } catch (ReflectiveOperationException e) {
            FactionsBridge.get().exception(e, "Failed to find API class for " + name());
        }
        return api;
    }

    /**
     * Method to obtain the fancy name of the Provider.
     * @return fancy name!
     */
    @NotNull
    public String fancy() {
        return name().replaceAll("\\w+_", "");
    }

    /**
     * Plugin Name and the Enum shorthand for the given Provider.
     *
     * @return String representation if the Provider.
     */
    @NotNull
    @Override
    public String toString() {
        return pluginName + "_" + name();
    }

}
