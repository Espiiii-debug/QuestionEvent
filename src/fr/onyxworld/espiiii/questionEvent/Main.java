package fr.onyxworld.espiiii.questionEvent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.onyxworld.espiiii.questionEvent.commands.QuestionCommands;
import fr.onyxworld.espiiii.questionEvent.event.Question;
import fr.onyxworld.espiiii.questionEvent.listeners.ChatEvent;

public class Main extends JavaPlugin
{
	public String prefix;
	
	// Plugin Start
		@Override
		public void onEnable()
		{
			saveDefaultConfig();
			System.out.println("------------------");
			System.out.println("| Question Event |");
			System.out.println("------------------");
			Main main = this;
			// INitialisation du shedules
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run()
				{
					 new Question(" ", " ", main, false);
				}
			},0, this.getConfig().getInt("time.loop") * 60 * 60 * 20L);	
			
			
			// Initialisation du prefix (config.yml)
			prefix = this.getConfig().getString("prefix");
			
			// Register command	
//			this.getCommand("question").setExecutor(new QuestionCommands(this));
			
			// Register Event
			Bukkit.getPluginManager().registerEvents(new ChatEvent(this), this);
			
			super.onEnable();
		}
		
		// ShutDOwn Server
		@Override
		public void onDisable() 
		{
		
			
			
			super.onDisable();
		}

}
