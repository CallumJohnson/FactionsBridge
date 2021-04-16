package cc.javajobs.factionsbridge.bridge.impl.factionsblue.tasks;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.events.*;
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

    private final HashMap<IFaction, Set<IClaim>> claimCount = new HashMap<>();
    private final HashMap<IFaction, String> nameChangeTrack = new HashMap<>();
    private final HashMap<IFaction, List<IFactionPlayer>> memberTrack = new HashMap<>();

    /**
     * Constructor to initialise {@link FactionsBlueTasks#claimCount}
     * and {@link FactionsBlueTasks#nameChangeTrack}.
     */
    public FactionsBlueTasks() {
        for (IFaction fac : FactionsBridge.getFactionsAPI().getAllFactions()) {
            claimCount.put(fac, new HashSet<>(fac.getAllClaims().size()));
            nameChangeTrack.put(fac, fac.getName());
            memberTrack.put(fac, fac.getMembers());
        }
    }

    @Override
    public void run() {
        for (IFaction faction : FactionsBridge.getFactionsAPI().getAllFactions()) {
            Set<IClaim> currentClaims = new HashSet<>(faction.getAllClaims());
            Set<IClaim> oldClaims = claimCount.getOrDefault(faction, new HashSet<>());

            if (currentClaims.size() == oldClaims.size() && !claimCount.containsKey(faction)) {

                // create
                IFactionCreateEvent createEvent = new IFactionCreateEvent(
                        faction, faction.getLeader(), null
                );
                Bukkit.getPluginManager().callEvent(createEvent);

            } else if (currentClaims.size() == 0 && oldClaims.size() > 1) {

                // unclaimall
                IClaimUnclaimAllEvent unclaimAllEvent = new IClaimUnclaimAllEvent(
                        faction, faction.getLeader(), null
                );
                Bukkit.getPluginManager().callEvent(unclaimAllEvent);

            } else if (currentClaims.size() < oldClaims.size()) {
                if (oldClaims.removeAll(currentClaims)) {
                    for (IClaim unclaimedClaim : oldClaims) {

                        // unclaim
                        IClaimUnclaimEvent unclaimEvent = new IClaimUnclaimEvent(
                                unclaimedClaim, faction, faction.getLeader(), null
                        );
                        Bukkit.getPluginManager().callEvent(unclaimEvent);

                    }
                }
            }

            if (nameChangeTrack.containsKey(faction)) {
                String oldName = nameChangeTrack.get(faction);
                if (!faction.getName().equalsIgnoreCase(oldName)) {
                    IFactionRenameEvent bridgeEvent = new IFactionRenameEvent(
                            faction,
                            faction.getName(),
                            null
                    );
                    Bukkit.getPluginManager().callEvent(bridgeEvent);
                }
            }

            if (memberTrack.containsKey(faction)) {
                List<IFactionPlayer> old = memberTrack.get(faction);
                List<IFactionPlayer> current = faction.getMembers();
                if (old.size() != current.size()) {
                    if (old.size() > current.size()) { // leave/kick
                        for (IFactionPlayer iFactionPlayer : old) {
                            if (!current.contains(iFactionPlayer)) {
                                IFactionPlayerLeaveIFactionEvent bridgeEvent = new IFactionPlayerLeaveIFactionEvent(
                                        faction,
                                        iFactionPlayer,
                                        IFactionPlayerLeaveIFactionEvent.LeaveReason.UNKNOWN,
                                        null
                                );
                                Bukkit.getPluginManager().callEvent(bridgeEvent);
                            }
                        }
                    } else { // join
                        for (IFactionPlayer iFactionPlayer : current) {
                            if (!old.contains(iFactionPlayer)) {
                                IFactionPlayerJoinIFactionEvent bridgeEvent = new IFactionPlayerJoinIFactionEvent(
                                        faction,
                                        iFactionPlayer,
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
