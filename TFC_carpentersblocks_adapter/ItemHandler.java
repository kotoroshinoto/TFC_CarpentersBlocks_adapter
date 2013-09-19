package TFC_carpentersblocks_adapter;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.logging.Level;

public class ItemHandler {
	public static void changeItemRecipe(String itemname,Object[] recipe, int stacksize){
		Item item=Util.findItem(itemname);
		if(item != null){
			ItemStack itemStack=new ItemStack(item, stacksize);
			ModLogger.log(Level.INFO, "attempting to remove Recipe for "+item.getUnlocalizedName());
			Util.removeRecipes(itemStack);
//			GameRegistry.addRecipe(itemStack, recipe);
		} else {
			ModLogger.log(Level.SEVERE,"Could not find item:"+itemname);
		}
	}
	public static void changeItemRecipes()
    {
		ModLogger.log(Level.INFO, "attempting to adjust Carpenter's Blocks Item Recipes");
		//HAMMER
		Object[] recipe=new Object[] { "XX ", " YX", " Y ", 'X', Item.ingotIron, 'Y', carpentersblocks.util.handler.BlockHandler.blockCarpentersBlock };
		changeItemRecipe("itemCarpentersHammer",recipe,1);
		//CHISEL
		recipe=new Object[] { "X", "Y", 'X', Item.ingotIron, 'Y', carpentersblocks.util.handler.BlockHandler.blockCarpentersBlock };
		changeItemRecipe("itemCarpentersChisel",recipe,1);
		//DOOR
		recipe=new Object[] {"XX", "XX", "XX", 'X', carpentersblocks.util.handler.BlockHandler.blockCarpentersBlock};
		changeItemRecipe("itemCarpentersDoor",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityDoor);
		//BED
		recipe=new Object[] {"XXX", "YYY", 'X', Block.cloth, 'Y', carpentersblocks.util.handler.BlockHandler.blockCarpentersBlock};
		changeItemRecipe("itemCarpentersBed",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityBed);
    }
}
