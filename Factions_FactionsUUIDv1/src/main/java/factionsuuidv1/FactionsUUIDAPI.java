package factionsuuidv1;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import dev.kitteh.factions.FLocation;
import dev.kitteh.factions.FPlayers;
import dev.kitteh.factions.Factions;
import factionsuuidv1.events.FactionsUUIDListener;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsUUID implementation of {@link FactionsAPI}.
 */
public class FactionsUUIDAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return Factions.getInstance().getAllFactions().stream().map(FactionsUUIDFaction::new)
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
        return new FactionsUUIDClaim(new FLocation(chunk));
    }

    /**
     * Method to retrieve a Faction by id.
     *
     * @param id of the Faction
     * @return Faction implementation.
     */
    @Nullable
    @Override
    public Faction getFaction(@NotNull String id) {
        dev.kitteh.factions.Faction fac = getFactionInternalById(id);
        return fac == null ? null : new FactionsUUIDFaction(fac);
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
        dev.kitteh.factions.Faction fac = Factions.getInstance().getByTag(tag);
        return fac == null ? null : new FactionsUUIDFaction(fac);
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
        return new FactionsUUIDFPlayer(FPlayers.getInstance().getByPlayer(player));
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWarZone() {
        return new FactionsUUIDFaction(Factions.getInstance().getWarZone());
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        return new FactionsUUIDFaction(Factions.getInstance().getSafeZone());
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        return new FactionsUUIDFaction(Factions.getInstance().getWilderness());
    }

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the Faction exists already.
     */
    @NotNull
    @Override
    public Faction createFaction(@NotNull String name) throws IllegalStateException {
        if (Factions.getInstance().getByTag(name) != null) throw new IllegalStateException("Faction already exists.");
        return new FactionsUUIDFaction(Factions.getInstance().createFaction(name));
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        dev.kitteh.factions.Faction fac = getFactionInternalById(faction.getId());
        if (fac == null) {
            throw new IllegalStateException("Invalid faction id.");
        }
        Factions.getInstance().removeFaction(fac);
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public boolean register() {
        Bukkit.getPluginManager().registerEvents(
                new FactionsUUIDListener(),
                FactionsBridge.get().getDevelopmentPlugin()
        );
        return true;
    }

    private @Nullable dev.kitteh.factions.Faction getFactionInternalById(String id) {
        try {
            return Factions.getInstance().getFactionById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid faction id.");
        }
    }
}
