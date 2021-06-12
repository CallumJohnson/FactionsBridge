package cc.javajobs.factionsbridge.bridge;

import cc.javajobs.factionsbridge.bridge.impl.atlasfactions.AtlasFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.factionsblue.FactionsBlueAPI;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDAPI;
import cc.javajobs.factionsbridge.bridge.impl.factionsx.FactionsXAPI;
import cc.javajobs.factionsbridge.bridge.impl.kingdoms.KingdomsAPI;
import cc.javajobs.factionsbridge.bridge.impl.legacyfactions.LegacyFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.massivecorefactions.MassiveCoreFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.medievalfactions.MedievalFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.saberfactions.SaberFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.savagefactions.SavageFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.supremefactions.SupremeFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.ultimatefactions.UltimateFactionsAPI;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

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

    Factions_AtlasFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "Elapsed"), new AtlasFactionsAPI()),
    Factions_FactionsBlue("FactionsBlue", Collections.singletonList("NickD"), new FactionsBlueAPI()),
    Factions_FactionsUUID("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "CmdrKittens"), new FactionsUUIDAPI()),
    Factions_FactionsX("FactionsX", Collections.singletonList("ProSavage"), new FactionsXAPI()),
    Kingdoms_Kingdoms("Kingdoms", Collections.singletonList("Crypto Morin"), new KingdomsAPI()),
    Factions_MassiveCoreFactions("Factions", Arrays.asList("Cayorion", "Madus", "Ulumulu1510", "MarkehMe", "Brettflan"), new MassiveCoreFactionsAPI()),
    Factions_SaberFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "DroppingAnvil", "Driftay"), new SaberFactionsAPI()),
    Factions_SavageFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "SvenjaReißaus", "Driftay", "D3cide", "Savag3life"), new SavageFactionsAPI()),
    Factions_SupremeFactions("Factions", Arrays.asList("Olof Larsson", "Brett Flannigan", "drtshock", "ProSavage", "Savag3life", "Lone____Wolf", "SupremeDev"), new SupremeFactionsAPI()),
    Factions_LegacyFactions("LegacyFactions", Arrays.asList("Cayorion", "Madus", "Ulumulu1510", "MarkehMe", "Brettflan", "drtshoc"), new LegacyFactionsAPI()),
    Factions_MedievalFactions("MedievalFactions", Arrays.asList("DanTheTechMan", "Pasarus", "Caibinus"), new MedievalFactionsAPI()),
    Factions_UltimateFactions("UltimateFactions", Collections.singletonList("Miinoo_"), new UltimateFactionsAPI());

    private final String pluginName;
    private final List<String> authors;
    private final FactionsAPI api;
    private final String[] classes;

    /**
     * Constructor to initialise a Provider.
     * @param pluginName of the Provider.
     * @param authors which made the Provider.
     * @param fapi {@link FactionsAPI} implementation.
     * @param classes to check for to be sure the Provider is correctly identified (unused).
     */
    Provider(String pluginName, List<String> authors, FactionsAPI fapi, String... classes) {
        this.pluginName = pluginName;
        this.authors = authors;
        this.api = fapi;
        this.classes = classes;
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
        if (this.authors == null || authors == null) return new boolean[0];
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
     * Method to count how many predefined classes are found at runtime.
     * @return the amount of classes which are found.
     */
    public int areClassesLoaded() {
        int loadedClasses = 0;
        if (classes == null) return loadedClasses;
        for (String aClass : classes) {
            try {
                Class.forName(aClass);
                loadedClasses++;
            } catch (ClassNotFoundException ignored) {
            }
        }
        return loadedClasses;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public FactionsAPI getAPI() {
        return api;
    }

    /**
     * Method to get the total amount of classes which are being checked.
     * @return amount of classes which are provided in the constructor.
     */
    public int getClassCount() {
        if (classes == null) return 0;
        return classes.length;
    }

    /**
     * Method to obtain the fancy name of the Provider.
     * @return fancy name!
     */
    public String fancy() {
        return name().replaceAll("\\w+_", "");
    }

    /**
     * Method to obtain the Provider relative to the FactionsAPI class.
     *
     * @param factionsAPI to use to find the Provider.
     * @return {@link Provider} or {@code null}.
     */
    public static Provider getFromAPI(FactionsAPI factionsAPI) {
        for (Provider value : Provider.values()) {
            if (value.getAPI().equals(factionsAPI)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Provider{" + "pluginName='" + pluginName + '\'' + ", authors=" + String.join(", ", authors) + '}';
    }

}
