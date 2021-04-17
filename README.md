
# FactionsBridge

A Plugin which Bridges the gap between multiple APIs and Plugins which use them.

Due to the nature of Factions and their individualised implementations, it can be difficult to implement Factions into a Project without having to make a multitude of methods to support them all or choosing a specific branch to exclusively support. This project is being developed to hopefully mitigate this issue and create a unified interface, which any developer can depend, use and rely upon to make the distance for them when handling different Factions APIs.

### Plugins which are supported.

As of Version 1.0.0, 9 different versions of Factions are supported.
| Plugin | Download Link |
| ------ | ------ |
| FactionsX | https://www.spigotmc.org/resources/factionsx.83459/ |
| FactionsUUID | https://www.spigotmc.org/resources/factionsuuid.1035/ |
| SavageFactions (Abandoned) | https://www.spigotmc.org/resources/savagefactions-the-ultimate-factions-experience-abandoned.52891/ |
| SaberFactions | https://www.spigotmc.org/resources/saberfactions-1-8-1-16-x-discord-saber-pw-the-complete-factions-solution.69771/ |
| SupremeFactions | https://docs.supremedev.us/supreme-dev-supreme-factions |
| Atlas Factions | https://elapsed.dev/ | 
| FactionsBlue | https://www.spigotmc.org/resources/factions-blue.66627/ | 
| MassiveFactions | https://www.spigotmc.org/resources/factions.1900/ |
| KingdomsX | https://www.spigotmc.org/resources/kingdomsx.77670/ |

## How to use the API

To begin, you need to remove Factions from your dependancies, this will hopefully be a full replacement and if it is not, please create an issue/contact me to get your desired functionality added into this project.
Added FactionsBridge into your plugin.yml dependancies to ensure that your plugin is hooked correctly.
```YML
name: YourProject
main: com.yourname.yourproject.YourProject
version: 1.0-Snapshot-of-the-century

# If you are shading the project in, include the following:
softdepend: [Factions, FactionsX, FactionsBlue, KingdomsX, Kingdoms]

# If you are depending on the standalone plugin, including the following:
softdepend/depend: [FactionsBridge] # depending on your preference or requirements.
```

If you are shading the project into your plugin, please make sure to 'connect' to the FactionsBridge class *before* you attempt to use the API.
#### How do I connect for Shading?
```JAVA
// 	This will create all methodology using the Plugin Object you provide.
new FactionsBridge().connect(yourPluginObject);
/*
	FactionsBridge will bridge the APIs and then allow you to use the following 
	methods for access to the API methods.
*/
```
#### How do I get access to the API?
```JAVA
// 	This is how to obtain the API.
IFactionAPI api = FactionsBridge.getFactionAPI(); 
/* 
	The API class has many methods which will hopefully add all the 
	functionality you could ever require.
 */
```

There are also events which are bridged, these events are listened to within FactionsBridge and then ported into the API so you can use them at the same time.
```YML
IClaimClaimEvent            		# When a Faction claims land. (1.0)
IClaimUnclaimAllEvent       		# When a Faction unclaims all their land. (1.0)
IClaimUnclaimEvent          		# When a Faction unclaims land. (1.0)
IFactionCreateEvent         		# When a Faction is created. (1.0)
IFactionDisbandEvent        		# When a Faction is disbanded. (1.0)
IFactionRenameEvent         		# When a Faction is renamed. (1.0)
IFactionPlayerLeaveIFactionEvent	# When a Player Leaves or is Kicked from a Faction. (1.0.2)
IFactionPlayerJoinIFactionEvent		# When a Player Joins a Faction. (1.0.2)
```


## side-note:
This API is very unstable and is a very basic implementation of the concept, updates will come to increase stability and fix issues if they arise.
