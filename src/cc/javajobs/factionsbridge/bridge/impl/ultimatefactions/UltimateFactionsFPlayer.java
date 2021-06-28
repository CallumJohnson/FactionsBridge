package cc.javajobs.factionsbridge.bridge.impl.ultimatefactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class UltimateFactionsFPlayer extends AbstractFPlayer<OfflinePlayer> {

    /**
     * Constructor to create an AbstractFPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public UltimateFactionsFPlayer(@NotNull OfflinePlayer fPlayer) {
        super(fPlayer);
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
        return new UltimateFactionsFaction(FactionsSystem.getFactions().getFaction(getUniqueId()));
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
        return (double) unsupported("UltimateFactions", "getPower()");
    }

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        if (bridge.catch_exceptions) return;
        unsupported("UltimateFactions", "setPower(power)");
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
        return (String) unsupported("UltimateFactions", "getTitle()");
    }

    /**
     * Method to set the title of the FPlayer.
     *
     * @param title to set.
     */
    @Override
    public void setTitle(@NotNull String title) {
        if (bridge.catch_exceptions) return;
        unsupported("UltimateFactions", "setTitle(title)");
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
        final de.miinoo.factions.model.Faction f = (de.miinoo.factions.model.Faction) faction.getFaction();
        final Rank rankOfPlayer = f.getRankOfPlayer(fPlayer.getUniqueId());
        GUITags tag = null;
        for (GUITags value : GUITags.values()) {
            if (value.name().equals(rankOfPlayer.getName())) {
                tag = value;
                break;
            }
        }
        if (tag == null) {
            if (bridge.catch_exceptions) return Role.DEFAULT_ROLE;
            else return (Role) methodError(getClass(), "getRole()", "Failed to find Tag from Rank.");
        }
        return Role.getRole(tag.name().replaceAll("Rank_", "").toUpperCase());
    }

}
