package towny;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Towny implementation of {@link FPlayer}.
 * Object Target: {@link Resident}.
 *
 * @author Callum Johnson
 * @since 01/07/2021 - 10:15
 */
public class TownyFPlayer extends AbstractFPlayer<Resident> {

    /**
     * Constructor to create an AbstractFPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public TownyFPlayer(@NotNull Resident fPlayer) {
        super(fPlayer);
    }

    /**
     * Method to get the UUID of the FPlayer.
     *
     * @return {@link UUID} originated from Minecraft/Mojang.
     */
    @Override
    public @NotNull UUID getUniqueId() {
        return fPlayer.getUUID();
    }

    /**
     * Method to get the Name of the FPlayer.
     *
     * @return name of the player.
     */
    @Override
    public @NotNull String getName() {
        return fPlayer.getName();
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
    @Override
    public @Nullable Faction getFaction() {
        try {
            return new TownyFaction(fPlayer.getTown());
        } catch (TownyException ex) {
            if (bridge.catch_exceptions) return null;
            return (Faction) methodError(getClass(), "getFaction()", "TownyException encountered");
        }
    }

    /**
     * Method to determine if the FPlayer has a Faction
     *
     * @return {@code true} if they do.
     * @see #getFaction()
     */
    @Override
    public boolean hasFaction() {
        return fPlayer.hasTown();
    }

    /**
     * Method to get the OfflinePlayer related to the FPlayer.
     *
     * @return {@link OfflinePlayer} from the Bukkit API.
     */
    @Override
    public @NotNull OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(getUniqueId());
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
    @Override
    public @Nullable Player getPlayer() {
        return fPlayer.getPlayer();
    }

    /**
     * Method to determine if the player is online or not.
     *
     * @return {@code true} if they are connected to the server.
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
    @Override
    public @Nullable String getTitle() {
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
    @Override
    public @NotNull Role getRole() {
        if (!hasFaction()) return Role.FACTIONLESS;
        try {
            final Town town = fPlayer.getTown();
            if (town == null) throw new NotRegisteredException("Null Town");
            if (town.getMayor().getUUID().equals(getUniqueId())) return Role.getOwner();
            if (town.getRank("co-mayor").contains(fPlayer) || town.getRank("comayor").contains(fPlayer)) {
                return Role.CO_LEADER;
            }
            if (town.getRank("assistant").contains(fPlayer)) return Role.OFFICER;
            if (town.getRank("helper").contains(fPlayer)) return Role.NORMAL;
            return Role.RECRUIT;
        } catch (NotRegisteredException e) {
            if (bridge.catch_exceptions) return Role.FACTIONLESS;
            return (Role) methodError(getClass(), "getRole()", "NotRegisteredException detected.");
        }
    }

}
