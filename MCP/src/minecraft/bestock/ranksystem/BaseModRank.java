package bestock.ranksystem;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;



@Mod(modid = "bestockRankSystem", name = "bestock Rank System", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)

public class BaseModRank{

	@Instance("bestockRankSystem")
	public static BaseModRank instance;
	@SidedProxy(clientSide="bestock.ranksystem.ClientProxyTweaks", serverSide="bestock.ranksystem.CommonProxyTweaks")
	public static CommonProxyTweaks proxy;
	
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		//code run before mod load
		
	}

	@Init
	public void init(FMLInitializationEvent event) {
		//code run during load
		MinecraftForge.EVENT_BUS.register(new CommandEventHandler());
	}

	@PostInit
	public static void postInit(FMLPostInitializationEvent event) {
		//code run after load
		
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event)
	{
		ICommandManager commandManager = ModLoader.getMinecraftServerInstance().getCommandManager();
		ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager); 
		addCommands(serverCommandManager);
	}
	
	
	public void addCommands(ServerCommandManager manager)
	{
		manager.registerCommand(new CommandRankUp());		
		manager.registerCommand(new CommandSetRank());
		manager.registerCommand(new CommandGetRank());
	}
	
	
}
