package TFC_carpentersblocks_adapter;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemHandler {
	
	public static void changeItemRecipes()
    {
		Item itemCarpentersHammer=GameRegistry.findItem("CarpentersBlocks", "itemCarpentersHammer");
		if(itemCarpentersHammer != null){
			ItemStack HammerItemStack=new ItemStack(itemCarpentersHammer, 1);
			System.out.println("attempting to remove Carpenter's Hammer Recipe");
			removeRecipes(HammerItemStack);
//			GameRegistry.addRecipe(new ItemStack(itemCarpentersHammer, 1), new Object[] { "XX ", " YX", " Y ", 'X', Item.ingotIron, 'Y', BlockHandler.blockCarpentersBlock });
		}
		Item itemCarpentersChisel=GameRegistry.findItem("CarpentersBlocks", "itemCarpentersChisel");
		if(itemCarpentersChisel != null){
			ItemStack ChiselItemStack=new ItemStack(itemCarpentersChisel, 1);
			System.out.println("attempting to remove Carpenter's Chisel Recipe");
			removeRecipes(ChiselItemStack);
//			GameRegistry.addRecipe(new ItemStack(itemCarpentersChisel, 1), new Object[] { "X", "Y", 'X', Item.ingotIron, 'Y', BlockHandler.blockCarpentersBlock });
		}
		Item itemCarpentersDoor=GameRegistry.findItem("CarpentersBlocks", "itemCarpentersDoor");
		if(itemCarpentersDoor != null){
			ItemStack DoorItemStack=new ItemStack(itemCarpentersDoor, carpentersblocks.util.handler.BlockHandler.recipeQuantityDoor);
			System.out.println("attempting to remove Carpenter's Door Recipe");
			removeRecipes(DoorItemStack);
//			GameRegistry.addRecipe(, new Object[] {"XX", "XX", "XX", 'X', BlockHandler.blockCarpentersBlock});
		}
		Item itemCarpentersBed=GameRegistry.findItem("CarpentersBlocks", "itemCarpentersBed");
		if(itemCarpentersBed != null){
			ItemStack BedItemStack=new ItemStack(itemCarpentersBed, carpentersblocks.util.handler.BlockHandler.recipeQuantityBed);
			System.out.println("attempting to remove Carpenter's Bed Recipe");
			removeRecipes(BedItemStack);
//			GameRegistry.addRecipe(, new Object[] {"XXX", "YYY", 'X', Block.cloth, 'Y', BlockHandler.blockCarpentersBlock});
		}
		
		
    }

    public static int findRecipe(ItemStack stack){
    	CraftingManager craftman=CraftingManager.getInstance();
		@SuppressWarnings("rawtypes")
		List recipes=craftman.getRecipeList();
		for(int i=0;i<recipes.size();i++){
			Object o = recipes.get(i);
			if (o instanceof IRecipe){
				IRecipe recipe=(IRecipe)o;
				ItemStack output=recipe.getRecipeOutput();
				if (ItemStack.areItemStacksEqual(stack,output)){
					return i;
				}
			} else {
				System.out.println("found a recipe that isn't shaped or unshaped");
			}
		}
		return -1;
    }
    public static void removeRecipes(ItemStack stack){
    	System.out.println("Attempting to remove all recipes for item");
    	for(int recipe_index=findRecipe(stack);recipe_index != -1;recipe_index=findRecipe(stack)){	
    		System.out.println("Attempting to a recipe for item");
    		removeRecipe(recipe_index);
    	}
    }
    public static void removeRecipe(int index){
    	CraftingManager craftman=CraftingManager.getInstance();
		@SuppressWarnings("rawtypes")
		List recipes=craftman.getRecipeList();
		recipes.remove(index);
    }
}
