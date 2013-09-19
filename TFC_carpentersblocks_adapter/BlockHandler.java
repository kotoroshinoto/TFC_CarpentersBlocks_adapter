package TFC_carpentersblocks_adapter;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler {
	
    public static int recipeQuantitySlope = 4;
    public static int recipeQuantityStairs = 4;
    public static int recipeQuantityBarrier = 2;
    public static int recipeQuantityGate = 1;
    public static int recipeQuantityBlock = 5;
    public static int recipeQuantityButton = 1;
    public static int recipeQuantityLever = 1;
    public static int recipeQuantityPressurePlate = 1;
    public static int recipeQuantityDaylightSensor = 1;
    public static int recipeQuantityHatch = 1;
    public static int recipeQuantityDoor = 1;
    public static int recipeQuantityBed = 1;
    public static int recipeQuantityLadder = 4;
    
    public static void changeBlockRecipe(String blockname,Object[] recipe, int stacksize,boolean isShapedOreRecipe){
		Block block=Util.findBlock(blockname);
		if(block != null){
			ItemStack itemStack=new ItemStack(block, stacksize);
			ModLogger.log(Level.INFO, "attempting to remove Recipe for "+block.getUnlocalizedName());
			Util.removeRecipes(itemStack);
			if(isShapedOreRecipe){
				ShapedOreRecipe shapedOreRecipe = new ShapedOreRecipe(itemStack,recipe);
//				GameRegistry.addRecipe(itemStack, shapedOreRecipe);
			} else {
//				GameRegistry.addRecipe(itemStack, recipe);
			}
		} else {
			ModLogger.log(Level.SEVERE,"Could not find block:"+blockname);
		}
	}
    
	public static void changeBlockRecipes()
    {
		ModLogger.log(Level.INFO, "attempting to adjust Carpenter's Blocks Block Recipes");
		Block blockCarpentersBlock = Util.findBlock("blockCarpentersBlock");
		//Carpenter Block
		Object[] recipe=new Object[]{"XXX", "XYX", "XXX", 'X', "stickWood", 'Y', "plankWood"};
		changeBlockRecipe("blockCarpentersBlock",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityBlock,true);
		//Slope
		recipe=new Object[]{"  X", " XY", "XYY", 'X', "stickWood", 'Y', "plankWood"};
		changeBlockRecipe("blockCarpentersSlope",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantitySlope,true);
		//Stairs
		recipe=new Object[]{"  X", " XX", "XXX", 'X', blockCarpentersBlock};
		changeBlockRecipe("blockCarpentersStairs",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityStairs,false);
		//Barrier (fence)
		recipe=new Object[]{"X X", "XXX", 'X', "stickWood"};
		changeBlockRecipe("blockCarpentersBarrier",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityBarrier,true);
		//Gate
		recipe=new Object[]{"XYX", "XYX", 'X', "stickWood", 'Y', blockCarpentersBlock};
		changeBlockRecipe("blockCarpentersGate",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityGate,true);
		//Button
		recipe=new Object[] {"X", 'X', blockCarpentersBlock};
		changeBlockRecipe("blockCarpentersButton",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityButton,true);
	    //Lever
	    recipe=new Object[] {"X", "Y", 'X', "stickWood", 'Y', blockCarpentersBlock};
	    changeBlockRecipe("blockCarpentersLever",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityLever,true);
	    //Pressure Plate
	    recipe=new Object[] {"XX", 'X', blockCarpentersBlock};
	    changeBlockRecipe("blockCarpentersPressurePlate",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityPressurePlate,false);
	    //Light Sensor
	    recipe=new Object[] {"XXX", "YYY", "ZZZ", 'X', Block.glass, 'Y', Item.netherQuartz, 'Z', blockCarpentersBlock};
	    changeBlockRecipe("blockCarpentersDaylightSensor",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityDaylightSensor,false);
	    //Hatch
	    recipe=new Object[] {"XXX", "XXX", 'X', blockCarpentersBlock};
	    changeBlockRecipe("blockCarpentersHatch",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityHatch,false);
	    //Ladder
	    recipe=new Object[] {"X X", "XXX", "X X", 'X', blockCarpentersBlock};
	    changeBlockRecipe("blockCarpentersLadder",recipe,carpentersblocks.util.handler.BlockHandler.recipeQuantityLadder,false);
    }
}
