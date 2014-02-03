package tfc_carpentersblocks_adapter.mod.item;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import carpentersblocks.CarpentersBlocks;
import carpentersblocks.item.ItemCarpentersHammer;
import carpentersblocks.util.registry.ItemRegistry;

public class ItemCarpentersHammerTFC extends ItemCarpentersHammer {

	public ItemCarpentersHammerTFC(int itemID,TFC.API.Metal metal) {
		super(itemID);

		setUnlocalizedName("item."+metal.Name.replace(" ", "")+"CarpentersHammer");
		setMaxStackSize(1);

		if (ItemRegistry.itemCarpentersToolsDamageable) {
			setMaxDamage(tfc_carpentersblocks_adapter.mod.util.TFC_ItemConvenience.TFC_MetalUses.get(metal));
		}
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon("TFC_CarpentersBlocks_adapter:originalhammer");
	}
}
