package fr.onyxworld.espiiii.questionEvent.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.onyxworld.espiiii.questionEvent.Main;


public class QuestionCommands implements CommandExecutor
{
	
	
	private Main main;
	
	public QuestionCommands(Main main) {this.main = main;}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		
		// Verification de l'executeur (console, joueur, etc...)
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			switch(args[0])
			{
			
			// Si la l'arguments est create
			case "create" :
				if(args.length != 1)
				{
					// Récuperation du prefix et du message d'erreur
					player.sendMessage(ChatColor.RED+ main.prefix + main.getConfig().getString("message.error.argsMissing").replace("&", "§"));
				
				}
				break;
				
				// Si l'arguments est help
			case "help" :
				
				break;
			default:
					
			}
			
		} else
		{
			return true;
		}
		
		return true;
	}

	
	
}
