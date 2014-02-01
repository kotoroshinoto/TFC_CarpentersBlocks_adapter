package tfc_carpentersblocks_adapter.mod.util;

import java.util.HashMap;

import TFC.TFCItems;
import TFC.API.Metal;
import net.minecraft.item.Item;
import TFC.API.Constant.Global;
public class TFC_ItemConvenience {
	public static Metal toolmetals[]=new Metal[9];;
	public static HashMap<Metal,Integer> TFC_MetalUses= new HashMap<Metal,Integer> ();
	public static HashMap<Metal,Item> TFC_HammerHeads = new HashMap<Metal,Item> ();
	public static HashMap<Metal,Item> TFC_ChiselHeads = new HashMap<Metal,Item> ();;
	public static void Initialize() {
		TFC_MetalUses.clear();
		TFC_HammerHeads.clear();
		TFC_ChiselHeads.clear();
		toolmetals[0]=Global.BISMUTHBRONZE;
		TFC_MetalUses.put(Global.BISMUTHBRONZE, TFC.TFCItems.BismuthBronzeUses);
		TFC_HammerHeads.put(Global.BISMUTHBRONZE, TFCItems.BismuthBronzeHammerHead);
		TFC_ChiselHeads.put(Global.BISMUTHBRONZE, TFC.TFCItems.BismuthBronzeChiselHead);
		toolmetals[1]=Global.BLACKBRONZE;
		TFC_MetalUses.put(Global.BLACKBRONZE, TFC.TFCItems.BlackBronzeUses);
		TFC_HammerHeads.put(Global.BLACKBRONZE, TFCItems.BlackBronzeHammerHead);
		TFC_ChiselHeads.put(Global.BLACKBRONZE, TFC.TFCItems.BlackBronzeChiselHead);
		toolmetals[2]=Global.BLACKSTEEL;
		TFC_MetalUses.put(Global.BLACKSTEEL, TFC.TFCItems.BlackSteelUses);
		TFC_HammerHeads.put(Global.BLACKSTEEL, TFCItems.BlackSteelHammerHead);
		TFC_ChiselHeads.put(Global.BLACKSTEEL, TFC.TFCItems.BlackSteelChiselHead);
		toolmetals[3]=Global.BLUESTEEL;
		TFC_MetalUses.put(Global.BLUESTEEL, TFC.TFCItems.BlueSteelUses);
		TFC_HammerHeads.put(Global.BLUESTEEL, TFCItems.BlueSteelHammerHead);
		TFC_ChiselHeads.put(Global.BLUESTEEL, TFC.TFCItems.BlueSteelChiselHead);
		toolmetals[4]=Global.BRONZE;
		TFC_MetalUses.put(Global.BRONZE, TFC.TFCItems.BronzeUses);
		TFC_HammerHeads.put(Global.BRONZE, TFCItems.BronzeHammerHead);
		TFC_ChiselHeads.put(Global.BRONZE, TFC.TFCItems.BronzeChiselHead);
		toolmetals[5]=Global.COPPER;
		TFC_MetalUses.put(Global.COPPER, TFC.TFCItems.CopperUses);
		TFC_HammerHeads.put(Global.COPPER, TFCItems.CopperHammerHead);
		TFC_ChiselHeads.put(Global.COPPER, TFC.TFCItems.CopperChiselHead);
		toolmetals[6]=Global.WROUGHTIRON;
		TFC_MetalUses.put(Global.WROUGHTIRON, TFC.TFCItems.WroughtIronUses);
		TFC_HammerHeads.put(Global.WROUGHTIRON, TFCItems.WroughtIronHammerHead);
		TFC_ChiselHeads.put(Global.WROUGHTIRON, TFC.TFCItems.WroughtIronChiselHead);
		toolmetals[7]=Global.REDSTEEL;
		TFC_MetalUses.put(Global.REDSTEEL, TFC.TFCItems.RedSteelUses);
		TFC_HammerHeads.put(Global.REDSTEEL, TFCItems.RedSteelHammerHead);
		TFC_ChiselHeads.put(Global.REDSTEEL, TFC.TFCItems.RedSteelChiselHead);
		toolmetals[8]=Global.STEEL;
		TFC_MetalUses.put(Global.STEEL, TFC.TFCItems.SteelUses);
		TFC_HammerHeads.put(Global.STEEL, TFCItems.SteelHammerHead);
		TFC_ChiselHeads.put(Global.STEEL, TFC.TFCItems.SteelChiselHead);
	}
}
