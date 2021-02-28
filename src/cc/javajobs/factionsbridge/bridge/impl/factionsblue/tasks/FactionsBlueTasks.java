package cc.javajobs.factionsbridge.bridge.impl.factionsblue.tasks;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.events.IClaimUnclaimAllEvent;
import cc.javajobs.factionsbridge.bridge.events.IClaimUnclaimEvent;
import cc.javajobs.factionsbridge.bridge.events.IFactionCreateEvent;
import cc.javajobs.factionsbridge.bridge.events.IFactionRenameEvent;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.HashSet;
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

    @Override
    public void run() {
        for (IFaction faction : FactionsBridge.getFactionsAPI().getAllFactions()) {
            Set<IClaim> currentClaims = new HashSet<>(faction.getAllClaims());
            Set<IClaim> oldClaims = claimCount.getOrDefault(faction, new HashSet<>());

            if (currentClaims.size() == oldClaims.size() && !claimCount.containsKey(faction)) {

                // create
                IFactionCreateEvent createEvent = new IFactionCreateEvent(
                        faction.getId(), faction.getLeader(), null
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
                }
            }

            claimCount.put(faction, currentClaims);
            nameChangeTrack.put(faction, faction.getName());

        }
    }

}
