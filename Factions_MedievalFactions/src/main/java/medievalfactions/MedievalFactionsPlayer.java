package medievalfactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import dansplugins.factionsystem.data.PersistentData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The MedievalFactions implementation utilises {@link OfflinePlayer}.
 *
 * @author Callum Johnson
 * @since 03/05/2021 - 09:16
 */
public class MedievalFactionsPlayer extends AbstractFPlayer<OfflinePlayer> {

    /**
     * Constructor to create an AbstractFPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public MedievalFactionsPlayer(@NotNull OfflinePlayer fPlayer) {
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
        return fPlayer.getName() == null ? "Robot_" + Math.random() : fPlayer.getName();
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    @Nullable
    public Faction getFaction() {
        return new MedievalFactionsFaction(PersistentData.getInstance().getPlayersFaction(getUniqueId()));
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
        return PersistentData.getInstance().isInFaction(getUniqueId());
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
        return (Player) fPlayer;
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
        return PersistentData.getInstance().getPlayersPowerRecord(getUniqueId()).getPowerLevel();
    }

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        PersistentData.getInstance().getPlayersPowerRecord(getUniqueId()).setPowerLevel((int) power);
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
        else return (String) unsupported("MedievalFactions", "getTitle()");
    }

    /**
     * Method to set the title of the FPlayer.
     *
     * @param title to set.
     */
    @Override
    public void setTitle(@NotNull String title) {
        if (bridge.catch_exceptions) return;
        unsupported("MedievalFactions", "setTitle(title)");
    }

    /**
     * Method to get the Role of the FPlayer.
     *
     * @return {@link Role}
     */
    @NotNull
    @Override
    public Role getRole() {
        final AbstractFaction<?> faction = (AbstractFaction<?>) getFaction();
        if (faction == null) return Role.FACTIONLESS;
        if (faction.getLeader() == this) return Role.LEADER;
        final dansplugins.factionsystem.objects.Faction f =
                (dansplugins.factionsystem.objects.Faction) faction.getFaction();
        if (f.isOfficer(getUniqueId())) return Role.OFFICER;
        if (f.isMember(getUniqueId())) return Role.NORMAL;
        return Role.CUSTOM;
    }

}
