package red.man10;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	                    if (playerState.get(p) != null && playerState.get(p).equalsIgnoreCase("done")) {
	                        p.sendMessage(prefix+"§4あなたはすでに投票しています！");
	                        return true;
	                    }
	              }
	              if(vote2.containsKey(args[0])==false) {
                      p.sendMessage(prefix+"§4その回答は存在しません！");
                      return true;
	              }
				  board.setShowPlayer(p);
				  int i =vote2.get(args[0])+1;
				  vote2.put(args[0], i);
				  board.addScore(args[0], 1);
				  playerState.put(p, "done");
				  p.sendMessage(prefix+"§a回答を送信しました！");
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
					     p.sendMessage(" /mvote start 投票内容 項目1 項目2 … ==> 投票を開始");
					     p.sendMessage(" /mvote admin end ==> 投票を終了");
					     p.sendMessage("=======§a§kaaa§6§l====v1.0.0====§a§kaaa§r=======");
					     return true;
				        } else if(args[1].equalsIgnoreCase("end")) {
				        	if(board.contain()==false) {
				        		p.sendMessage(prefix + "§4投票が実施されていません！");
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
				        	Bukkit.broadcastMessage(prefix+"§a投票ありがとうございました。<(_ _)>");
				        	return true;
				        }
				  }
			  } else if(args.length >= 4) {
				  if(args[0].equalsIgnoreCase("start")) {
				        if(!p.hasPermission("mvote.admin")){
				            p.sendMessage(prefix + "§4あなたには投票を開始する権限がありません！");
				            return true;
				        }
					  board = new Scoreboard();
					  board.setTitle(args[1]);
					  for (int count = 2; count < args.length; count++){
						  board.setScore(args[count], 0);
						  vote2.put(args[count], 0);
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
		reloadConfig();
		super.onDisable();
	}
	public static FileConfiguration config1;
	public static FileConfiguration config2;
	private HashMap<Player,String> playerState;
	private HashMap<String,Integer> vote2;
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
        getCommand("mvote").setExecutor(this);
		super.onEnable();
	}

}
