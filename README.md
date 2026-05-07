


# FactionsBridge

A Plugin which Bridges the gap between multiple APIs and Plugins which use them.

Due to the nature of Factions and their individualised implementations, it can be difficult to implement Factions into a Project without having to make a multitude of methods to support them all or choosing a specific branch to exclusively support. This project is being developed to hopefully mitigate this issue and create a unified interface, which any developer can depend, use and rely upon to make the distance for them when handling different Factions APIs.

### JavaDocs

To view the JavaDocs, visit the following website: <redacted>

### Plugins which are supported.

As of Version 1.4.2, 18 (two major versions of FactionsUUID and KingdomsX) different versions of Factions are supported.
- FactionsX
- FactionsUUID (including v4!)
- VulcanFactions
- SavageFactions (Abandoned)
- SaberFactions
- SupremeFactions
- Atlas Factions
- FactionsBlue
- MassiveFactions
- KingdomsX
- MedievalFactions
- LegacyFactions
- UltimateFactions
- Towny
- Factions (Kore Factions)
- ImprovedFactions
- Lands

## How to use the API

To begin, you need to remove Factions from your dependencies, this will hopefully be a full replacement and if it is not, please create an issue/contact me to get your desired functionality added into this project.
Added FactionsBridge into your plugin.yml dependencies to ensure that your plugin is hooked correctly.
```YML
name: YourProject
main: com.yourname.yourproject.YourProject
version: 1.0-Snapshot-of-the-century

# If you are shading the project in, include the following:
softdepend: [Factions, FactionsX, FactionsBlue, KingdomsX, Kingdoms, LegacyFactions, MedievalFactions, UltimateFactions, Towny, ImprovedFactions]

# If you are depending on the standalone plugin, including the following:
softdepend/depend: [FactionsBridge] # depending on your preference or requirements.
```

If you are shading the project into your plugin, please make sure to 'connect' to the FactionsBridge class *before* you attempt to use the API.
**Please make sure to relocate the Shaded Jar, if you don't conflicts will occur with Spigot**
#### How do I connect for Shading?
```JAVA
FactionsBridge bridge = new FactionsBridge();
// 	This will create all methodology using the Plugin Object you provide.
bridge.connect(yourPluginObject); // 1.0.0->Present.

// If you do not require factions but you want console output, use these parameters.
// The catch exceptions parameter is a completely new variable which should be used when you
// want to develop against FactionsBridge without worrying about the try/catch blocks. 
bridge.connect(yourPluginObject, true,          false           , true           ); 
// ->> connect(plugin,           consoleOutput, requiresFactions, catchExceptions)
/*
	FactionsBridge will bridge the APIs and then allow you to use the following 
	methods for access to the API methods.
*/
```
#### How do I get access to the API?
```JAVA
// 	This is how to obtain the API.
FactionsAPI api = FactionsBridge.getFactionsAPI(); 
/* 
	The API class has many methods which will hopefully add all the 
	functionality you could ever require.
 */
```

There are also events which are bridged, these events are listened to within FactionsBridge and then ported into the API so you can use them at the same time.
```YML
FactionClaimEvent            		# When a Faction claims land. (1.2.0)
FactionUnclaimAllEvent       		# When a Faction unclaims all their land. (1.2.0)
FactionUnclaimEvent          		# When a Faction unclaims land. (1.2.0)
FactionCreateEvent         		# When a Faction is created. (1.2.0)
FactionDisbandEvent        		# When a Faction is disbanded. (1.2.0)
FactionRenameEvent         		# When a Faction is renamed. (1.2.0)
FactionLeaveEvent			# When a Player Leaves or is Kicked from a Faction. (1.2.0)
FactionJoinEvent			# When a Player Joins a Faction. (1.2.0)
```


## side-note:
This API is very unstable and is a very basic implementation of the concept, updates will come to increase stability and fix issues if they arise.
The standalone plugin (FactionsBridge/BridgePlugin) uses bStats to track implementations. See more here: https://bstats.org/plugin/bukkit/FactionsBridge/11893

## Contact Me
```JAVA
Discord: `pondinq`
```
