package cc.javajobs.factionsbridge.bridge.impl.ultimatefactions;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.bridge.impl.ultimatefactions.events.UltimateFactionsListener;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * UltimateFactions implementation of the {@link FactionsAPI}.
 */
public class UltimateFactionsAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return FactionsSystem.getFactions().getFactions().stream().map(UltimateFactionsFaction::new)
                .collect(Collectors.toList());
    }

    /**
     * Method to obtain a Claim by chunk.
     *
     * @param chunk of the claim.
     * @return Claim implementation.
     */
    @NotNull
    @Override
    public Claim getClaim(@NotNull Chunk chunk) {
        return new UltimateFactionsClaim(new FactionChunk(chunk.getWorld(), chunk.getX(), chunk.getZ()));
    }

    /**
     * Method to retrieve an Faction by Id.
     *
     * @param id of the Faction
     * @return Faction implementation.
     */
    @Nullable
    @Override
    public Faction getFaction(@NotNull String id) {
        return new UltimateFactionsFaction(FactionsSystem.getFactions().getFaction(UUID.fromString(id)));
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
        return new UltimateFactionsFaction(FactionsSystem.getFactions().getFaction(tag));
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
        return new UltimateFactionsFPlayer(player);
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWarZone() {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Ultimate doesn't support getWarZone()");
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Ultimate doesn't support getSafeZone()");
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Ultimate doesn't support getWilderness()");
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
        de.miinoo.factions.model.Faction faction = new de.miinoo.factions.model.Faction(name, null, "Description");
        FactionsSystem.getFactions().saveFaction(faction);
        return new UltimateFactionsFaction(faction);
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        final Factions factions = FactionsSystem.getFactions();
        factions.removeFaction(factions.getFaction(UUID.fromString(faction.getId())));
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        if (FactionsBridge.getFactionsAPI().hasRegistered()) return;
        Bukkit.getPluginManager().registerEvents(
                new UltimateFactionsListener(),
                FactionsBridge.get().getDevelopmentPlugin()
        );
        FactionsBridge.get().registered = true;
    }

}
