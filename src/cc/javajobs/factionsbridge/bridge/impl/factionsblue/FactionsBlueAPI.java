package cc.javajobs.factionsbridge.bridge.impl.factionsblue;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.impl.factionsblue.events.FactionsBlueListener;
import cc.javajobs.factionsbridge.bridge.impl.factionsblue.tasks.FactionsBlueTasks;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import me.zysea.factions.FPlugin;
import me.zysea.factions.api.FactionsApi;
import me.zysea.factions.interfaces.Factions;
import me.zysea.factions.persist.FactionsMemory;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FactionsBlue Implementation of the {@link FactionsAPI}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 14:27
 */
public class FactionsBlueAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        Factions factions = FPlugin.getInstance().getFactions();
        Collection<me.zysea.factions.faction.Faction> facs = ((FactionsMemory) factions).getAllFactions();
        return facs.stream().map(FactionsBlueFaction::new).collect(Collectors.toList());
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
        return new FactionsBlueClaim(new me.zysea.factions.objects.Claim(chunk));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull String id) {
        try {
            return new FactionsBlueFaction(FactionsApi.getFaction(Integer.parseInt(id)));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Method to retrive an IFaction from Name.
     *
     * @param name of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public Faction getFactionByTag(@NotNull String name) {
        return new FactionsBlueFaction(FactionsApi.getFaction(name));
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull OfflinePlayer player) {
        return new FactionsBlueFaction(FactionsApi.getFaction(player));
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
        return new FactionsBluePlayer(FactionsApi.getFPlayer(player));
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
        Faction fac = getFactionByName(name);
        if (fac != null && !fac.isServerFaction()) {
            throw new IllegalStateException("Faction already exists.");
        }
        Factions factions = FPlugin.getInstance().getFactions();
        int id = factions.generateFactionId();
        me.zysea.factions.faction.Faction f = new me.zysea.factions.faction.Faction(id, name);
        ((FactionsMemory) factions).put(f);
        return new FactionsBlueFaction(f);
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        ((FactionsBlueFaction) faction).getFaction().disband();
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        if (FactionsBridge.getFactionsAPI().hasRegistered()) return;
        Bukkit.getPluginManager().registerEvents(
                new FactionsBlueListener(),
                FactionsBridge.get().getDevelopmentPlugin()
        );
        Bukkit.getScheduler().runTaskTimer(
                FactionsBridge.get().getDevelopmentPlugin(),
                new FactionsBlueTasks(),
                0,
                12000
        );
        FactionsBridge.get().registered = true;
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWarZone() {
        return Objects.requireNonNull(getFaction("-1"));
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        return Objects.requireNonNull(getFaction("0"));
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        return Objects.requireNonNull(getFaction("-2"));
    }

}
