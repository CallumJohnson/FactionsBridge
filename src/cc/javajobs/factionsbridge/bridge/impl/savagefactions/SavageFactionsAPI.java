package cc.javajobs.factionsbridge.bridge.impl.savagefactions;

import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDAPI;
import cc.javajobs.factionsbridge.bridge.impl.saberfactions.SaberFactionsClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class SavageFactionsAPI extends FactionsUUIDAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return Factions.getInstance().getAllFactions().stream().map(SavageFactionsFaction::new)
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
        return new SaberFactionsClaim(new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
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
        return new SavageFactionsFaction(Factions.getInstance().getFactionById(id));
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
        return new SavageFactionsFaction(Factions.getInstance().getByTag(tag));
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
        return new SavageFactionsFPlayer(FPlayers.getInstance().getByOfflinePlayer(player));
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWarZone() {
        return new SavageFactionsFaction(Factions.getInstance().getWarZone());
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        return new SavageFactionsFaction(Factions.getInstance().getSafeZone());
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        return new SavageFactionsFaction(Factions.getInstance().getWilderness());
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
        if (fac != null && !fac.isServerFaction()) throw new IllegalStateException("Faction already exists.");
        com.massivecraft.factions.Faction faction = Factions.getInstance().createFaction();
        faction.setTag(name);
        return new SavageFactionsFaction(faction);
    }

}
