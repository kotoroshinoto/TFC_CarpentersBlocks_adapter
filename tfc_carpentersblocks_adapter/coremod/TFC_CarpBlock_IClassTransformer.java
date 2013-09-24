package tfc_carpentersblocks_adapter.coremod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import scala.tools.asm.*;
import scala.tools.asm.tree.*;
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
		// TODO Auto-generated method stub
		String targetMethodName= "isOverlay";
		String targetMethodDesc="(Lnet/minecraft/item/ItemStack;)Z";
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
			int targetInsn_index = -1;
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
//				this.logMethodNodes(m);
				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				int index = -1;
				int targetInstructionType=AbstractInsnNode.FIELD_INSN;
				int targetOpcodeType=Opcodes.GETSTATIC;
				String targetOwner="carpentersblocks/util/handler/OverlayHandler";
				String targetName="overlayMap";
				String targetDesc="Ljava/util/Map;";
				ModLogger.log(Level.INFO, "looking for: Type("+AbstractInsnNode.FIELD_INSN+") Opcode("+Opcodes.GETSTATIC+")"+targetOwner+" "+targetName+" "+targetDesc);
				ArrayList<LabelNode> lbls=new ArrayList<LabelNode>();
				while (iter.hasNext())
				{
					index++;
					currentNode = iter.next();
					ModLogger.log(Level.INFO, "Node instruction type: "+currentNode.getType()+" opcode: "+currentNode.getOpcode());
					if (currentNode.getType() == AbstractInsnNode.LABEL){
						lbls.add((LabelNode)currentNode);
					}
					if (currentNode.getType() == targetInstructionType && currentNode.getOpcode() == targetOpcodeType)
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
							targetInsn_index=index;
							break;
						}
					}
				}
				//				ArrayList<AbstractInsnNode> removeThese=new ArrayList<AbstractInsnNode>();
				InsnList addBefore = new InsnList();
				InsnList addAfter = new InsnList();
				AbstractInsnNode beforeTarget=m.instructions.get(targetInsn_index-1);
				AbstractInsnNode afterTarget=m.instructions.get(targetInsn_index+5);
				//				removeThese.add(m.instructions.get(targetInsn_index+6));
				//				removeThese.add(m.instructions.get(targetInsn_index+7));
				//				removeThese.add(m.instructions.get(targetInsn_index+8));
				//				for(AbstractInsnNode n:removeThese){
				//					m.instructions.remove(n);
				//				}
				m.instructions.remove(m.instructions.get(targetInsn_index+6));
				if (needDeobf){
					addBefore.add(new VarInsnNode(Opcodes.ALOAD,0));
					addBefore.add(new FieldInsnNode(Opcodes.GETFIELD, "yd", "field_77993_c", "I"));
					addBefore.add(new FieldInsnNode(Opcodes.GETSTATIC,"carpentersblocks/util/handler/OverlayHandler", "overlayMap", "Ljava/util/Map;"));
					addBefore.add(new InsnNode(Opcodes.ICONST_1));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;"));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;"));
					addBefore.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Integer"));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I"));
					lbls.add(new LabelNode(new Label()));
					addBefore.add(new JumpInsnNode(Opcodes.IF_ICMPNE, lbls.get(1)));
					addBefore.add(new VarInsnNode(Opcodes.ALOAD, 0));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "yd", "func_77960_j", "()I"));
					addBefore.add(new InsnNode(Opcodes.ICONST_1));
					addBefore.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, lbls.get(1)));
					lbls.add(new LabelNode(new Label()));
					addBefore.add(lbls.get(2));
					addBefore.add(new LineNumberNode(303, lbls.get(2)));
					addBefore.add(new InsnNode(Opcodes.ICONST_0));
					addBefore.add(new InsnNode(Opcodes.IRETURN));
					addBefore.add(lbls.get(1));
					addBefore.add(new LineNumberNode(304, lbls.get(1)));
					addBefore.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
					lbls.add(new LabelNode(new Label()));
					addAfter.add(lbls.get(3));
					m.instructions.insert(beforeTarget, addBefore);
					m.instructions.insert(afterTarget, addAfter);
					m.localVariables.clear();
					m.localVariables.add(new LocalVariableNode("itemStack", "Lyd;", null, lbls.get(0), lbls.get(3), 0));
					m.maxStack=3;
				}else{
					addBefore.add(new VarInsnNode(Opcodes.ALOAD,0));
					addBefore.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/item/ItemStack", "itemID", "I"));
					addBefore.add(new FieldInsnNode(Opcodes.GETSTATIC,"carpentersblocks/util/handler/OverlayHandler", "overlayMap", "Ljava/util/Map;"));
					addBefore.add(new InsnNode(Opcodes.ICONST_1));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;"));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;"));
					addBefore.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Integer"));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I"));
					lbls.add(new LabelNode(new Label()));
					addBefore.add(new JumpInsnNode(Opcodes.IF_ICMPNE, lbls.get(1)));
					addBefore.add(new VarInsnNode(Opcodes.ALOAD, 0));
					addBefore.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getItemDamage", "()I"));
					addBefore.add(new InsnNode(Opcodes.ICONST_1));
					addBefore.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, lbls.get(1)));
					lbls.add(new LabelNode(new Label()));
					addBefore.add(lbls.get(2));
					addBefore.add(new LineNumberNode(303, lbls.get(2)));
					addBefore.add(new InsnNode(Opcodes.ICONST_0));
					addBefore.add(new InsnNode(Opcodes.IRETURN));
					addBefore.add(lbls.get(1));
					addBefore.add(new LineNumberNode(304, lbls.get(1)));
					addBefore.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
					lbls.add(new LabelNode(new Label()));
					addAfter.add(lbls.get(3));
					m.instructions.insert(beforeTarget, addBefore);
					m.instructions.insert(afterTarget, addAfter);
					m.localVariables.clear();
					m.localVariables.add(new LocalVariableNode("itemStack", "Lnet/minecraft/item/ItemStack;", null, lbls.get(0), lbls.get(3), 0));
					m.maxStack=3;
				}
