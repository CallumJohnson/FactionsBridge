package factionsx;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import factionsx.events.FactionsXListener;
import net.prosavage.factionsx.manager.FactionManager;
import net.prosavage.factionsx.manager.GridManager;
import net.prosavage.factionsx.manager.PlayerManager;
import net.prosavage.factionsx.persist.data.FLocation;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsX implementation of {@link FactionsAPI}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 16:43
 */
public class FactionsXAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return FactionManager.INSTANCE.getFactions().stream().map(FactionsXFaction::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return IFaction at that location
     */
    @Override
    public Faction getFactionAt(@NotNull Location location) {
        return new FactionsXFaction(GridManager.INSTANCE.getFactionAt(location.getChunk()));
    }

    /**
     * Method to obtain an IClaim from Chunk.
     *
     * @param chunk to convert
     * @return IClaim object.
     */
    @NotNull
    @Override
    public Claim getClaim(@NotNull Chunk chunk) {
        return new FactionsXClaim(new FLocation(chunk.getX(), chunk.getZ(), chunk.getWorld().getName()));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull String id) {
        return new FactionsXFaction(FactionManager.INSTANCE.getFaction(Long.parseLong(id)));
    }

    /**
     * Method to retrieve an Faction from Tag.
     *
     * @param tag of the Faction
     * @return Faction implementation.
     */
    @Nullable
    @Override
    public Faction getFactionByTag(@NotNull String tag) {
        return new FactionsXFaction(FactionManager.INSTANCE.getFaction(tag));
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull OfflinePlayer player) {
        return new FactionsXFaction(PlayerManager.INSTANCE.getFPlayer(player.getUniqueId()).getFaction());
    }

    /**
     * Method to obtain the FPlayer by a Player.
     * <p>
     * Due to the SpigotAPI, OfflinePlayer == Player through implementation, so you can pass both here.
     * </p>
     *
     * @param player to get the FPlayer equivalent for.
     * @return FPlayer implementation.
     */
    @NotNull
    @Override
    public FPlayer getFPlayer(@NotNull OfflinePlayer player) {
        return new FactionsXPlayer(PlayerManager.INSTANCE.getFPlayer(player.getUniqueId()));
    }

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the IFaction exists already.
     */
    @NotNull
    @Override
    public Faction createFaction(@NotNull String name) throws IllegalStateException {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("FactionsX doesn't support createFaction(name).");
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        FactionManager.INSTANCE.deleteFaction(
                (net.prosavage.factionsx.core.Faction) ((AbstractFaction<?>) faction).getFaction());
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public boolean register() {
        Bukkit.getPluginManager().registerEvents(
                new FactionsXListener(),
                FactionsBridge.get().getDevelopmentPlugin()
        );
        return true;
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWarZone() {
        return new FactionsXFaction(FactionManager.INSTANCE.getFaction(FactionManager.WARZONE_ID));
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        return new FactionsXFaction(FactionManager.INSTANCE.getFaction(FactionManager.SAFEZONE_ID));
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        return new FactionsXFaction(FactionManager.INSTANCE.getWilderness());
    }

}
