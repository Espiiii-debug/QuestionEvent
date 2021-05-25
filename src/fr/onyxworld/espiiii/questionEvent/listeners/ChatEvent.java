package fr.onyxworld.espiiii.questionEvent.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.onyxworld.espiiii.questionEvent.Main;
import fr.onyxworld.espiiii.questionEvent.event.Question;

public class ChatEvent implements Listener 
{
	private Main main;
	public ChatEvent(Main main) {
		this.main = main;
	}

	@EventHandler
	public void PlayerChatEvent(AsyncPlayerChatEvent e)
	{
		if(Question.statut != null)
		{
			int statut = (Integer) Question.statut;
			ArrayList<Player> participatedPlayerList = Question.participatedPlayerList;
			
			// Si le joueur à deja participé :
			for (Player element : participatedPlayerList) {
				if(element == e.getPlayer()) return;
			}
			
			// Si le joueur a la permission
			if(!(e.getPlayer().hasPermission("questionEvent.sendAnswer"))) return;
			String answer = main.getConfig().getStringList("message.answer").get(statut);
			String question = main.getConfig().getStringList("message.questions").get(statut);
			Question.checkAnswer(question, e.getPlayer(), answer, e.getMessage(), main);
			e.setCancelled(true);
		}
		
	}
	
}
