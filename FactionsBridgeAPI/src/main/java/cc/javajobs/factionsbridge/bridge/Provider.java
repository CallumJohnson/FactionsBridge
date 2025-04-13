package cc.javajobs.factionsbridge.bridge;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The Provider enumeration is a data store class for all plugins supported by this release of FactionsBridge.
 * <p>
 * Each enum entry has a Plugin Name, Author List, {@link FactionsAPI} implementation
 * and possibilities of adding further data in the form of a String array to be more precise
 * if ever required.
 * </p>
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 09:54
 */
public enum Provider {

    Factions_AtlasFactions(
            "Factions",
            "atlasfactions.AtlasFactionsAPI",
            new AuthorConfiguration("Atlas_1", "Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "Elapsed")
    ),
    Factions_FactionsBlue(
            "FactionsBlue",
            "factionsblue.FactionsBlueAPI",
            new AuthorConfiguration("1.1.6 Stable", "NickD")
    ),
    Factions_FactionsUUIDv1(
            "FactionsUUID",
            "factionsuuidv1.FactionsUUIDAPI",
            new AuthorConfiguration("1.0.0-SNAPSHOT", "mbaxter", "drtshock", "dariasc", "Brett Flannigan", "Olof Larsson")
    ),
    Factions_FactionsUUID(
            "Factions",
            "factionsuuid.FactionsUUIDAPI",
            new AuthorConfiguration("1.6.9.5-U0.5.24", "Olof Larsson", "Brett Flannigan", "drtshock", "CmdrKittens"),
            new AuthorConfiguration("1.6.9.5-U0.5.25", "Olof Larsson", "Brett Flannigan", "drtshock", "dariasc", "CmdrKittens", "mbaxter"),
            new AuthorConfiguration("VulcanFactions-Fork", "Olof Larsson", "Brett Flannigan", "drtshock", "CmdrKittens", "Jerry")
    ),
    Factions_FactionsX(
            "FactionsX",
            "factionsx.FactionsXAPI",
            new AuthorConfiguration("1.2-STABLE", "ProSavage")
    ),
    Factions_Kingdoms14(
            "Kingdoms",
            "kingdoms14.KingdomsAPI",
            new AuthorConfiguration("1.14", "Crypto Morin")
    ),
    Factions_Kingdoms(
            "Kingdoms",
            "kingdoms.KingdomsAPI",
            new AuthorConfiguration("1.10.19.2", "Crypto Morin")
    ),
    Factions_LegacyFactions(
            "LegacyFactions",
            "legacyfactions.LegacyFactionsAPI",
            new AuthorConfiguration("1.4.7", "Cayorion", "Madus", "Ulumulu1510", "MarkehMe", "Brettflan", "drtshoc")
    ),
    Factions_MassiveCoreFactions(
            "Factions",
            "massivecorefactions.MassiveCoreFactionsAPI",
            new AuthorConfiguration("2.14.0", "Cayorion", "Madus", "Ulumulu1510", "MarkehMe", "Brettflan")
    ),
    Factions_MedievalFactions5(
            "MedievalFactions",
            "medievalfactions5.MedievalFactionsAPI",
            new AuthorConfiguration("5.1.1", "DanTheTechMan" + "Pasarus", "Caibinus", "Callum", "Richardhyy", "Mitras2", "Kaonami", "GoodLucky777", "Elafir", "Deej", "VoChiDanh", "alyphen")
    ),
    Factions_MedievalFactions(
            "MedievalFactions",
            "medievalfactions.MedievalFactionsAPI",
            new AuthorConfiguration("v4.1", "DanTheTechMan", "Pasarus", "Caibinus"),
            new AuthorConfiguration("v4.2-beta-1", "DanTheTechMan", "Pasarus", "Caibinus", "Callum")
    ),
    Factions_SaberFactions(
            "Factions",
            "saberfactions.SaberFactionsAPI",
            new AuthorConfiguration("2.9.1-RC", "Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "DroppingAnvil", "Driftay"),
            new AuthorConfiguration("3.0.1-RC", "Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "DroppingAnvil", "Driftay", "SaberDev"),
            new AuthorConfiguration("3.0.2-RC", "Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "DroppingAnvil", "Driftay", "SaberDev", "Callum"),
            new AuthorConfiguration("4.0.2-RC", "Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "DroppingAnvil", "Driftay", "SaberDev", "Callum", "Atilt")
    ),
    Factions_SavageFactions(
            "Factions",
            "savagefactions.SavageFactionsAPI",
            new AuthorConfiguration("2.5-RC-9", "Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "SvenjaReißaus", "Driftay", "D3cide", "Savag3life")
    ),
    Factions_SupremeFactions(
            "Factions",
            "supremefactions.SupremeFactionsAPI",
            new AuthorConfiguration("1.6.9.5-U0.2.1-RC-1.6.2-RC-2.5-RC-9", "Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "Savag3life", "Lone____Wolf", "SupremeDev")
    ),
    Factions_Towny(
            "Towny",
            "towny.TownyAPI",
            new AuthorConfiguration("0.97.0.0", "Shade, Modified by FuzzeWuzze. Forked by ElgarL. Forked by LlmDl.")
    ),
    Factions_UltimateFactions(
            "UltimateFactions",
            "ultimatefactions.UltimateFactionsAPI",
            new AuthorConfiguration("4.3.4", "Miinoo_")
    ),
    Factions_KoreFactions(
            "Factions",
            "korefactions.KoreFactionsAPI",
            new AuthorConfiguration("1.6.9.5-U0.5.18-b", "Olof Larsson", "Brett Flannigan", "drtshock", "CmdrKittens", "Golfing8")
    ),
    Factions_ImprovedFactions(
            "ImprovedFactions",
            "improvedfactions.ImprovedFactionsAPI",
            new AuthorConfiguration("BETA-5.0.4", "Tobero")
    );

    /**
     * The plugin name of the given Provider.
     */
    private final String pluginName;

    /**
     * The authors of the plugin of the given Provider.
     */
    private final AuthorConfiguration[] authors;

    /**
     * The api class which will be obtainable using {@link FactionsBridge#getFactionsAPI()}.
     */
    private final String API_CLASS_NAME;

    /**
     * Constructor to initialise a Provider.
     *
     * @param pluginName     of the Provider.
     * @param configurations of authors who made the Provider.
     * @param fapiName       The class name for the {@link FactionsAPI} implementation.
     */
    Provider(@NotNull String pluginName, @Nullable String fapiName, @NotNull AuthorConfiguration... configurations) {
        this.pluginName = pluginName;
        this.authors = configurations;
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
        for (Provider value : Provider.values()) {
            if (value.API_CLASS_NAME.equals(location)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Method to obtain the Plugin linked to the Provider.
     *
     * @return {@link Plugin} or {@code null}.
     */
    public Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin(pluginName);
    }

    /**
     * Method to match authors to a Provider.
     *
     * @param authors of the Plugin identified at runtime.
     * @return {@link Boolean} if the entire list matches or not.
     */
    @Nullable
    public AuthorConfiguration authorsMatch(List<String> authors) {
        if (authors == null || authors.isEmpty()) return null;
        for (final AuthorConfiguration configuration : this.authors) {
            if (configuration.equals(authors)) return configuration;
        }
        return null;
    }

    /**
     * Method to match authors to a Provider.
     *
     * @param authors of the Plugin identified at runtime.
     * @param version of  the Plugin identified at runtime.
     * @return {@link Boolean} if the entire list matches or not.
     */
    public AuthorConfiguration versionAndAuthorsMatch(String version, List<String> authors) {
        if (authors == null || authors.isEmpty() || version.isEmpty()) return null;
        for (final AuthorConfiguration configuration : this.authors) {
            if (!version.startsWith(configuration.getVersion()) && !version.equals(configuration.getVersion())) {
                continue;
            }
            if (configuration.equals(authors)) {
                return configuration;
            }
        }
        return null;
    }

    /**
     * Method to get the preconfigured authors of the Provider.
     *
     * @return {@link List} of authors.
     */
    @NotNull
    public List<String> getAuthors() {
        final ArrayList<String> pluginAuthors = new ArrayList<>(getPlugin().getDescription().getAuthors());
        for (AuthorConfiguration author : authors) {
            if (author.equals(pluginAuthors)) {
                return author.getAuthors();
            }
        }
        return new ArrayList<>();
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
     *
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
