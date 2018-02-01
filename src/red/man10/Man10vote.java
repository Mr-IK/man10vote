package red.man10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class Man10vote extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(args.length == 0) {
			  p.sendMessage("=======§a§kaaa§6§l===="+prefix+"====§a§kaaa§r=======");
			  if(vote2.isEmpty()==true) {
				  p.sendMessage(prefix + "§4現在投票は行われていません！");
			  }else {
				  p.sendMessage(prefix + "§a投票が行われています！ =>§e§l"+board.getTitle());
				  p.sendMessage(prefix + "§e/mvote ○○ ==> ○○に投票");
				  int l = list.size();
				  for (int count = 1; count <= l; count++){
					  int i = count-1;
					  String v = list.get(i);
					  int b = vote2.get(v);
					  p.sendMessage(prefix+count+": §a§l"+v+" §e§l"+b+"§a§l票");
				  }
			  }
			  p.sendMessage("=======§a§kaaa§6§l====v1.0.0====§a§kaaa§r=======");
			  return true;
			  } else if(args.length == 1) {
				  if(vote2.isEmpty()==true) {
					  p.sendMessage(prefix + "§4現在投票は行われていません！");
					  return true;
				  }
	              if (!playerState.isEmpty()) {
	                    if (playerState.get(p.getUniqueId()) != null && playerState.get(p.getUniqueId()).intValue()>=1) {
	                        p.sendMessage(prefix+"§4あなたはすでに投票しています！");
	                        return true;
	                    }
	              }
	              int a = 0;
	              try {
	              a = Integer.parseInt(args[0])-1;
				  }catch (NumberFormatException f){
				   p.sendMessage(prefix+"§4数字で入力してください。");
				   return true;
				  }
	              if(list.size()<a) {
                      p.sendMessage(prefix+"§4その回答は存在しません！");
                      return true;
	              }
	              Inventory inv = Bukkit.createInventory((InventoryHolder)null, 9, prefix);
				  ItemStack blackGlass = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte) 15);
				  inv.setItem(1, blackGlass);
	              inv.setItem(2, blackGlass);
	              inv.setItem(5, blackGlass);
	              inv.setItem(6, blackGlass);
	              inv.setItem(7, blackGlass);
					 ItemStack b = new ItemStack(Material.EMERALD_BLOCK);
					 ItemMeta o =b.getItemMeta();
					 o.setDisplayName("§a§l投票する");
					 b.setItemMeta(o);
					 inv.setItem(0, b);
					 ItemStack c = new ItemStack(Material.REDSTONE_BLOCK);
					 ItemMeta d =c.getItemMeta();
					 d.setDisplayName("§4§l考え直す");
					 c.setItemMeta(d);
					 inv.setItem(8, c);
					 ItemStack e = new ItemStack(Material.PAPER);
					 ItemMeta f =e.getItemMeta();
					 f.setDisplayName("§e§l"+list.get(a));
					 e.setItemMeta(f);
					 inv.setItem(4, e);
					 ItemStack g = new ItemStack(Material.COMPASS);
					 ItemMeta h =g.getItemMeta();
					 h.setDisplayName(a+1+"");
					 g.setItemMeta(h);
					 inv.setItem(3, g);
					 p.openInventory(inv);
					 p.sendMessage(prefix+"§a§l内容をご確認ください。");
					 return true;
			  } else if(args.length == 2) {
			        if(!p.hasPermission("mvote.admin")){
			            p.sendMessage(prefix + "§4あなたには権限がありません！");
			            return true;
			        }
				  if(args[0].equalsIgnoreCase("admin")) {
				        if(args[1].equalsIgnoreCase("help")) {
					     p.sendMessage("=======§6§lmvote§r=======");
					     p.sendMessage(" /mvote ○○ ==> ○○に投票");
					     p.sendMessage(" /mvote admin help ==> OP用ヘルプを確認(これ)");
					     p.sendMessage(" /mvote new 投票内容 項目1 項目2 … ==> 投票を開始");
					     p.sendMessage(" /mvote admin end ==> 投票を終了");
					     p.sendMessage("=======§a§kaaa§6§l====v1.0.0====§a§kaaa§r=======");
					     return true;
				        } else if(args[1].equalsIgnoreCase("end")) {
							  if(vote2.isEmpty()==true) {
								  p.sendMessage(prefix + "§4現在投票は行われていません！");
								  return true;
							  }
			    		    Bukkit.broadcastMessage(prefix+"§2§l結果発表！: §e§l"+board.getTitle());
			    		    int i = 0;
				    		for (String key : vote2.keySet()) {
				    			i = i+vote2.get(key);
				    			Bukkit.broadcastMessage(prefix+"§a"+key+": §e§l"+vote2.get(key)+"§a票");
				    		}
				    		Bukkit.broadcastMessage(prefix+"§2§l合計: §e§l"+i+"§2§l票");
							  for (Player player : Bukkit.getOnlinePlayers()) {
								  board.setMainScoreboard(player);
							  }
				        	Bukkit.broadcastMessage(prefix+"§a投票ありがとうございました。<(_ _)>");
							  int max = 0;
							  String maxs = null;
							  for (String key : vote2.keySet()) {
								  int ii = vote2.get(key);
								  if(max<ii) {
									  max = ii;
									  maxs = key;
								  }else if(max==ii) {
									  maxs = maxs+","+key;
								  }
							  }
							  if(maxs==null) {
								  maxs = "無し";
							  }
			   	            String runcom = config1.getString("voteend").replace("<win_name>", maxs);
			            	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			            	String comman = runcom;
			            	Bukkit.dispatchCommand(console, comman);
				    		vote2.clear();
				    		playerState.clear();
				        	board.remove();
				        	list.clear();
				        	return true;
				        }
				  }else if(args[0].equalsIgnoreCase("add")) {
				        if(!p.hasPermission("mvote.admin")){
				            p.sendMessage(prefix + "§4あなたには項目を追加する権限がありません！");
				            return true;
				        }
						  if(vote2.isEmpty()==true) {
							  p.sendMessage(prefix + "§4現在投票は行われていません！");
							  return true;
						  }
						  if(vote2.get(args[1])!=null) {
							  p.sendMessage(prefix + "§4同じ内容がすでにあったため追加できませんでした！");
						  }
						  vote2.put(args[1], 0);
						  list.add(args[1]);
						  int l =list.size();
						  int m =list.size();
						  for (int count = 1; count < l; count++){
							  String v = list.get(count-1);
							  int b = vote2.get(v);
							  board.setScore(count+": "+v+" §e"+b+"§a票", m);
							  m--;
						  }
						  board.setScore(list.size()+": "+args[1]+" §e"+0+"§a票", 1);
				          p.sendMessage(prefix + "§a項目を追加しました。");
				         return true;
				  }else if(args[0].equalsIgnoreCase("log")) {
		              int a = 0;
		              a = Integer.parseInt(args[1])-1;
					  if(vote2.isEmpty()==true) {
						  p.sendMessage(prefix + "§4現在投票は行われていません！");
						  return true;
					  }
		              if(list.size()-1<a) {
							 p.sendMessage(prefix + "§4その項目は存在しません！");
							 return true;
		              }
					  if(list.contains(list.get(a))==false) {
						 p.sendMessage(prefix + "§4その項目は存在しません！");
						 return true;
					  }
					  String b = list.get(a);
					  String c = "・";
					  p.sendMessage(prefix + "§e§l"+b+"§aに投票したリスト");
					  for (UUID key : playerState.keySet()) {
						  if(playerState.get(key).equals(a+1)) {
						  String d = getServer().getOfflinePlayer(key).getName();
						  c = c+" "+d;
						  if(c.length()<=3) {
							  p.sendMessage(prefix + c);
							  c = "・";
						  }
						  }else {
						  }
						  	  
					  }
					  if(c.equals("・")==false) {
						  p.sendMessage(prefix + c);
					  }
				  }
			  } else if(args.length >= 4) {
				  if(args[0].equalsIgnoreCase("new")) {
				        if(!p.hasPermission("mvote.admin")){
				            p.sendMessage(prefix + "§4あなたには投票を開始する権限がありません！");
				            return true;
				        }
					  board = new Scoreboard();
					  board.setTitle(args[1]);
					  int i = 0;
					  int l = args.length-2;
					  for (int count = 2; count < args.length; count++){
						  i++;
						  if(vote2.get(args[count])!=null) {
							  board.remove();
							  vote2.clear();
							  list.clear();
							  p.sendMessage(prefix + "§42つ以上の同じ内容があったため作成できませんでした！");
							  return true;
						  }
						  vote2.put(args[count], 0);
						  list.add(args[count]);
						  board.setScore(i+": "+args[count]+" §e0§a票", l);
						  l--;
					  }
					  for (Player player : Bukkit.getOnlinePlayers()) {
						  board.setShowPlayer(player); 
					  }
					  Bukkit.broadcastMessage(prefix+"§a§l投票が開始されました！=>§e§l"+args[1]+" §e§l/mvote");
					  return true;
				  }
			  }
		return true;
	}

	@Override
	public void onDisable() {
	    if(vote2.isEmpty()==true) {
		    config2.set("vote.enable", "false");
			vote.saveConfig();
			}else {
			config2.set("vote.enable", "true");
			config2.set("vote.title", board.getTitle());
			for (int i = 1; i <= list.size(); i++){
				String a = list.get(i-1);
				int b = vote2.get(a);
				config2.set("vote.a."+i+".key", a);
				config2.set("vote.a."+i+".value", b);
			}
			for (UUID key : playerState.keySet()) {
                config2.set("vote.player."+key, playerState.get(key));
			}
			vote.saveConfig();
			}
		reloadConfig();
		super.onDisable();
	}
	public static FileConfiguration config1;
	public static FileConfiguration config2;
	private HashMap<UUID,Integer> playerState;
	private HashMap<String,Integer> vote2;
	ArrayList<String> list;
	String prefix = "§6§l[§a§lM§f§la§d§ln§f§l10§a§lvote§6§l]§r";
	Scoreboard board;
	Vote vote;
	@Override
	public void onEnable() {
		new join(this);
	    saveDefaultConfig();
	    FileConfiguration config = getConfig();
        config1 = config;
        vote = new Vote(this, "vote.yml");
        vote.saveDefaultConfig();
        FileConfiguration config4 = vote.getConfig();
        config2 = config4;
        playerState = new HashMap<>();
        vote2 = new HashMap<>();
        list = new ArrayList<>();
        getCommand("mvote").setExecutor(this);
		super.onEnable();
        if(config2.getString("vote.enable").equals("true")==true) {
            board = new Scoreboard();
            board.setTitle(config2.getString("vote.title"));
            for (String key : config2.getConfigurationSection("vote.a").getKeys(false)) {
   			  vote2.put(config2.getString("vote.a."+key+".key"), config2.getInt("vote.a."+key+".value"));
   			  list.add(config2.getString("vote.a."+key+".key"));
   			  board.setScore(key+": "+config2.getString("vote.a."+key+".key"), list.size());
            }
            if(config2.contains("vote.player")==false) {
            	
            }else {
            for (String key : config2.getConfigurationSection("vote.player").getKeys(false)) {
            	UUID uuid = UUID.fromString(key);
            	playerState.put(uuid, config2.getInt("vote.player."+key));
            }
            }
            config2.set("vote", null);
            vote.saveConfig();
        }
	}
    public class join implements Listener{
    public join(Man10vote plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
		  if(vote2.isEmpty()==true) {
			  return;
		  }
          if (!playerState.isEmpty()) {
              if (playerState.get(p.getUniqueId()) != null && playerState.get(p.getUniqueId()).intValue()>=1) {
        		  board.setShowPlayer(p); 
                  return;
              }
          }
		  p.sendMessage(prefix+"§a§l投票が行われています！=>§e§l"+board.getTitle()+" §a§l/mvote");
		  board.setShowPlayer(p); 
		  return;
    } 
    @EventHandler
    public void OnClick(InventoryClickEvent e){
    	Player p = (Player)e.getWhoClicked();
    	if(e.getInventory().getLocation() != null) {
    		  return;
    		}
        if (e.getClickedInventory().getName().equals(prefix)==true) {
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){
                return;
            }
            if(e.getSlot()==0) {
            	e.setCancelled(true);
            	int a =Integer.parseInt(e.getInventory().getItem(3).getItemMeta().getDisplayName())-1;
	             String l = list.get(a);
				  board.setShowPlayer(p);
				  int i =vote2.get(l)+1;
				  int c= board.getpoint(a+1+": "+l+" §e"+vote2.get(l)+"§a票");
				  board.removeScores(a+1+": "+l+" §e"+vote2.get(l)+"§a票");
				  vote2.put(l, i);
				  board.setScore(a+1+": "+l+" §e"+vote2.get(l)+"§a票", c);
				  playerState.put(p.getUniqueId(), a+1);
				  p.sendMessage(prefix+"§2"+l+"§aに投票しました！");
	   	            String runcom = config1.getString("voteuse").replace("<player_name>", p.getName());
	            	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	            	String comman = runcom;
	            	Bukkit.dispatchCommand(console, comman);
	            	e.getInventory().setItem(4, null);
	            	p.closeInventory();
            }
            if(e.getSlot()==8) {
            	p.closeInventory();
            }
            e.setCancelled(true);
            return;
        }
    }
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
    	Player p = (Player) e.getPlayer();
    	if(e.getInventory().getLocation() != null) {
  		  return;
  		}
      if (e.getInventory().getName().equals(prefix)==true) {
    	  if(e.getInventory().getItem(4)==null) {
    		  return;
    	  }
    	  p.sendMessage(prefix+"§4投票をキャンセルしました。");
    	  return;
      }
    }
    }
}
