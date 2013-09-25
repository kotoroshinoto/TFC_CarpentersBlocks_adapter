package tfc_carpentersblocks_adapter.coremod;

//import TFC.Handlers.PacketHandler;
import java.util.Arrays;
import java.util.logging.Level;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import tfc_carpentersblocks_adapter.Reference;
import tfc_carpentersblocks_adapter.coremod.util.ModLogger;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.versioning.*;

//channels = { Reference.ModChannel }, 
@NetworkMod( clientSideRequired = true, serverSideRequired = true)
public class TFC_CarpentersBlocks_adapter_coremod extends DummyModContainer{
	
	@Instance("TFC_CarpentersBlocks_adapter_coremod")
	public static TFC_CarpentersBlocks_adapter_coremod instance;
	
	public TFC_CarpentersBlocks_adapter_coremod(){
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = Reference.ModID+"_coremod";
		meta.name = Reference.ModName+"[coremod]";
		meta.version = Reference.ModVersion;
		meta.credits = Reference.Credits;
		meta.authorList = Arrays.asList("KotoroShinoto");
		meta.description = Reference.ModDescription;
		meta.url = Reference.ModURL;
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}
//	@SidedProxy(clientSide = "carpentersblocks.proxy.ClientProxy", serverSide = "carpentersblocks.proxy.CommonProxy")
//	public static CommonProxy proxy;
	
//	public static CreativeTabs tabCarpentersBlocks = new CarpentersBlocksTab("carpentersBlocks");
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
	bus.register(this);
	return true;
	}
	@Subscribe
	public void modConstruction(FMLConstructionEvent evt){

	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent evt) {

	}
	
	@Subscribe
	public void preInit(FMLPreInitializationEvent event)
	{
		ModLogger.log(Level.INFO, "Pre-Initializing Carpenter's Blocks adapter for TFC [coremod]");
		instance = this;
//		FeatureHandler.initProps(event);
//		BlockHandler.initBlocks(event);
//		ItemHandler.initItems(event);

//		ModLogger.init();

//		proxy.registerHandlers(event);
//		proxy.registerRenderInformation(event);
	}
	
	@Subscribe
	public void init(FMLInitializationEvent event)
	{
//		cpw.mods.fml.common.registry.
//		Util.listItems();
//		Util.listBlocks();
		ModLogger.log(Level.INFO, "Initializing Carpenter's Blocks adapter for TFC [coremod]");
//		FeatureHandler.registerTileEntities();
	}
}
