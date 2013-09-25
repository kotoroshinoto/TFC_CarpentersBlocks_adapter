package tfc_carpentersblocks_adapter.coremod;

import java.util.Map;
import java.util.logging.Level;

import tfc_carpentersblocks_adapter.mod.util.ModLogger;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

public class TFC_CarpBlock_IFMLLoadingPlugin implements IFMLLoadingPlugin {
	public static boolean runtimeDeobf;
	@Override
	public String[] getLibraryRequestClass() {
		// TODO Auto-generated method stub
		ModLogger.log(Level.INFO, "running getLibraryRequestClass ");
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		// TODO Auto-generated method stub
		ModLogger.log(Level.INFO, "running getASMTransformerClass");
		return new String[]{TFC_CarpBlock_IClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		ModLogger.log(Level.INFO, "running getModContainerClass");
		return tfc_carpentersblocks_adapter.coremod.TFC_CarpentersBlocks_adapter_coremod.class.getName();
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		ModLogger.log(Level.INFO, "running getSetupClass");
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub
		ModLogger.log(Level.INFO, "running injectData");
		runtimeDeobf = (Boolean) data.get("runtimeDeobfuscationEnabled");
	}

}
