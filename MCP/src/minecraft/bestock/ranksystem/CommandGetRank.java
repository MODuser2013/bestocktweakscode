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

public class CommandGetRank extends CommandBase
{
    public String getCommandName()
    {
        return "get-rank";
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
        return "/get-rank <player>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP target = func_82359_c(sender, args[0]);
    	NBTTagCompound tag = target.getEntityData();

    	NBTBase modeTag = tag.getTag("PlayerRank");

    	if(modeTag == null)
    	{
    		//player has no rank
    		sender.sendChatToPlayer(target.getEntityName() + " has no rank set");
    	}
    	else
    	{
    		//player has rank, display to sender
    		sender.sendChatToPlayer(target.getEntityName() + " has a rank of " + tag.getInteger("PlayerRank"));
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
