package TFC_carpentersblocks_adapter;

//import TFC.Handlers.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = Reference.ModID, name = Reference.ModName, version = Reference.ModVersion, dependencies = Reference.ModDependencies)
//channels = { Reference.ModChannel }, 
@NetworkMod( clientSideRequired = true, serverSideRequired = true)
public class TFC_CarpentersBlocks_adapter {

	public TFC_CarpentersBlocks_adapter() {

	}

}
