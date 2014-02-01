package tfc_carpentersblocks_adapter.coremod.util;

import java.util.logging.Level;

import carpentersblocks.tileentity.TEBase;
import carpentersblocks.util.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ReplacementFunctions {
	//fix dyes
	public static boolean isValidDye(ItemStack itemStack) {
		ModLogger.log(Level.INFO, "checking if ID: "+itemStack.itemID+" is a dye");
		return itemStack.itemID == Item.dyePowder.itemID;
	}
	//fix overlays
	public static boolean isOverlay(ItemStack itemStack){
		ModLogger.log(Level.INFO, "checking if ID: "+itemStack.itemID+" is an overlay");
		//put it in
//		ModLogger.log(Level.INFO, "Testing ID:"+itemStack.itemID+" DMG: "+itemStack.getItemDamage());
		if(itemStack.itemID == Block.tallGrass.blockID){
			if(itemStack.getItemDamage() != 1){
//				ModLogger.log(Level.INFO, "tallGrass && dmg != 1");
				return false;
			}
		}
//		ModLogger.log(Level.INFO, "Normal Overlay Check");
		return carpentersblocks.util.BlockProperties.isOverlay(itemStack);		
	}
	//OverlayHandler.getItemStack
	public static ItemStack FilterOverlayItemStack(ItemStack itemStack){
		ModLogger.log(Level.INFO, "checking which item to give back when returning: "+itemStack.itemID+" from an overlay");
		//get it back
		if(itemStack.itemID == Block.tallGrass.blockID){
			return new ItemStack(Block.tallGrass,1,1);
		} else {
			return itemStack;
		}
	}
	//fix covers & side covers
	public static boolean isCover(ItemStack itemStack){
		ModLogger.log(Level.INFO, "checking if ID: "+itemStack.itemID+" is a cover");
		//isvalid
		if(itemStack.itemID == Block.cloth.blockID){
			return false;
		}
		if(itemStack.itemID == Block.carpet.blockID){
			return carpentersblocks.util.BlockProperties.isCover(new ItemStack(Block.cloth,1,itemStack.getItemDamage()));
		}else{
			return carpentersblocks.util.BlockProperties.isCover(itemStack);
		}
	}
	
	public static boolean setCover(TEBase block, int side, int metadata, ItemStack itemStack){
		ModLogger.log(Level.INFO, "setting ID: "+itemStack.itemID+" as a cover");
		//put it in
		if(itemStack.itemID == Block.carpet.blockID){
			return carpentersblocks.util.BlockProperties.setCover(block,side,metadata, new ItemStack(Block.cloth,1,itemStack.getItemDamage()));
		}else{
			return carpentersblocks.util.BlockProperties.setCover(block,side,metadata,itemStack);
		}
	}
	//BlockProperties ejectEntity
	public static ItemStack FilterCoverBlock(ItemStack itemStack){
		ModLogger.log(Level.INFO, "checking which item to give back when returning: "+itemStack.itemID+" from a cover");
		//get it back
		if(itemStack.itemID == Block.cloth.blockID){
			return new ItemStack(Block.carpet,itemStack.stackSize,itemStack.getItemDamage());
		}else{
			return itemStack;
		}
		
	}
}