//				this.logMethodNodes(m);
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();

		//		return bytes;
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
		// TODO Auto-generated method stub
		String targetMethodName= "getItemStack";
		String targetMethodDesc="(I)Lnet/minecraft/item/ItemStack;";
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
			int targetInsn_index = -1;
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
				this.logMethodNodes(m);
				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				int index = -1;
				int targetInstructionType=AbstractInsnNode.METHOD_INSN;
				int targetOpcodeType=Opcodes.INVOKESPECIAL;
				String targetOwner="net/minecraft/item/ItemStack";
				String targetName="<init>";
				String targetDesc="(III)V";
				ModLogger.log(Level.INFO, "looking for: Type("+AbstractInsnNode.FIELD_INSN+") Opcode("+Opcodes.GETSTATIC+")"+targetOwner+" "+targetName+" "+targetDesc);
				ArrayList<LabelNode> lbls=new ArrayList<LabelNode>();
				while (iter.hasNext())
				{
					index++;
					currentNode = iter.next();
					ModLogger.log(Level.INFO, "Node instruction type: "+currentNode.getType()+" opcode: "+currentNode.getOpcode());
					if (currentNode.getType() == AbstractInsnNode.LABEL){
						lbls.add((LabelNode)currentNode);
					}
					if (currentNode.getType() == targetInstructionType && currentNode.getOpcode() == targetOpcodeType)
					{
						MethodInsnNode fieldnode=(MethodInsnNode)currentNode;
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
							targetInsn_index=index;
							break;
						}
					}
				}
				//						ArrayList<AbstractInsnNode> removeThese=new ArrayList<AbstractInsnNode>();
				InsnList addBefore = new InsnList();
				InsnList addAfter = new InsnList();
				AbstractInsnNode targetnode=m.instructions.get(targetInsn_index-1);
				//						removeThese.add(m.instructions.get(targetInsn_index+6));
				//						removeThese.add(m.instructions.get(targetInsn_index+7));
				//						removeThese.add(m.instructions.get(targetInsn_index+8));
				//						for(AbstractInsnNode n:removeThese){
				//							m.instructions.remove(n);
				//						}
				m.instructions.remove(m.instructions.get(targetInsn_index+2));
				addBefore.add(new VarInsnNode(Opcodes.ILOAD, 0));
				addBefore.add(new InsnNode(Opcodes.ICONST_1));
				lbls.add(new LabelNode());
				addBefore.add(new JumpInsnNode(Opcodes.IF_ICMPNE,lbls.get(1)));
				addBefore.add(new InsnNode(Opcodes.ICONST_1));
				lbls.add(new LabelNode());
				addBefore.add(new JumpInsnNode(Opcodes.GOTO, lbls.get(2)));
				addBefore.add(lbls.get(1));
				addBefore.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 4, new Object[] {lbls.get(0), lbls.get(0), Opcodes.INTEGER, Opcodes.INTEGER}));
				addAfter.add(lbls.get(2));
				addAfter.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 5, new Object[] {lbls.get(0), lbls.get(0), Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER}));
				lbls.add(new LabelNode());
				m.instructions.insert(targetnode, addAfter);
				m.instructions.insertBefore(targetnode, addBefore);
				m.localVariables.clear();
				m.localVariables.add(new LocalVariableNode("overlay", "I", null, lbls.get(0), lbls.get(3), 0));
				m.maxStack=6;
				this.logMethodNodes(m);
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
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

	private void logMethodNodes(MethodNode m) {
		ModLogger.log(Level.INFO, "logging nodes for method "+m.name);
		Iterator<AbstractInsnNode> iter = m.instructions.iterator();
		AbstractInsnNode currentNode = null;
		int count=0;
		ArrayList<LabelNode> lbls= new ArrayList<LabelNode>();
		while (iter.hasNext()) {
			count++;
			currentNode = iter.next();
			switch (currentNode.getType()) {
			case AbstractInsnNode.LABEL: {
				LabelNode n = (LabelNode) currentNode;
				lbls.add(n);
				ModLogger.log(Level.INFO, "LabelNode l"+(lbls.size()-1));
				break;
			}
			case AbstractInsnNode.LINE: {
				LineNumberNode n = (LineNumberNode) currentNode;
				ModLogger.log(Level.INFO, "line "+n.line + " l"+ lbls.indexOf(n.start));
				break;
			}
			case AbstractInsnNode.FIELD_INSN: {
				FieldInsnNode n = (FieldInsnNode) currentNode;
				ModLogger.log(Level.INFO, "FIELD "+getOpcodeName(n.getOpcode())+" "+n.owner+" "+n.name+" "+n.desc);
				break;
			}
			case AbstractInsnNode.VAR_INSN: {
				VarInsnNode n = (VarInsnNode) currentNode;
				ModLogger.log(Level.INFO, "VAR "+ getOpcodeName(n.getOpcode())+" "+n.var);
				break;
			}
			case AbstractInsnNode.METHOD_INSN: {
				MethodInsnNode n = (MethodInsnNode) currentNode;
				ModLogger.log(Level.INFO, "METHOD "+getOpcodeName(n.getOpcode())+" "+n.owner+" "+n.name+" "+n.desc);
				break;
			}
			case AbstractInsnNode.INSN: {
				InsnNode n = (InsnNode) currentNode;
				ModLogger.log(Level.INFO, "INSN "+ getOpcodeName(n.getOpcode()));
				break;
			}
			case AbstractInsnNode.TYPE_INSN:{
				TypeInsnNode n = (TypeInsnNode) currentNode;
				ModLogger.log(Level.INFO, "TYPEINSN "+ getOpcodeName(n.getOpcode())+" "+n.desc);
				break;
			}
			case AbstractInsnNode.JUMP_INSN:{
				JumpInsnNode n = (JumpInsnNode) currentNode;
				ModLogger.log(Level.INFO, "JUMPINSN "+ getOpcodeName(n.getOpcode())+" l"+ lbls.indexOf(n.label));
				break;
			}
			case AbstractInsnNode.FRAME:{
				FrameNode n = (FrameNode) currentNode;
				int localsize=0;
				int stacksize=0;
				if(n.local != null){
					localsize=n.local.size();
				}
				if(n.stack != null){
					stacksize=n.stack.size();
				}
				ModLogger.log(Level.INFO,"FRAME "+getOpcodeName(n.getOpcode())+" "+localsize+" - "+stacksize+" - ");
				break;
			}
			default: {
				ModLogger.log(Level.INFO, currentNode.toString());
				break;
			}
			}
		}
		ModLogger.log(Level.INFO, "logged "+count+" nodes");
	}
	private String getOpcodeName(int code){
		switch(code){
		case Opcodes.ALOAD: return "ALOAD";
		case Opcodes.ARETURN: return "ARETURN";
		case Opcodes.GETSTATIC: return "GETSTATIC";
		case Opcodes.GETFIELD: return "GETFIELD";
		case Opcodes.INVOKESTATIC: return "INVOKESTATIC";
		case Opcodes.INVOKEINTERFACE: return "INVOKEINTERFACE";
		case Opcodes.INVOKEVIRTUAL: return "INVOKEVIRTUAL";
		case Opcodes.INVOKESPECIAL: return "INVOKESPECIAL";
		case Opcodes.IRETURN: return "IRETURN";
		case Opcodes.ICONST_1: return "ICONST_1";
		case Opcodes.CHECKCAST: return "CHECKCAST";
		case Opcodes.F_SAME: return "F_SAME";
		case Opcodes.IF_ICMPEQ: return "IF_ICMPEQ";
		case Opcodes.IF_ICMPNE: return "IF_ICMPNE";
		case Opcodes.NEW: return "NEW";
		case Opcodes.DUP: return "DUP";
		case Opcodes.ILOAD: return "ILOAD";
		case Opcodes.GOTO: return "GOTO";
		default: return ""+code;
		}
	}
}
