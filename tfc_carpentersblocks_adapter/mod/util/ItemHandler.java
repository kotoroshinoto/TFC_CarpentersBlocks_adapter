package tfc_carpentersblocks_adapter.mod.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.logging.Level;

import carpentersblocks.util.handler.OverlayHandler;
import TFC.TFCItems;
public class ItemHandler {
	public static void changeItemRecipe(String itemname,Object[] recipe, int oldstacksize, int newstacksize){
		Item item=Util.findItem(itemname);
		if(item != null){
			ItemStack olditemStack=new ItemStack(item, oldstacksize);
			ItemStack itemStack=new ItemStack(item, newstacksize);
			ModLogger.log(Level.INFO, "attempting to remove Recipe for "+item.getUnlocalizedName());
			Util.removeRecipes(olditemStack);
			GameRegistry.addRecipe(itemStack, recipe);
		} else {
			ModLogger.log(Level.SEVERE,"Could not find item:"+itemname);
		}
	}
	
	public static void changeToolRecipe(String itemname,Object[] recipe, int oldstacksize, int newstacksize){
		Item item=Util.findItem(itemname);
		if(item != null){
			ItemStack olditemStack=new ItemStack(item, oldstacksize);
			ItemStack itemStack=new ItemStack(item, newstacksize);
			ModLogger.log(Level.INFO, "attempting to remove Recipe for "+item.getUnlocalizedName());
			Util.removeRecipes(olditemStack);
//			Item TFCHammerHead = Util.findItem("Wrought Iron Hammer Head");
//			Item TFCChiselHead = Util.findItem("Wrought Iron Chisel Head");
			for(int i = 0; i <= 25; i+=5)
			{
				float j = ((float)i / 100);
				//bismuth bronze
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.BismuthBronzeUses*j));
//				//black bronze
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.BlackBronzeUses*j));
//				//black steel
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.BlackSteelUses*j));
//				//blue steel
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.BlueSteelUses*j));
//				//red steel
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.RedSteelUses*j));
//				//bronze
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.BronzeUses*j));
//				//copper
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.CopperUses*j));
//				//steel
//				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.SteelUses*j));
				//wrought iron
				itemStack=new ItemStack(item, newstacksize,(int)(TFCItems.WroughtIronUses*j));
				/*
				GameRegistry.addRecipe(
				new ItemStack(TFCItems.BismuthBronzePick, 1,(int)(TFCItems.BismuthBronzeUses*j)), 
				new Object[] { "#","I", Character.valueOf('#'), new ItemStack(TFCItems.BismuthBronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)}
				);
				*/
				GameRegistry.addRecipe(itemStack, recipe);
			}
		} else {
			ModLogger.log(Level.SEVERE,"Could not find item:"+itemname);
		}
	}
	public static void changeItemRecipes()
    {
		Block blockCarpentersBlock = Util.findBlock("blockCarpentersBlock");
		Item TFCironHammerHead = TFCItems.WroughtIronHammerHead;// Util.findItem("Wrought Iron Hammer Head");
		Item TFCironChiselHead = TFCItems.WroughtIronChiselHead;// Util.findItem("Wrought Iron Chisel Head");
		Item TFCwoolCloth = TFCItems.WoolCloth;//Util.findItem("WoolCloth"); 
		ModLogger.log(Level.INFO, "attempting to adjust Carpenter's Blocks Item Recipes");
		//HAMMER
		Object[] recipe=new Object[] { "X", "Y", 'X', TFCironHammerHead, 'Y', blockCarpentersBlock };
		changeItemRecipe("itemCarpentersHammer",recipe,1,1);
		//CHISEL
		recipe=new Object[] { "X", "Y", 'X', TFCironChiselHead, 'Y', blockCarpentersBlock };
		changeItemRecipe("itemCarpentersChisel",recipe,1,1);
		//DOOR
//		recipe=new Object[] {"XX", "XX", "XX", 'X', carpentersblocks.util.handler.BlockHandler.blockCarpentersBlock};
//		changeItemRecipe("itemCarpentersDoor",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityDoor);
		//BED
		recipe=new Object[] {"XXX", "YYY", 'X', TFCwoolCloth, 'Y', blockCarpentersBlock};
		changeItemRecipe("itemCarpentersBed",recipe,carpentersblocks.util.registry.BlockRegistry.recipeQuantityBed,tfc_carpentersblocks_adapter.mod.util.BlockHandler.recipeQuantityBed);
    }
	@SuppressWarnings("unchecked")
	public static void registerOverlayItems(){
		@SuppressWarnings("rawtypes")
		HashMap overlayMap=(HashMap) OverlayHandler.overlayMap;
		ItemStack grassitemstack=new ItemStack(Block.tallGrass,1,1);
		overlayMap.put(1, grassitemstack.itemID);
		Item strawItem=Util.findItem("Straw");
		overlayMap.put(5, strawItem.itemID);
	}
}
