package tfc_carpentersblocks_adapter.mod.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.logging.Level;

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
	public static void changeItemRecipes()
    {
		Block blockCarpentersBlock = Util.findBlock("blockCarpentersBlock");
		Item TFCironHammerHead = Util.findItem("Wrought Iron Hammer Head");
		Item TFCironChiselHead = Util.findItem("Wrought Iron Chisel Head");
		Item TFCwoolCloth = Util.findItem("WoolCloth"); 
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
		changeItemRecipe("itemCarpentersBed",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityBed,tfc_carpentersblocks_adapter.mod.util.BlockHandler.recipeQuantityBed);
    }
}
