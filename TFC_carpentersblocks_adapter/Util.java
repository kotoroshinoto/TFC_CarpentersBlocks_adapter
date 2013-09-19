package TFC_carpentersblocks_adapter;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import java.util.logging.Level;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class Util {
	public static Item findItem(String unlocalizedItemName){
		Item[] items=Item.itemsList;
		for(int i=0;i<items.length;i++){
			Item item=items[i];
			if(item !=null){
				String itemname=item.getUnlocalizedName();
				if(itemname.equalsIgnoreCase("item."+unlocalizedItemName)){
					return item;
				}
			}
		}
		return null;
	}

	public static Block findBlock(String unlocalizedBlockName){
		Block[] blocks=Block.blocksList;
		for(int i=0;i<blocks.length;i++){
			Block block=blocks[i];
			if(block != null){
				String blockname=block.getUnlocalizedName();
				if(blockname.equalsIgnoreCase("tile."+unlocalizedBlockName)){
					return block;
				}
			}
		}
		return null;
	}
	
	public static void listItems(){
		Item[] items=Item.itemsList;
		for(int i=0;i<items.length;i++){
			Item item=items[i];
			if(item != null){
				String itemname=item.getUnlocalizedName();
				ModLogger.log(Level.INFO,"Item ID:"+item.itemID +" Name:"+itemname);
			}
		}
	}
	public static void listBlocks(){
		Block[] blocks=Block.blocksList;
		for(int i=0;i<blocks.length;i++){
			Block block=blocks[i];
			if (block != null){
				String blockname=block.getUnlocalizedName();
				ModLogger.log(Level.INFO,"Block ID:"+block.blockID +" Name:"+blockname);
			}
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
				ModLogger.log(Level.WARNING, "found a recipe that isn't shaped or unshaped");
			}
		}
		return -1;
    }
    public static void removeRecipes(ItemStack stack){
    	ModLogger.log(Level.INFO, "Attempting to remove all recipes for item");
    	for(int recipe_index=findRecipe(stack);recipe_index != -1;recipe_index=findRecipe(stack)){	
    		removeRecipe(recipe_index);
    	}
    }
    public static void removeRecipe(int index){
    	ModLogger.log(Level.INFO, "Attempting to remove recipe #"+index);
    	CraftingManager craftman=CraftingManager.getInstance();
		@SuppressWarnings("rawtypes")
		List recipes=craftman.getRecipeList();
		recipes.remove(index);
    }
}
