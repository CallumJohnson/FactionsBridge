package cc.javajobs.factionsbridge.bridge.impl.massivecorefactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * MassiveCoreFactions implementation of IFactionPlayer.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 15:17
 */
public class MassiveCoreFactionsPlayer extends AbstractFPlayer<MPlayer> {

    /**
     * Constructor to create an MassiveCoreFactionsPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public MassiveCoreFactionsPlayer(@NotNull MPlayer fPlayer) {
        super(fPlayer);
    }

    /**
     * Method to get the unique Id of the Faction Player.
     *
     * @return UUID (UniqueId).
     */
    @NotNull
    @Override
    public UUID getUniqueId() {
        return fPlayer.getUuid();
    }

    /**
     * Method to get the Name of the Faction Player.
     *
     * @return name of the Player.
     */
    @NotNull
    @Override
    public String getName() {
        return fPlayer.getName();
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public Faction getFaction() {
        return new MassiveCoreFactionsFaction(fPlayer.getFaction());
    }

    /**
     * Method to determine if the Player is in a Faction & if the Faction isn't a System Faction.
     * <p>
     * Some Factions implementations, if a Player isn't in a Faction, the Player is assumed
     * to be "factionless" which is defaulted to Wilderness.
     * </p>
     *
     * @return {@code true} if the player is in a faction other than Wilderness/WarZone/SafeZone.
     */
    @Override
    public boolean hasFaction() {
        return fPlayer.hasFaction();
    }

    /**
     * Method to get the Offline form of the Player.
     *
     * @return {@link OfflinePlayer}
     */
    @NotNull
    @Override
    public OfflinePlayer getOfflinePlayer() {
        return fPlayer.getPlayer();
    }

    /**
     * Method to get the Online form of the Player.
     *
     * @return {@link Player}
     * @see FPlayer#isOnline()
     */
    @Override
    public Player getPlayer() {
        return fPlayer.getPlayer();
    }

    /**
     * Uses Bukkit methodology to determine if the Player is online.
     *
     * @return {@code true} = yes, {@code false} = no.
     */
    @Override
    public boolean isOnline() {
        return fPlayer.isOnline();
    }

}
