package improvedfactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import io.github.toberocat.improvedfactions.factions.FactionUtils;
import io.github.toberocat.improvedfactions.ranks.AdminRank;
import io.github.toberocat.improvedfactions.ranks.MemberRank;
import io.github.toberocat.improvedfactions.ranks.OwnerRank;
import io.github.toberocat.improvedfactions.ranks.Rank;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.UUID;

/**
 * ImprovedFactions implementation of {@link FPlayer}.
 * Object Target: {@link OfflinePlayer}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 16:31
 */
public class ImprovedFactionsPlayer extends AbstractFPlayer<OfflinePlayer> {

    /**
     * Constructor to create an FactionsXPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public ImprovedFactionsPlayer(@NotNull OfflinePlayer fPlayer) {
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
        return fPlayer.getUniqueId();
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
     * Method to get the Faction linked to the FactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public Faction getFaction() {
        return new ImprovedFactionsFaction(FactionUtils.getFaction(getUniqueId()));
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
        // FactionsUUID only checks for '-1', this method removes WarZone and SafeZone issues too.
        return FactionUtils.getFaction(getUniqueId()) != null;
    }

    /**
     * Method to get the Offline form of the Player.
     *
     * @return {@link OfflinePlayer}
     */
    @NotNull
    @Override
    public OfflinePlayer getOfflinePlayer() {
        return fPlayer;
    }

    /**
     * Method to get the Online form of the Player.
     *
     * @return {@link Player}
     * @see FPlayer#isOnline()
     */
    @Override
    public Player getPlayer() {
        return ((Player) fPlayer);
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
        if (bridge.catch_exceptions) return 0.0D;
        return (double) unsupported(getProvider(), "getPower()");
    }

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "setPower(power)");
    }

    /**
     * Method to obtain the title of the FPlayer.
     *
     * @return title or tag of the FPlayer.
     */
    @Nullable
    @Override
    public String getTitle() {
        if (bridge.catch_exceptions) return null;
        return (String) unsupported(getProvider(), "getTitle()");
    }

    /**
     * Method to set the title of the FPlayer.
     *
     * @param title to set.
     */
    @Override
    public void setTitle(@NotNull String title) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "setTitle(title)");
    }

    /**
     * Method to get the Role of the FPlayer.
     *
     * @return {@link Role}
     */
    @NotNull
    @Override
    public Role getRole() {
        final io.github.toberocat.improvedfactions.factions.Faction faction = FactionUtils.getFaction(getPlayer());
        if (!hasFaction() || getFaction() == null || faction == null) return Role.FACTIONLESS;
        final Rank playerRank;
        if (isOnline()) {
            playerRank = FactionUtils.getPlayerRank(faction, getPlayer());
        } else {
            playerRank = Arrays.stream(faction.getMembers()).filter(m -> m.getUuid().equals(getUniqueId())).findFirst()
                    .get().getRank();
        }
        if (playerRank instanceof OwnerRank) return Role.LEADER;
        if (playerRank instanceof AdminRank) return Role.OFFICER;
        if (playerRank instanceof MemberRank) return Role.NORMAL;
        else return Role.FACTIONLESS;
    }

}
