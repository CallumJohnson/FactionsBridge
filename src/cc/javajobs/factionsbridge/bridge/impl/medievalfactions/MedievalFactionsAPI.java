package cc.javajobs.factionsbridge.bridge.impl.medievalfactions;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.bridge.impl.medievalfactions.events.MedievalFactionsListener;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import dansplugins.factionsystem.ChunkManager;
import dansplugins.factionsystem.MedievalFactions;
import dansplugins.factionsystem.commands.DisbandCommand;
import dansplugins.factionsystem.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * An implementation for MedievalFactions of the FactionsAPI.
 *
 * @author Callum Johnson
 * @since 03/05/2021 - 09:26
 */
public class MedievalFactionsAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return PersistentData.getInstance().getFactions().stream()
                .map(MedievalFactionsFaction::new)
                .collect(Collectors.toList());
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
    @NotNull
    @Override
    public Faction getFaction(@NotNull String id) {
        return new MedievalFactionsFaction(PersistentData.getInstance().getFaction(id));
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
        return getFaction(tag);
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull OfflinePlayer player) {
        return new MedievalFactionsFaction(PersistentData.getInstance().getPlayersFaction(player.getUniqueId()));
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
        return new MedievalFactionsPlayer(player);
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
        dansplugins.factionsystem.objects.Faction faction = new dansplugins.factionsystem.objects.Faction
                (name, UUID.randomUUID(), MedievalFactions.getInstance().getConfig().getInt("initialMaxPowerLevel"));
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
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        try {
            MedievalFactionsFaction fac = (MedievalFactionsFaction) faction;
            int index = PersistentData.getInstance().getFactions().indexOf(fac.getFaction());
            Class<?> disbandCommandClass = DisbandCommand.class;
            Method method = disbandCommandClass.getDeclaredMethod("removeFaction", Integer.TYPE, OfflinePlayer.class);
            method.setAccessible(true);
            method.invoke(this, index, null);
        } catch (Exception ex) {
            if (FactionsBridge.get().catch_exceptions) return;
            throw new BridgeMethodException(getClass(), "deleteFaction()", "Reflection failed, failed to delete Faction.");
        }
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        if (FactionsBridge.getFactionsAPI().hasRegistered()) return;
        Bukkit.getPluginManager().registerEvents(
                new MedievalFactionsListener(),
                FactionsBridge.get().getDevelopmentPlugin()
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
        if (FactionsBridge.get().catch_exceptions)
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        throw new BridgeMethodUnsupportedException("MedievalFactions doesn't support getWarZone");
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        if (FactionsBridge.get().catch_exceptions)
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        throw new BridgeMethodUnsupportedException("MedievalFactions doesn't support getSafeZone");
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        if (FactionsBridge.get().catch_exceptions)
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        throw new BridgeMethodUnsupportedException("MedievalFactions doesn't support getWilderness");
    }

}
