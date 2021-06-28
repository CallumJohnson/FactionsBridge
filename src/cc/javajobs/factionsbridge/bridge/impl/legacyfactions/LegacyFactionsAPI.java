package cc.javajobs.factionsbridge.bridge.impl.legacyfactions;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.impl.legacyfactions.events.LegacyFactionsListener;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.entity.FPlayerColl;
import net.redstoneore.legacyfactions.entity.FactionColl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LegacyFactions implementation of {@link FactionsAPI}.
 *
 * @author Callum Johnson
 * @since 04/05/2021 - 10:09
 */
public class LegacyFactionsAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return FactionColl.all().stream().map(LegacyFactionsFaction::new).collect(Collectors.toList());
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
        return new LegacyFactionsClaim(new FLocation(chunk));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull String id) {
        return new LegacyFactionsFaction(FactionColl.get().getFactionById(id));
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
        return new LegacyFactionsFaction(FactionColl.get().getByTag(tag));
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
        return new LegacyFactionsPlayer(FPlayerColl.get(player));
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
        net.redstoneore.legacyfactions.entity.Faction faction = FactionColl.get().createFaction();
        faction.setTag(name);
        return new LegacyFactionsFaction(faction);
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        FactionColl.get().removeFaction(faction.getId());
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        Bukkit.getPluginManager().registerEvents(new LegacyFactionsListener(), FactionsBridge.get().getDevelopmentPlugin());
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
        return new LegacyFactionsFaction(FactionColl.get().getWarZone());
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        return new LegacyFactionsFaction(FactionColl.get().getSafeZone());
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        return new LegacyFactionsFaction(FactionColl.get().getWilderness());
    }

}
