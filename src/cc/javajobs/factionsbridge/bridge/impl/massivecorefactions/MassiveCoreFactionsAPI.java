package cc.javajobs.factionsbridge.bridge.impl.massivecorefactions;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.impl.massivecorefactions.events.MassiveCoreFactionsListener;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.MStore;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MassiveCore implementation of {@link FactionsAPI}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 15:25
 */
public class MassiveCoreFactionsAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return FactionColl.get().getAll()
                .stream().map(MassiveCoreFactionsFaction::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return Faction at that location
     */
    @Override
    public Faction getFactionAt(@NotNull Location location) {
        return new MassiveCoreFactionsFaction(BoardColl.get().getFactionAt(PS.valueOf(location)));
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
        return new MassiveCoreFactionsClaim(PS.valueOf(chunk));
    }

    /**
     * Method to retrieve an Faction from Id.
     *
     * @param id of the Faction
     * @return Faction implementation.
     */
    @Override
    public Faction getFaction(@NotNull String id) {
        return new MassiveCoreFactionsFaction(FactionColl.get().getByName(id));
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
     * Method to retrieve an Faction from Player/OfflinePlayer.
     *
     * @param player in the Faction.
     * @return Faction implementation.
     */
    @Override
    public Faction getFaction(@NotNull OfflinePlayer player) {
        return new MassiveCoreFactionsFaction(MPlayer.get(player).getFaction());
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
        return new MassiveCoreFactionsPlayer(MPlayer.get(player));
    }

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return Faction implementation.
     * @throws IllegalStateException if the Faction exists already.
     */
    @NotNull
    @Override
    public Faction createFaction(@NotNull String name) throws IllegalStateException {
        Faction fac = getFactionByName(name);
        if (fac != null && !fac.isServerFaction()) {
            throw new IllegalStateException("Faction already exists.");
        }
        String fId = MStore.createId();
        com.massivecraft.factions.entity.Faction faction = FactionColl.get().create(fId);
        faction.setName(name);
        return new MassiveCoreFactionsFaction(faction);
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        ((MassiveCoreFactionsFaction) faction).getFaction().detach();
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        if (FactionsBridge.getFactionsAPI().hasRegistered()) return;
        Bukkit.getPluginManager().registerEvents(
                new MassiveCoreFactionsListener(),
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
        return new MassiveCoreFactionsFaction(FactionColl.get().getWarzone());
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        return new MassiveCoreFactionsFaction(FactionColl.get().getSafezone());
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        return new MassiveCoreFactionsFaction(FactionColl.get().getNone());
    }

}
