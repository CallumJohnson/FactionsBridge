package lands;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import me.angeschossen.lands.api.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Lands implementation of {@link FPlayer}.
 * Similarly to the plugin itself, {@link LandsFPlayer} utilises {@link OfflinePlayer}.
 */
public class LandsFPlayer extends AbstractFPlayer<OfflinePlayer> {

    /**
     * Constructor to create an AbstractFPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public LandsFPlayer(@NotNull OfflinePlayer fPlayer) {
        super(fPlayer);
        new LandsFactionsAPI();
    }

    /**
     * Method to get the UUID of the FPlayer.
     *
     * @return {@link UUID} originated from Minecraft/Mojang.
     */
    @NotNull
    @Override
    public UUID getUniqueId() {
        return fPlayer.getUniqueId();
    }

    /**
     * Method to get the Name of the FPlayer.
     *
     * @return name of the player.
     */
    @NotNull
    @Override
    public String getName() {
        return Objects.requireNonNull(fPlayer.getName());
    }

    /**
     * Method to get the {@link Faction} relative to the FPlayer.
     * <p>
     * This method can return null, please use {@link #hasFaction()} to ensure the safest practice.
     * </p>
     *
     * @return {@link Faction} or {@code null}.
     * @see #hasFaction()
     */
    @Nullable
    @Override
    public Faction getFaction() {
        final FactionsAPI api = new LandsFactionsAPI();
        return api.getFaction(fPlayer);
    }

    /**
     * Method to determine if the FPlayer has a Faction
     *
     * @return {@code true} if they do.
     * @see #getFaction()
     */
    @Override
    public boolean hasFaction() {
        return getFaction() != null;
    }

    /**
     * Method to get the OfflinePlayer related to the FPlayer.
     *
     * @return {@link OfflinePlayer} from the Bukkit API.
     */
    @NotNull
    @Override
    public OfflinePlayer getOfflinePlayer() {
        return fPlayer;
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
        return Bukkit.getPlayer(getUniqueId());
    }

    /**
     * Method to determine if the player is online or not.
     *
     * @return {@code true} if they are connected to the server.
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
        return (double) unsupported("Lands", "getPower()");
    }

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        if (bridge.catch_exceptions) return;
        unsupported("Lands", "setPower(power)");
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
        return (String) unsupported("Lands", "getTitle()");
    }

    /**
     * Method to set the title of the FPlayer.
     *
     * @param title to set.
     */
    @Override
    public void setTitle(@NotNull String title) {
        if (bridge.catch_exceptions) return;
        unsupported("Lands", "setTitle(title)");
    }

    /**
     * Method to get the Role of the FPlayer.
     *
     * @return {@link Role}
     */
    @NotNull
    @Override
    public Role getRole() {
        final Faction playerFaction = getFaction();
        if (!hasFaction() || playerFaction == null) return Role.FACTIONLESS;
        final FPlayer leader = playerFaction.getLeader();
        boolean isOwner = leader != null && leader.getUniqueId() == getUniqueId();
        if (isOwner) {
            return Role.LEADER;
        }
        final AbstractFaction<?> faction = (AbstractFaction<?>) playerFaction;
        final Nation nation = (Nation) faction.getFaction();
        if (nation.getTrustedPlayers().contains(getUniqueId())) {
            return Role.NORMAL;
        }
        return Role.FACTIONLESS;
    }

}
