package cc.javajobs.factionsbridge.bridge.impl.factionsblue;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import me.zysea.factions.FPlugin;
import me.zysea.factions.api.FactionsApi;
import me.zysea.factions.faction.Faction;
import me.zysea.factions.interfaces.Factions;
import me.zysea.factions.objects.Claim;
import me.zysea.factions.persist.FactionsMemory;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsBlue Implementation of the IFactionsAPI.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 26/02/2021 - 14:27
 */
public class FactionsBlueAPI implements IFactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @Override
    public List<IFaction> getAllFactions() {
        Factions factions = FPlugin.getInstance().getFactions();
        Collection<Faction> facs = ((FactionsMemory) factions).getAllFactions();
        return facs.stream().map(FactionsBlueFaction::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return IFaction at that location
     */
    @Override
    public IFaction getFactionAt(Location location) {
        return new FactionsBlueFaction(FactionsApi.getOwner(new Claim(location)));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(String id) {
        return new FactionsBlueFaction(FactionsApi.getFaction(id));
    }

    /**
     * Method to retrive an IFaction from Name.
     *
     * @param name of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFactionByName(String name) {
        return new FactionsBlueFaction(FactionsApi.getFaction(name));
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(OfflinePlayer player) {
        return new FactionsBlueFaction(FactionsApi.getFaction(player));
    }

    /**
     * Method to get an IFactionPlayer from Player/OfflinePlayer.
     *
     * @param player related to the IFactionPlayer.
     * @return IFactionPlayer implementation.
     */
    @Override
    public IFactionPlayer getFactionPlayer(OfflinePlayer player) {
        return new FactionsBluePlayer(FactionsApi.getFPlayer(player));
    }

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the IFaction exists already.
     */
    @Override
    public IFaction createFaction(String name) throws IllegalStateException {
        IFaction fac = getFactionByName(name);
        if (fac != null && !fac.isServerFaction()) {
            throw new IllegalStateException("Faction already exists.");
        }
        Factions factions = FPlugin.getInstance().getFactions();
        int id = factions.generateFactionId();
        Faction f = new Faction(id, name);
        ((FactionsMemory) factions).put(f);
        return new FactionsBlueFaction(f);
    }

}
