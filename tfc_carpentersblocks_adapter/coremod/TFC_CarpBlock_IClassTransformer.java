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
import net.minecraft.item.ItemStack;
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
			return patchBlockBase(arg0,arg2);
		}
		if (arg0.equals("carpentersblocks.util.BlockProperties")){
			ModLogger.log(Level.INFO, "identified BlockProperties class for modification ");
			return patchBlockProperties(arg0,arg2);
		}
		if(arg0.equals("carpentersblocks.util.handler.OverlayHandler")){
			ModLogger.log(Level.INFO, "identified OverlayHandler class for modification ");
			return patchOverlayHandler(arg0,arg2);
		}
		return arg2;
	}
	private byte[] patchBlockProperties(String name, byte[] bytes){
		/*
Change this:
{
mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isOverlay", "(Lnet/minecraft/item/ItemStack;)Z", null, null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(302, l0);
mv.visitFieldInsn(GETSTATIC, "carpentersblocks/util/handler/OverlayHandler", "overlayMap", "Ljava/util/Map;");
mv.visitVarInsn(ALOAD, 0);
mv.visitFieldInsn(GETFIELD, "net/minecraft/item/ItemStack", "itemID", "I");
mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "containsValue", "(Ljava/lang/Object;)Z");
mv.visitInsn(IRETURN);
Label l1 = new Label();
mv.visitLabel(l1);
mv.visitLocalVariable("itemStack", "Lnet/minecraft/item/ItemStack;", null, l0, l1, 0);
mv.visitMaxs(2, 1);
mv.visitEnd();
}

To this:
{
mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isOverlay", "(Lnet/minecraft/item/ItemStack;)Z", null, null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(302, l0);
mv.visitVarInsn(ALOAD, 0);
mv.visitFieldInsn(GETFIELD, "net/minecraft/item/ItemStack", "itemID", "I");
mv.visitFieldInsn(GETSTATIC, "carpentersblocks/util/handler/OverlayHandler", "overlayMap", "Ljava/util/Map;");
mv.visitInsn(ICONST_1);
mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
Label l1 = new Label();
mv.visitJumpInsn(IF_ICMPNE, l1);
mv.visitVarInsn(ALOAD, 0);
mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getItemDamage", "()I");
mv.visitInsn(ICONST_1);
mv.visitJumpInsn(IF_ICMPEQ, l1);
Label l2 = new Label();
mv.visitLabel(l2);
mv.visitLineNumber(303, l2);
mv.visitInsn(ICONST_0);
mv.visitInsn(IRETURN);
mv.visitLabel(l1);
mv.visitLineNumber(304, l1);
mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
mv.visitFieldInsn(GETSTATIC, "carpentersblocks/util/handler/OverlayHandler", "overlayMap", "Ljava/util/Map;");
mv.visitVarInsn(ALOAD, 0);
mv.visitFieldInsn(GETFIELD, "net/minecraft/item/ItemStack", "itemID", "I");
mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "containsValue", "(Ljava/lang/Object;)Z");
mv.visitInsn(IRETURN);
Label l3 = new Label();
mv.visitLabel(l3);
mv.visitLocalVariable("itemStack", "Lnet/minecraft/item/ItemStack;", null, l0, l3, 0);
mv.visitMaxs(3, 1);
mv.visitEnd();
}
		 */
		return bytes;
	}
	private byte[] patchOverlayHandler(String name, byte[] bytes) {
		/*

public static ItemStack getItemStack(int overlay)
change this:
{
mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getItemStack", "(I)Lnet/minecraft/item/ItemStack;", null, null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(68, l0);
mv.visitTypeInsn(NEW, "net/minecraft/item/ItemStack");
mv.visitInsn(DUP);
mv.visitFieldInsn(GETSTATIC, "carpentersblocks/util/handler/OverlayHandler", "overlayMap", "Ljava/util/Map;");
mv.visitVarInsn(ILOAD, 0);
mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
mv.visitInsn(ICONST_1);
mv.visitInsn(ICONST_0);
mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/item/ItemStack", "<init>", "(III)V");
mv.visitInsn(ARETURN);
Label l1 = new Label();
mv.visitLabel(l1);
mv.visitLocalVariable("overlay", "I", null, l0, l1, 0);
mv.visitMaxs(5, 1);
mv.visitEnd();
}


to this:
{
mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getItemStack", "(I)Lnet/minecraft/item/ItemStack;", null, null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(68, l0);
mv.visitTypeInsn(NEW, "net/minecraft/item/ItemStack");
mv.visitInsn(DUP);
mv.visitFieldInsn(GETSTATIC, "carpentersblocks/util/handler/OverlayHandler", "overlayMap", "Ljava/util/Map;");
mv.visitVarInsn(ILOAD, 0);
mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
mv.visitInsn(ICONST_1);
mv.visitVarInsn(ILOAD, 0);
mv.visitInsn(ICONST_1);
Label l1 = new Label();
mv.visitJumpInsn(IF_ICMPNE, l1);
mv.visitInsn(ICONST_1);
Label l2 = new Label();
mv.visitJumpInsn(GOTO, l2);
mv.visitLabel(l1);
mv.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 4, new Object[] {l0, l0, Opcodes.INTEGER, Opcodes.INTEGER});
mv.visitInsn(ICONST_0);
mv.visitLabel(l2);
mv.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 5, new Object[] {l0, l0, Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER});
mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/item/ItemStack", "<init>", "(III)V");
mv.visitInsn(ARETURN);
Label l3 = new Label();
mv.visitLabel(l3);
mv.visitLocalVariable("overlay", "I", null, l0, l3, 0);
mv.visitMaxs(6, 1);
mv.visitEnd();
}

		 */
		return bytes;
	}
	private byte[] patchBlockBase(String name, byte[] bytes) {
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
