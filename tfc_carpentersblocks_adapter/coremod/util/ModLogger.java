package tfc_carpentersblocks_adapter.coremod.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class ModLogger
{

	private static Logger logger = Logger.getLogger("TFC_CarpentersBlocks_adapter_coremod");

	public static void init()
	{
		logger.setParent(FMLLog.getLogger());
	}

	public static void log(Level level, String message)
	{
		logger.log(level, message);
	}

}