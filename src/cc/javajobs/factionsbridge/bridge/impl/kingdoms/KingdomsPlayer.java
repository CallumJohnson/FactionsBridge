package cc.javajobs.factionsbridge.bridge.impl.kingdoms;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.player.KingdomPlayer;

import java.util.UUID;

/**
 * Kingdoms implementation of IFactionPlayer.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 17:08
 */
public class KingdomsPlayer extends AbstractFPlayer<KingdomPlayer> {

    /**
     * Constructor to create an KingdomsPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public KingdomsPlayer(@NotNull KingdomPlayer fPlayer) {
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
        return fPlayer.getId();
    }

    /**
     * Method to get the Name of the Faction Player.
     *
     * @return name of the Player.
     */
    @NotNull
    @Override
    public String getName() {
        return getOfflinePlayer().getName() == null ? "Robot_" + Math.random() : getOfflinePlayer().getName();
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public Faction getFaction() {
        return new KingdomsKingdom(fPlayer.getKingdom());
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
        return fPlayer.hasKingdom();
    }

    /**
     * Method to get the Offline form of the Player.
     *
     * @return {@link OfflinePlayer}
     */
    @NotNull
    @Override
    public OfflinePlayer getOfflinePlayer() {
        return fPlayer.getOfflinePlayer();
    }

    /**
     * Method to get the Player related to the FPlayer.
     * <p>
     * Please use {@link #isOnline()} before relying on this method as it can return null if the player isn't online.
     * </p>
     *
     * @return {@link Player} or {@code null}.
     * @see #isOnline()
     */
    @Nullable
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
        return fPlayer.getOfflinePlayer().isOnline();
    }

}
