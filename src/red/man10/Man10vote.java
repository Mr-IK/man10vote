package red.man10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Man10vote extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(args.length == 0) {
			  p.sendMessage("=======§6§lmvote§r=======");
			  p.sendMessage(" /mvote ○○ ==> ○○に投票");
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
	             String l = list.get(a);
				  board.setShowPlayer(p);
				  int i =vote2.get(l)+1;
				  vote2.put(l, i);
				  board.addScore(a+1+": "+l, 1);
				  playerState.put(p.getUniqueId(), a+1);
				  p.sendMessage(prefix+"§2"+l+"§aに投票しました！");
	   	            String runcom = config1.getString("voteuse").replace("<player_name>", p.getName());
 	            	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
 	            	String comman = runcom;
 	            	Bukkit.dispatchCommand(console, comman);
				  return true;
			  } else if(args.length == 2) {
				  if(args[0].equalsIgnoreCase("admin")) {
				        if(!p.hasPermission("mvote.admin")){
				            p.sendMessage(prefix + "§4あなたにはOP用helpを見る権限がありません!");
				            return true;
				        }
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
				    		vote2.clear();
				    		playerState.clear();
				        	board.remove();
				        	list.clear();
				        	Bukkit.broadcastMessage(prefix+"§a投票ありがとうございました。<(_ _)>");
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
						  int l =list.size();
						  board.setScore(l+1+": "+args[1], 0);
						  vote2.put(args[1], 0);
						  list.add(args[1]);
				          p.sendMessage(prefix + "§a項目を追加しました。");
				         return true;
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
						  board.setScore(i+": "+args[count], 0);
					  }
					  for (Player player : Bukkit.getOnlinePlayers()) {
						  board.setShowPlayer(player); 
					  }
					  Bukkit.broadcastMessage(prefix+"§a§l投票が開始されました！=>§e§l"+args[1]);
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
	String prefix = "§6§l[§a§lM§f§la§d§ln§a§lvote§6§l]§r";
	Scoreboard board;
	Vote vote;
	@Override
	public void onEnable() {
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
   			  board.setScore(key+": "+config2.getString("vote.a."+key+".key"), config2.getInt("vote.a."+key+".value"));
            }
            for (String key : config2.getConfigurationSection("vote.player").getKeys(false)) {
            	UUID uuid = UUID.fromString(key);
            	playerState.put(uuid, config2.getInt("vote.player."+key));
            }
            config2.set("vote", null);
            vote.saveConfig();
            Bukkit.broadcastMessage(prefix+"§a§l投票が再び開始されました！=>§e§l"+config2.getString("vote.title"));
        }
	}

}
