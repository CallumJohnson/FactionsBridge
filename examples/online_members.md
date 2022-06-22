```
Player p = Bukkit.getPlayer("Authorises");
Faction f = api.getFaction(p);
for(FPlayer fp : f.getOnlineMembers()){
Player lp = fp.getPlayer();
  lp.sendMessage("hi");
}
```

Simply get a list of all the online fplayers and convert them to players.
