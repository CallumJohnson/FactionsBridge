package cc.javajobs.factionsbridge.bridge.impl.factionsblue;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import me.zysea.factions.api.FactionsApi;
import me.zysea.factions.faction.FPlayer;
import me.zysea.factions.faction.Members;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * FactionsBlue Implementation of the IFactionPlayer.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 14:21
 */
public class FactionsBluePlayer extends AbstractFPlayer<FPlayer> {

    /**
     * Constructor to create an AbstractFPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public FactionsBluePlayer(@NotNull FPlayer fPlayer) {
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
        return fPlayer.getName();
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public Faction getFaction() {
        if (!fPlayer.hasFaction()) {
            // -2 is Wilderness/Factionless
            return new FactionsBlueFaction(FactionsApi.getFaction(-2));
        }
        return new FactionsBlueFaction(fPlayer.getFaction());
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
    @Override
    public OfflinePlayer getOfflinePlayer() {
        return fPlayer.getOfflinePlayer();
    }

    /**
     * Method to get the Online form of the Player.
     *
     * @return {@link Player}
     * @see #isOnline()
     */
    @Override
    public Player getPlayer() {
        return fPlayer.getOfflinePlayer().getPlayer();
    }

    /**
     * Uses Bukkit methodology to determine if the Player is online.
     *
     * @return {@code true} = yes, {@code false} = no.
     */
    @Override
    public boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }

    /**
     * Method to get the power of the FPlayer.
     *
     * @return power value.
     */
    @Override
    public double getPower() {
        if (bridge.catch_exceptions) return 0.0D;
        return (double) unsupported("FactionsBlue", "getPower()");
    }

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        if (bridge.catch_exceptions) return;
        unsupported("FactionsBlue", "setPower(power)");
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
        return (String) unsupported("FactionsBlue", "getTitle()");
    }

    /**
     * Method to set the title of the FPlayer.
     *
     * @param title to set.
     */
    @Override
    public void setTitle(@NotNull String title) {
        if (bridge.catch_exceptions) return;
        unsupported("FactionsBlue", "setTitle(title)");
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
        final AbstractFaction<?> faction = (AbstractFaction<?>) getFaction();
        final me.zysea.factions.faction.Faction f = (me.zysea.factions.faction.Faction) faction.getFaction();
        final Members memberInformation = f.getMembers();
        final me.zysea.factions.faction.role.Role memberRole = memberInformation.getMemberRole(getUniqueId());
        return Role.getRole(memberRole.getTag());
    }

}
