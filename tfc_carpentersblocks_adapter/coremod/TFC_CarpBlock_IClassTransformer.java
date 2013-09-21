package tfc_carpentersblocks_adapter.coremod;

import java.util.Iterator;
import java.util.logging.Level;

import scala.tools.asm.ClassVisitor;
import scala.tools.asm.ClassReader;
import scala.tools.asm.ClassWriter;
import scala.tools.asm.Opcodes;
import scala.tools.asm.tree.ClassNode;
import scala.tools.asm.tree.InsnList;
import scala.tools.asm.tree.JumpInsnNode;
import scala.tools.asm.tree.LabelNode;
import scala.tools.asm.tree.MethodNode;
import scala.tools.asm.tree.MethodInsnNode;
import scala.tools.asm.tree.FieldInsnNode;
import scala.tools.asm.tree.AbstractInsnNode;
import tfc_carpentersblocks_adapter.mod.util.ModLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.world.World;

import org.objectweb.asm.commons.Remapper;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class TFC_CarpBlock_IClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		
		if(arg0.equals("carpentersblocks.block.BlockBase")){
			ModLogger.log(Level.INFO, "identified Blockbase class for modification ");
			return patchClassASM(arg0,arg2);
		}
		return arg2;
	}

	private byte[] patchClassASM(String name, byte[] bytes) {
		// TODO Auto-generated method stub
		String targetMethodName= "onBlockActivated";
		String targetMethodDesc="(Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/EntityPlayer;IFFF)Z";
		boolean needDeobf=tfc_carpentersblocks_adapter.coremod.TFC_CarpBlock_IFMLLoadingPlugin.runtimeDeobf;
		//set up ASM class manipulation stuff. Consult the ASM docs for details
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		
		Iterator<MethodNode> methods = classNode.methods.iterator();
		ModLogger.log(Level.INFO, "looking for "+targetMethodName+" "+targetMethodDesc);
		while(methods.hasNext())
		{
			MethodNode m = methods.next();
			int enableDyeColors_index = -1;
//public boolean onBlockActivated
//(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
//(Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/EntityPlayer;IFFF)Z
			String methodName;
			String methodDesc;
			if (needDeobf){
				methodName=FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, m.name, m.desc);
				methodDesc=FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(m.desc);
			} else {
				methodName=m.name;
				methodDesc=m.desc;
			}
			ModLogger.log(Level.INFO, "checking "+methodName+" "+methodDesc);
			if (targetMethodName.equals(methodName) && targetMethodDesc.equals(methodDesc))
			{
				ModLogger.log(Level.INFO, "target method found");
				AbstractInsnNode currentNode = null;
				
				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				int index = -1;
				String targetOwner="carpentersblocks/util/handler/FeatureHandler";
				String targetName="enableDyeColors";
				String targetDesc="Z";
				ModLogger.log(Level.INFO, "looking for: Type("+AbstractInsnNode.FIELD_INSN+") Opcode("+Opcodes.GETSTATIC+")"+targetOwner+" "+targetName+" "+targetDesc);
				while (iter.hasNext())
				{
					index++;
					currentNode = iter.next();
					ModLogger.log(Level.INFO, "Node instruction type: "+currentNode.getType()+" opcode: "+currentNode.getOpcode());
					if (currentNode.getType() == AbstractInsnNode.FIELD_INSN && currentNode.getOpcode() ==Opcodes.GETSTATIC)
					{
						FieldInsnNode fieldnode=(FieldInsnNode)currentNode;
						String nodeOwner;
						String nodeName;
						String nodeDesc;
						if (needDeobf){
							nodeOwner=FMLDeobfuscatingRemapper.INSTANCE.mapType(fieldnode.owner);
							nodeName=FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(fieldnode.owner, fieldnode.name, fieldnode.desc);
							nodeDesc=FMLDeobfuscatingRemapper.INSTANCE.mapDesc(fieldnode.desc);
						} else {
							nodeOwner=fieldnode.owner;
							nodeName=fieldnode.name;
							nodeDesc=fieldnode.desc;
						}
						ModLogger.log(Level.INFO, "checking: "+nodeOwner+" "+nodeName+" "+nodeDesc);
						if(nodeOwner.equals(targetOwner) && nodeName.equals(targetName) && nodeDesc.equals(targetDesc) ){
							ModLogger.log(Level.INFO, "target bytecode instruction found");
							enableDyeColors_index=index;
							break;
						}
					}
				}
				JumpInsnNode oldjump=(JumpInsnNode)m.instructions.get(enableDyeColors_index+5);
				JumpInsnNode newjump=new JumpInsnNode(Opcodes.IF_ICMPNE,oldjump.label);
				m.instructions.remove(oldjump);
				AbstractInsnNode targetInsNode=m.instructions.get(enableDyeColors_index+4);
				if (needDeobf){
					m.instructions.insertBefore(targetInsNode, new FieldInsnNode(Opcodes.GETFIELD, "yb", "field_77779_bT", "I"));
					InsnList toInject = new InsnList();
					toInject.add(new FieldInsnNode(Opcodes.GETFIELD, "yb", "field_77779_bT", "I"));
					toInject.add(newjump);
					m.instructions.insert(targetInsNode, toInject);
				}else{
					m.instructions.insertBefore(targetInsNode, new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/item/Item", "itemID", "I"));
					InsnList toInject = new InsnList();
					toInject.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/item/Item", "itemID", "I"));
					toInject.add(newjump);
					m.instructions.insert(targetInsNode, toInject);
				}
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

}
