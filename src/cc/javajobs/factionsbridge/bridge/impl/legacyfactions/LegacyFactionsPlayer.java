package cc.javajobs.factionsbridge.bridge.impl.legacyfactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import net.redstoneore.legacyfactions.entity.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * LegacyFactions implementation of {@link cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer}.
 * Object Target: {@link FPlayer}.
 *
 * @author Callum Johnson
 * @since 04/05/2021 - 09:55
 */
public class LegacyFactionsPlayer extends AbstractFPlayer<FPlayer> {

    /**
     * Constructor to create an LegacyFactionsPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public LegacyFactionsPlayer(@NotNull FPlayer fPlayer) {
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
        return UUID.fromString(fPlayer.getId());
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
        return new LegacyFactionsFaction(fPlayer.getFaction());
    }

    /**
     * Method to determine if the Player is in a Faction &amp; if the Faction isn't a System Faction.
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
    @SuppressWarnings("deprecation")
    @Override
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(fPlayer.getId());
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

    /**
     * Method to get the power of the FPlayer.
     *
     * @return power value.
     */
    @Override
    public double getPower() {
        return fPlayer.getPower();
    }

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        fPlayer.alterPower(power);
    }

    /**
     * Method to obtain the title of the FPlayer.
     *
     * @return title or tag of the FPlayer.
     */
    @Nullable
    @Override
    public String getTitle() {
        return fPlayer.getTitle();
    }

    /**
     * Method to set the title of the FPlayer.
     *
     * @param title to set.
     */
    @Override
    public void setTitle(@NotNull String title) {
        fPlayer.setTitle(title);
    }

    /**
     * Method to get the Role of the FPlayer.
     *
     * @return {@link Role}
     */
    @NotNull
    @Override
    public Role getRole() {
        if (!hasFaction() || getFaction() == null) return Role.FACTIONLESS;
        return Role.getRole(fPlayer.getRole().name());
    }

}
