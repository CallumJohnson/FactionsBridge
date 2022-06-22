```
Player player = Bukkit.getPlayer("Authorises");
Faction faction = api.getFaction(player);
for(FPlayer factionPlayer : faction.getOnlineMembers()){
Player loopPlayer = factionPlayer.getPlayer();
  loopPlayer.sendMessage("hi");
}
```

Get a list of all the online fplayers and convert them to players.
