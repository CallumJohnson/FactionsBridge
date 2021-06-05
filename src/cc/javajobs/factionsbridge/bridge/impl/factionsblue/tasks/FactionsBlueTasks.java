package cc.javajobs.factionsbridge.bridge.impl.factionsblue.tasks;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Due to the nature of FactionsBlue, this class should hopefully add functionality to the plugin.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 09:37
 */
public class FactionsBlueTasks implements Runnable {

    private final HashMap<Faction, Set<Claim>> claimCount = new HashMap<>();
    private final HashMap<Faction, String> nameChangeTrack = new HashMap<>();
    private final HashMap<Faction, List<FPlayer>> memberTrack = new HashMap<>();

    public FactionsBlueTasks() {
        for (Faction fac : FactionsBridge.getFactionsAPI().getAllFactions()) {
            claimCount.put(fac, new HashSet<>(fac.getAllClaims().size()));
            nameChangeTrack.put(fac, fac.getName());
            memberTrack.put(fac, fac.getMembers());
        }
    }

    @Override
    public void run() {
        for (Faction faction : FactionsBridge.getFactionsAPI().getAllFactions()) {
            Set<Claim> currentClaims = new HashSet<>(faction.getAllClaims());
            Set<Claim> oldClaims = claimCount.getOrDefault(faction, new HashSet<>());

            if (currentClaims.size() == oldClaims.size() && !claimCount.containsKey(faction)) {

                // create
                FactionCreateEvent createEvent = new FactionCreateEvent(
                        faction, faction.getLeader(), null
                );
                Bukkit.getPluginManager().callEvent(createEvent);

            } else if (currentClaims.size() == 0 && oldClaims.size() > 1) {

                // unclaimall
                FactionUnclaimAllEvent unclaimAllEvent = new FactionUnclaimAllEvent(
                        faction, faction.getLeader(), null
                );
                Bukkit.getPluginManager().callEvent(unclaimAllEvent);

            } else if (currentClaims.size() < oldClaims.size()) {
                if (oldClaims.removeAll(currentClaims)) {
                    for (Claim unclaimedClaim : oldClaims) {

                        // unclaim
                        FactionUnclaimEvent unclaimEvent = new FactionUnclaimEvent(
                                unclaimedClaim, faction, faction.getLeader(), null
                        );
                        Bukkit.getPluginManager().callEvent(unclaimEvent);

                    }
                }
            }

            if (nameChangeTrack.containsKey(faction)) {
                String oldName = nameChangeTrack.get(faction);
                if (!faction.getName().equalsIgnoreCase(oldName)) {
                    FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                            faction,
                            faction.getName(),
                            null
                    );
                    Bukkit.getPluginManager().callEvent(bridgeEvent);
                }
            }

            if (memberTrack.containsKey(faction)) {
                List<FPlayer> old = memberTrack.get(faction);
                List<FPlayer> current = faction.getMembers();
                if (old.size() != current.size()) {
                    if (old.size() > current.size()) { // leave/kick
                        for (FPlayer fpl : old) {
                            if (!current.contains(fpl)) {
                                FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                                        faction,
                                        fpl,
                                        FactionLeaveEvent.LeaveReason.UNKNOWN,
                                        null
                                );
                                Bukkit.getPluginManager().callEvent(bridgeEvent);
                            }
                        }
                    } else { // join
                        for (FPlayer fpl : current) {
                            if (!old.contains(fpl)) {
                                FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                                        faction,
                                        fpl,
                                        null
                                );
                                Bukkit.getPluginManager().callEvent(bridgeEvent);
                            }
                        }
                    }
                }
            }

            claimCount.put(faction, currentClaims);
            nameChangeTrack.put(faction, faction.getName());

        }
    }


}
