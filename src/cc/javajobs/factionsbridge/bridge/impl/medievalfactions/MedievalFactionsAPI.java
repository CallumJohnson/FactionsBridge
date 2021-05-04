package cc.javajobs.factionsbridge.bridge.impl.medievalfactions;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import dansplugins.factionsystem.ChunkManager;
import dansplugins.factionsystem.MedievalFactions;
import dansplugins.factionsystem.commands.DisbandCommand;
import dansplugins.factionsystem.data.PersistentData;
import dansplugins.factionsystem.objects.Faction;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Callum Johnson
 * @since 03/05/2021 - 09:26
 */
public class MedievalFactionsAPI implements IFactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @Override
    public List<IFaction> getAllFactions() {
        return PersistentData.getInstance().getFactions().stream()
                .map(MedievalFactionsFaction::new)
                .collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return IFaction at that location
     */
    @Override
    public IFaction getFactionAt(Location location) {
        MedievalFactionsClaim claim = (MedievalFactionsClaim) getClaimAt(location);
        return claim == null ? null : claim.getFaction();
    }

    /**
     * Method to obtain an IClaim from Location.
     *
     * @param location to get IClaim from.
     * @return IClaim object.
     */
    @Override
    public IClaim getClaimAt(Location location) {
        return getClaimAt(location.getChunk());
    }

    /**
     * Method to obtain an IClaim from Chunk.
     *
     * @param chunk to convert
     * @return IClaim object.
     */
    @Override
    public IClaim getClaimAt(Chunk chunk) {
        return new MedievalFactionsClaim(ChunkManager.getInstance().getClaimedChunk(
                chunk.getX(), chunk.getZ(), chunk.getWorld().getName(),
                PersistentData.getInstance().getClaimedChunks()
        ));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(String id) {
        return new MedievalFactionsFaction(PersistentData.getInstance().getFaction(id));
    }

    /**
     * Method to retrieve an IFaction from Name.
     *
     * @param name of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFactionByName(String name) {
        return getFaction(name);
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(OfflinePlayer player) {
        return new MedievalFactionsFaction(PersistentData.getInstance().getPlayersFaction(player.getUniqueId()));
    }

    /**
     * Method to get an IFactionPlayer from Player/OfflinePlayer.
     *
     * @param player related to the IFactionPlayer.
     * @return IFactionPlayer implementation.
     */
    @Override
    public IFactionPlayer getFactionPlayer(OfflinePlayer player) {
        return new MedievalFactionsPlayer(player);
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
        Faction faction = new Faction(name, UUID.randomUUID(), MedievalFactions.getInstance().getConfig().getInt("initialMaxPowerLevel"));
        PersistentData.getInstance().getFactions().add(faction);
        return new MedievalFactionsFaction(faction);
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(IFaction faction) throws IllegalStateException {
        try {
            int index = PersistentData.getInstance().getFactions().indexOf((Faction) faction.asObject());
            Class<?> disbandCommandClass = DisbandCommand.class;
            Method method = disbandCommandClass.getDeclaredMethod("removeFaction", Integer.TYPE);
            method.setAccessible(true);
            method.invoke(this, index);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "deleteFaction()");
        }
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        FactionsBridge.get().registered = true;
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link IFaction}
     */
    @Override
    public IFaction getWarZone() {
        throw new BridgeMethodUnsupportedException("MedievalFactions doesn't support getWarZone");
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link IFaction}
     */
    @Override
    public IFaction getSafeZone() {
        throw new BridgeMethodUnsupportedException("MedievalFactions doesn't support getSafeZone");
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link IFaction}
     */
    @Override
    public IFaction getWilderness() {
        return null;
    }

}
