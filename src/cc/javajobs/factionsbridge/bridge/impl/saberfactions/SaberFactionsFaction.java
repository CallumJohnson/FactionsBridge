package cc.javajobs.factionsbridge.bridge.impl.saberfactions;

import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import com.massivecraft.factions.Faction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class SaberFactionsFaction extends FactionsUUIDFaction {

    /**
     * Constructor to create a SaberFactionsFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public SaberFactionsFaction(@NotNull Faction faction) {
        super(faction);
    }

    /**
     * Method to obtain the Leader of the Faction.
     * <p>
     * Due to the nature of some of the implementations I will support, this can be {@code null}.
     * </p>
     *
     * @return {@link FPlayer} or {@code null}.
     */
    @Nullable
    @Override
    public FPlayer getLeader() {
        try {
            Class<?> factionClass = faction.getClass();
            Method getLeader = factionClass.getMethod("getFPlayerLeader");
            com.massivecraft.factions.FPlayer leader = (com.massivecraft.factions.FPlayer) getLeader.invoke(faction);
            return new SaberFactionsFPlayer(leader);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return null;
            return (FPlayer) methodError(getClass(), "getLeader()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List} of {@link Claim} related to the Faction.
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return faction.getAllClaims().stream().map(SaberFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List} of {@link FPlayer} related to the Faction.
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getFPlayers().stream().map(SaberFactionsFPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to get the points of the Faction.
     *
     * @return points of the Faction.
     */
    @Override
    public int getPoints() {
        try {
            Class<?> factionClass = faction.getClass();
            Method getPoints = factionClass.getMethod("getPoints");
            return (int) getPoints.invoke(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0;
            return (int) methodError(getClass(), "getPoints()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to override the points of the Faction to the specified amount.
     *
     * @param points to set for the Faction.
     * @see #getPoints()
     */
    @Override
    public void setPoints(int points) {
        try {
            Class<?> factionClass = faction.getClass();
            Method setPoints = factionClass.getMethod("setPoints", Integer.TYPE);
            setPoints.invoke(faction, points);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "setPoints(int points)", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to obtain the Bank Balance of the Faction.
     * <p>
     *     Thanks to Driftay for the note of the correct API usage here.
     * </p>
     *
     * @return bank balance in the form of {@link Double}.
     */
    @Override
    public double getBank() {
        try {
            Method getBalance = faction.getClass().getMethod("getFactionBalance");
            return (double) getBalance.invoke(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0.0;
            return (double) methodError(getClass(), "getBank()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to clear the Strikes related to the Faction
     */
    @Override
    public void clearStrikes() {
        setStrikes(0);
    }

    /**
     * Method to add a Strike to the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void addStrike(String sender, String reason) {
        setStrikes(getTotalStrikes()+1);
    }

    /**
     * Method to remove a Strike from the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void removeStrike(String sender, String reason) {
        setStrikes(getTotalStrikes()-1);
    }

    /**
     * Method to set the strikes for the given Faction.
     *
     * @param count to set.
     * @throws IllegalStateException if count is less than 0.
     */
    private void setStrikes(int count) {
        if (count < 0) throw new IllegalStateException("Count cannot be below 0.");
        try {
            Method set = faction.getClass().getMethod("setStrikes", Integer.TYPE);
            set.invoke(faction, count);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "addStrike(Sender, String)", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to obtain the total Strikes related to a Faction.
     *
     * @return total strikes.
     */
    @Override
    public int getTotalStrikes() {
        try {
            Method get = faction.getClass().getMethod("getStrikes");
            return (int) get.invoke(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0;
            return (int) methodError(getClass(), "getTotalStrikes()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to obtain the Provider name for Debugging/Console output purposes.
     *
     * @return String name of the Provider.
     */
    @NotNull
    @Override
    public String getProvider() {
        return "SaberFactions";
    }

}
