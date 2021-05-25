package fr.onyxworld.espiiii.questionEvent.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.onyxworld.espiiii.questionEvent.Main;

public class Question 
{
	private static String question;
	private static String answer;
	public static Object statut;
	public static ArrayList<Player> participatedPlayerList = null;
	
	public Question(String question, String answer, Main main, boolean command)
	{

		if(command) 
		{
		this.question = question;
		this.answer = answer;
		} else
		{
			participatedPlayerList = new ArrayList<Player>();
			List<String> questionConfig = main.getConfig().getStringList("message.questions");
			List<String> answerConfig = main.getConfig().getStringList("message.answer");
			
			int number = randomNumber(0, questionConfig.size());
			String questionRandom = questionConfig.get(number);
			String answerRandom = answerConfig.get(number);
			
			String broadcast = main.getConfig().getString("message.event.broadcastMessage");
			broadcast = broadcast.replaceAll("%question%", questionRandom);
			broadcast = broadcast.replaceAll("%answer%", answerRandom);
			broadcast = broadcast.replace("&", "§").replaceAll("%prefix%", main.prefix);
			 
			Bukkit.broadcastMessage(broadcast);
			statut = number;
			Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				
				@Override
				public void run() {
					
					// Si personne n'a encore trouvé
					if(statut != null) 
					{
						statut = null;
						
						// Si il y a un message dans le fichier config.yml
						if(!(main.getConfig().getString("message.event.noBodyFoundEventAnswer").equalsIgnoreCase("")))
						{
							String terminateEventMessage = main.getConfig().getString("message.event.noBodyFoundEventAnswer");
							terminateEventMessage = terminateEventMessage.replaceAll("%question%", questionRandom).replaceAll("%answer%", answerRandom).replace("&", "§").replaceAll("%prefix%", main.prefix);
							participatedPlayerList = null;
							Bukkit.broadcastMessage(terminateEventMessage);
						}
					}
				}
			}, main.getConfig().getInt("time.responseDelay")*60*20L);
			
			
			
			
			
			
		}
		
				
	}
	
	// Test la réponse du joueur. Lui donne une récompense si réponse juste.
	public static void checkAnswer(String question, Player player, String answer, String playerAnswer, Main main)
	{
		// Si la réponse est correct
		if(playerAnswer.equalsIgnoreCase(answer))
		{
			// Liste des récompenses
			List<String> offers = main.getConfig().getStringList("offers");
			
			// Nombre aléatoire pour choisir un cadeau
			int a = randomNumber(0, offers.size());
			String offersRandom = offers.get(a).replaceAll("%player%", player.getDisplayName());
			
			// Envoi de la commande
			Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() 
			{
				
				@Override
				public void run() 
				{
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), offersRandom);
				}
			},0);
			
			if(!(main.getConfig().getString("message.event.aBodyFoundEventAnswer").equalsIgnoreCase("")))
			{
				String terminateEventMessage = main.getConfig().getString("message.event.aBodyFoundEventAnswer");
				terminateEventMessage = terminateEventMessage.replaceAll("%player%", player.getDisplayName()).replaceAll("%qestion%", question).replaceAll("%answer%", answer).replaceAll("%prefix%", main.prefix);
			}
			statut = null;
			participatedPlayerList = null;
			// MEssage de win
			String winMessage = main.getConfig().getString("message.event.winMessage");
			winMessage = winMessage.replaceAll("%question%", question).replaceAll("%answer%", answer).replaceAll("%playerAnswer%", playerAnswer).replaceAll("%player%", player.getDisplayName()).replace("&", "§").replaceAll("%prefix%", main.prefix);
			player.sendMessage(winMessage);
			String brodcastWinMessage = main.getConfig().getString("message.event.aBodyFoundEventAnswer").replaceAll("%question%", question).replaceAll("%answer%", answer).replaceAll("%player%", player.getDisplayName()).replace("&", "§").replaceAll("%prefix%", main.prefix);
			Bukkit.broadcastMessage(brodcastWinMessage);
			
			
		}else
		{
			String looseMessage = main.getConfig().getString("message.event.looseMessage").replaceAll("%player%", player.getDisplayName()).replace("&", "§").replaceAll("%question%", question).replaceAll("%answer%", answer).replaceAll("%playerAnswer%", playerAnswer).replaceAll("%prefix%", main.prefix);
			
			player.sendMessage(looseMessage);
			participatedPlayerList.add(player);
		}
		
	}
	
	public static int randomNumber(int min, int max)
	{
		int randint = (int) (min + (Math.random() * (max - min)));
		return randint;
	}
	
}
