package bestock.ranksystem;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class CommandRankUp extends CommandBase
{
    public String getCommandName()
    {
        return "rank-up";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/rank-up <player>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP target = func_82359_c(sender, args[0]);
    	NBTTagCompound tag = target.getEntityData();

    	NBTBase modeTag = tag.getTag("PlayerRank");
    	if(modeTag == null)
    	{
    		tag.setInteger("PlayerRank", 1);
    		target.sendChatToPlayer(sender.getCommandSenderName() + " given you a rank!" );
            sender.sendChatToPlayer(sender.getCommandSenderName() + " created a rank for " + target.getEntityName());
    	}
    	else
    	{
    		tag.setInteger("PlayerRank", tag.getInteger("PlayerRank") + 1); //increments ranke
    	target.sendChatToPlayer(sender.getCommandSenderName() + " has boosted your rank! You are now " + tag.getInteger("PlayerRank"));
        sender.sendChatToPlayer(sender.getCommandSenderName() + " used Rank Up on " + target.getEntityName());
    	}
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getPlayers()) : null;
    }

    protected String[] getPlayers()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(int par1)
    {
        return par1 == 0;
    }
}
