package me.pagekite.glen3b.library.bukkit.command;

import java.util.ArrayList;
import java.util.List;

import me.pagekite.glen3b.library.bukkit.Utilities;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

/**
 * Represents a command that can be used via a base command.
 * TODO: TSender type argument (validates sender instance, such as players only command) and an Argument class and list.
 */
public abstract class SubCommand {

	/**
	 * Returns all values in {@code possibilities} that start with or equal (ignoring case) the string {@code argument}.
	 * This method eliminates possiblities that do not start with the argument.
	 * @param argument The argument in the command as typed so far. May be null.
	 * @param possibilities All possiblities for the argument, not accounting for the argument so far.
	 * @return All possiblities for the argument, accounting for the argument as typed so far.
	 */
	public static List<String> getTabCompletions(String argument, List<String> possibilities){
		if(possibilities == null){
			throw new IllegalArgumentException("Possibilities is null.");
		}
		
		String arg = argument == null ? "" : argument.trim().toLowerCase();
		ArrayList<String> retVal = new ArrayList<String>();
		for(String str : possibilities){
			if(str != null && (str.toLowerCase().startsWith(arg) || str.equalsIgnoreCase(arg))){
				retVal.add(str);
			}
		}
		
		return retVal;
	}
	
	/**
	 * Gets all possible tab completion arguments, given the arguments so far and the sender of the command.
	 * The first element of the {@code arguments} array will always be the alias of this {@code SubCommand} that is used in invokation.
	 * The default implementation of this method returns all online players that start with the argument so far.
	 * @param sender The requester of tab completion options.
	 * @param arguments The arguments passed to the command so far.
	 * @return A list of strings which are possibilities for the tab completion argument.
	 * @see SubCommand#getTabCompletions(List)
	 */
	public List<String> tabComplete(CommandSender sender, String[] arguments){
		// Get all online players
		List<String> players = Utilities.getOnlinePlayerNames();
		
		String argSoFar = arguments.length >= 2 ? arguments[1] : null;
		
		return SubCommand.getTabCompletions(argSoFar, players);
	}
	
	private List<String> _aliases;
	
	/**
	 * Creates a subcommand with the given aliases.
	 * @param aliases All command aliases, including the main alias as the first element.
	 */
	public SubCommand(String... aliases){
		if(aliases == null || aliases.length == 0){
			throw new IllegalArgumentException("There must be at least one alias.");
		}
		_aliases = Lists.newArrayList(aliases);
	}
	
	/**
	 * Gets a list of strings which act as aliases for this command.
	 * @return A {@code List<String>} instance of aliases, including the primary name (first element) of this command.
	 */
	public final List<String> getAliases(){
		return _aliases;
	}
	
	/**
	 * Gets the name of the command, also known as the primary alias.
	 * @return The primary alias of the command.
	 */
	public final String getName(){
		return _aliases.size() == 0 ? null : _aliases.get(0);
	}
	
	/**
	 * Gets a user-friendly, color-formatted string stating the usage of the command.<br/>
	 * <b>This method will eventually be replaced with a commands argument API, and this method will become final.</b>
	 * @return A string representing command usage, usually expressed as {@code mainAlias <arg1> <arg2> [arg3]} or {@code mainAlias <arg1> <arg2> <args...>}.
	 */
	public abstract String getUsage();
	
	/**
	 * Gets a description of the command.
	 * @return A description of the command to be displayed in the base command's help page.
	 */
	public abstract String getDescription();
	
	/**
	 * Execute this subcommand.
	 * @param sender The sender of this command.
	 * @param arguments The arguments, including the command name, that were passed to this command. Index 0 will always be the alias of the command that was used in execution.
	 */
	public abstract void execute(CommandSender sender, String[] arguments);
	
}
