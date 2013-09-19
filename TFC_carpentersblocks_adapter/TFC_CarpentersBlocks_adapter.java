package TFC_carpentersblocks_adapter;

//import TFC.Handlers.PacketHandler;
import java.util.logging.Level;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = Reference.ModID, name = Reference.ModName, version = Reference.ModVersion, dependencies = Reference.ModDependencies)
//channels = { Reference.ModChannel }, 
@NetworkMod( clientSideRequired = true, serverSideRequired = true)
public class TFC_CarpentersBlocks_adapter {
	
	@Instance("TFC_CarpentersBlocks_adapter")
	public static TFC_CarpentersBlocks_adapter instance;
	
	public TFC_CarpentersBlocks_adapter(){
		
	}
//	@SidedProxy(clientSide = "carpentersblocks.proxy.ClientProxy", serverSide = "carpentersblocks.proxy.CommonProxy")
//	public static CommonProxy proxy;
	
//	public static CreativeTabs tabCarpentersBlocks = new CarpentersBlocksTab("carpentersBlocks");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModLogger.log(Level.INFO, "Pre-Initializing Carpenter's Blocks adapter for TFC");
		instance = this;
//		FeatureHandler.initProps(event);
//		BlockHandler.initBlocks(event);
//		ItemHandler.initItems(event);

//		ModLogger.init();

//		proxy.registerHandlers(event);
//		proxy.registerRenderInformation(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
//		cpw.mods.fml.common.registry.
//		Util.listItems();
//		Util.listBlocks();
		ModLogger.log(Level.INFO, "Initializing Carpenter's Blocks adapter for TFC");
//		FeatureHandler.registerTileEntities();
		TFC_carpentersblocks_adapter.BlockHandler.changeBlockRecipes();
		TFC_carpentersblocks_adapter.ItemHandler.changeItemRecipes();
	}
}
