package cc.javajobs.factionsbridge.bridge.impl.factionsuuid;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.data.MemoryFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.UUID;

public class FactionsUUIDFPlayer extends AbstractFPlayer<FPlayer> {

    /**
     * Constructor to create a FactionsUUIDFPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public FactionsUUIDFPlayer(@NotNull FPlayer fPlayer) {
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
        try {
            final Class<?> FPLAYER_CLASS = fPlayer.getClass();
            final Method getOfflinePlayer = FPLAYER_CLASS.getMethod("getOfflinePlayer");
            final OfflinePlayer player = (OfflinePlayer) getOfflinePlayer.invoke(fPlayer);
            return player.getUniqueId();
        } catch (Exception ex) {
            return UUID.fromString(fPlayer.getId());
        }
    }

    /**
     * Method to get the Name of the FPlayer.
     *
     * @return name of the player.
     */
    @NotNull
    @Override
    public String getName() {
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
    @Nullable
    @Override
    public Faction getFaction() {
        return new FactionsUUIDFaction(fPlayer.getFaction());
    }

    /**
     * Method to determine if the FPlayer has a Faction
     *
     * @return {@code true} if they do.
     * @see #getFaction()
     */
    @Override
    public boolean hasFaction() {
        return fPlayer.hasFaction() && getFaction() != null;
    }

    /**
     * Method to get the OfflinePlayer related to the FPlayer.
     *
     * @return {@link OfflinePlayer} from the Bukkit API.
     */
    @NotNull
    @Override
    public OfflinePlayer getOfflinePlayer() {
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
    @Nullable
    @Override
    public Player getPlayer() {
        return fPlayer.getPlayer();
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
        return fPlayer.getPower();
    }

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        fPlayer.alterPower(Math.abs(fPlayer.getPower()-power));
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
        fPlayer.setTitle(Bukkit.getConsoleSender(), title);
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
        try {
            Class.forName("com.massivecraft.factions.perms.Role");
            Class.forName("com.massivecraft.factions.data.MemoryFPlayer");
            final MemoryFPlayer fpl = (MemoryFPlayer) fPlayer;
            return Role.getRole(fpl.getRole().name());
        } catch (ClassNotFoundException tier1) {
            try {
                Class.forName("com.massivecraft.factions.struct.Role");
                Class.forName("com.massivecraft.factions.zcore.persist.MemoryFPlayer");
                final com.massivecraft.factions.zcore.persist.MemoryFPlayer fpl =
                        (com.massivecraft.factions.zcore.persist.MemoryFPlayer) this.fPlayer;
                return Role.getRole(fpl.getRole().name());
            } catch (ReflectiveOperationException ex) {
                if (bridge.catch_exceptions) return Role.DEFAULT_ROLE;
                return (Role) methodError(getClass(), "getRole()", "Role unable to be resolved.");
            }
        }
    }

}
