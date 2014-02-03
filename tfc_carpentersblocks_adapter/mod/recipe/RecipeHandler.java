package tfc_carpentersblocks_adapter.mod.recipe;

import java.util.HashMap;
import java.util.logging.Level;

import carpentersblocks.util.handler.OverlayHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.HashMap;
import java.util.logging.Level;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import TFC.TFCItems;
import tfc_carpentersblocks_adapter.mod.util.ModLogger;
import tfc_carpentersblocks_adapter.mod.util.Util;


public class RecipeHandler {
	
    public static int recipeQuantitySlope = 4;
    public static int recipeQuantityStairs = 4;
    public static int recipeQuantityBarrier = 2;
    public static int recipeQuantityGate = 1;
    public static int recipeQuantityBlock = 3;
    public static int recipeQuantityButton = 1;
    public static int recipeQuantityLever = 1;
    public static int recipeQuantityPressurePlate = 1;
    public static int recipeQuantityDaylightSensor = 1;
    public static int recipeQuantityHatch = 1;
    public static int recipeQuantityDoor = 1;
    public static int recipeQuantityBed = 1;
    public static int recipeQuantityLadder = 4;
    
    enum RecipeType{
    	ObjArray,
    	ShapedOre,
    	ShapedTFC
    }
    public static void purgeBlockRecipes(String blockname,int stacksize){
    	Block block=Util.findBlock(blockname);
    	if(block != null){
    		ItemStack itemStack=new ItemStack(block, stacksize);
    		Util.removeRecipes(itemStack);
    	}
    }
    public static void changeBlockRecipe(String blockname,Object[] recipe,int oldstacksize, int newstacksize,RecipeType type){
    	purgeBlockRecipes(blockname,oldstacksize);
    	addBlockRecipe(blockname,recipe,newstacksize,type);
    }
    public static void addBlockRecipe(String blockname,Object[] recipe,int stacksize,RecipeType type){
		Block block=Util.findBlock(blockname);
		if(block != null){
			ItemStack itemStack=new ItemStack(block, stacksize);
			ModLogger.log(Level.INFO, "attempting to remove Recipe for "+block.getUnlocalizedName());
			
			switch(type){
			case ObjArray:
			default:
				GameRegistry.addRecipe(itemStack, recipe);
				break;
			case ShapedOre:
				ShapedOreRecipe shapedOreRecipe = new ShapedOreRecipe(itemStack,recipe);
				GameRegistry.addRecipe(shapedOreRecipe);
				break;
			case ShapedTFC:
				
				break;
			}
		} else {
			ModLogger.log(Level.SEVERE,"Could not find block:"+blockname);
		}
	}
    
	public static void changeBlockRecipes()
    {
		ModLogger.log(Level.INFO, "attempting to adjust Carpenter's Blocks Block Recipes");
		Block blockCarpentersBlock = Util.findBlock("blockCarpentersBlock");
		Item TFCplank=Util.findItem("SinglePlank");
		Block TFChorizSupport = Util.findBlock("WoodSupportH");
		Block TFCvertSupport = Util.findBlock("WoodSupportV");
		//Carpenter Block
		Object[] recipe;
		recipe=new Object[]{"XXX", "XYX", "XXX", 'X', new ItemStack(TFCplank,1,32767), 'Y', "plankWood"};
		changeBlockRecipe("blockCarpentersBlock",recipe,carpentersblocks.util.registry.BlockRegistry.recipeQuantityBlock,recipeQuantityBlock,RecipeType.ShapedOre);
		//Slope
		recipe=new Object[]{"  X", " XY", "XYY", 'X', new ItemStack(TFCplank,1,32767), 'Y', "plankWood"};
		changeBlockRecipe("blockCarpentersSlope",recipe,carpentersblocks.util.registry.BlockRegistry.recipeQuantitySlope,recipeQuantitySlope,RecipeType.ShapedOre);
		//Stairs
		recipe=new Object[]{"  X", " XX", "XXX", 'X', blockCarpentersBlock};
		changeBlockRecipe("blockCarpentersStairs",recipe,carpentersblocks.util.registry.BlockRegistry.recipeQuantityStairs,recipeQuantityStairs,RecipeType.ObjArray);
		//Barrier (fence)
		recipe=new Object[]{"XYX", "XYX", 'X', TFCvertSupport, 'Y',TFChorizSupport};
		changeBlockRecipe("blockCarpentersBarrier",recipe,carpentersblocks.util.registry.BlockRegistry.recipeQuantityBarrier,recipeQuantityBarrier,RecipeType.ShapedOre);
		//Gate
		recipe=new Object[]{"XYX", "XYX", 'X', TFCvertSupport, 'Y', blockCarpentersBlock};
		changeBlockRecipe("blockCarpentersGate",recipe,carpentersblocks.util.registry.BlockRegistry.recipeQuantityGate,recipeQuantityGate,RecipeType.ShapedOre);
	    

	    //--Don't Need Changes (at least not yet)

	    //Light Sensor
//	    recipe=new Object[] {"XXX", "YYY", "ZZZ", 'X', Block.glass, 'Y', Item.netherQuartz, 'Z', blockCarpentersBlock};
//	    changeBlockRecipe("blockCarpentersDaylightSensor",recipe,recipeQuantityDaylightSensor,false);
	    //Hatch
//	    recipe=new Object[] {"XXX", "XXX", 'X', blockCarpentersBlock};
//	    changeBlockRecipe("blockCarpentersHatch",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityHatch,false);
	    //Lever
//	    recipe=new Object[] {"X", "Y", 'X', "stickWood", 'Y', blockCarpentersBlock};
//	    changeBlockRecipe("blockCarpentersLever",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityLever,true);
		//Button
//		recipe=new Object[] {"X", 'X', blockCarpentersBlock};
//		changeBlockRecipe("blockCarpentersButton",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityButton,true);
	    //Pressure Plate
//	    recipe=new Object[] {"XX", 'X', blockCarpentersBlock};
//	    changeBlockRecipe("blockCarpentersPressurePlate",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityPressurePlate,false);
	    //Ladder
//	    recipe=new Object[] {"X X", "XXX", "X X", 'X', blockCarpentersBlock};
//	    changeBlockRecipe("blockCarpentersLadder",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityLadder,false);
    }
	
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
		changeItemRecipe("itemCarpentersBed",recipe,carpentersblocks.util.registry.BlockRegistry.recipeQuantityBed,tfc_carpentersblocks_adapter.mod.recipe.RecipeHandler.recipeQuantityBed);
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
