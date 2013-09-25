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
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

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
//			return patchBlockProperties(arg0,arg2);
		}
		if(arg0.equals("carpentersblocks.util.handler.OverlayHandler")){
			ModLogger.log(Level.INFO, "identified OverlayHandler class for modification ");
//			return patchOverlayHandler(arg0,arg2);
		}
		return arg2;
	}
	private byte[] patchBlockProperties(String name, byte[] bytes){
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
//				this.logMethodNodes(m);
				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				int index = -1;
				int targetInstructionType=AbstractInsnNode.METHOD_INSN;
				int targetOpcodeType=Opcodes.INVOKESPECIAL;
				String targetOwner="net/minecraft/item/ItemStack";
				String targetName="<init>";
				String targetDesc="(III)V";
				ModLogger.log(Level.INFO, "looking for: Type("+AbstractInsnNode.FIELD_INSN+") Opcode("+Opcodes.GETSTATIC+")"+targetOwner+" "+targetName+" "+targetDesc);
				while (iter.hasNext())
				{
					index++;
					currentNode = iter.next();
					ModLogger.log(Level.INFO, "Node instruction type: "+currentNode.getType()+" opcode: "+currentNode.getOpcode());
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
				LabelNode l0,l1,l2,l3;
				l0=m.localVariables.get(0).start;
				l3=m.localVariables.get(0).end;
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
				l1=new LabelNode(new Label());
				addBefore.add(new JumpInsnNode(Opcodes.IF_ICMPNE,l1));
				addBefore.add(new InsnNode(Opcodes.ICONST_1));
				l2=new LabelNode(new Label());
				addBefore.add(new JumpInsnNode(Opcodes.GOTO, l2));
				addBefore.add(l1);
				addBefore.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 4, new Object[] {l0, l0, Opcodes.INTEGER, Opcodes.INTEGER}));
				addAfter.add(l2);
				addAfter.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 5, new Object[] {l0, l0, Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER}));
				l3=new LabelNode(new Label());
				m.instructions.insert(targetnode, addAfter);
				m.instructions.insertBefore(targetnode, addBefore);
//				m.localVariables.clear();
//				m.localVariables.add(new LocalVariableNode("overlay", "I", null, l0, l3, 0));
				m.maxStack=6;
//				this.logMethodNodes(m);
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
				MethodInsnNode target1,target2,target3;
				FieldInsnNode target4;
				if(needDeobf){
					//TODO OBF NAMES
					target1=new MethodInsnNode (Opcodes.INVOKESTATIC,"carpentersblocks/util/BlockProperties","isCover","(Lnet/minecraft/item/ItemStack;)Z");
					target2=new MethodInsnNode (Opcodes.INVOKESTATIC,"carpentersblocks/util/BlockProperties","setCover","(Lcarpentersblocks/tileentity/TECarpentersBlock;ILnet/minecraft/item/ItemStack;)Z");
					target3=new MethodInsnNode (Opcodes.INVOKESTATIC,"carpentersblocks/util/BlockProperties","isOverlay","(Lnet/minecraft/item/ItemStack;)Z");
					target4=new FieldInsnNode(Opcodes.GETSTATIC,"carpentersblocks/util/handler/FeatureHandler","enableDyeColors","Z");	
				}else{
					target1=new MethodInsnNode (Opcodes.INVOKESTATIC,"carpentersblocks/util/BlockProperties","isCover","(Lnet/minecraft/item/ItemStack;)Z");
					target2=new MethodInsnNode (Opcodes.INVOKESTATIC,"carpentersblocks/util/BlockProperties","setCover","(Lcarpentersblocks/tileentity/TECarpentersBlock;ILnet/minecraft/item/ItemStack;)Z");
					target3=new MethodInsnNode (Opcodes.INVOKESTATIC,"carpentersblocks/util/BlockProperties","isOverlay","(Lnet/minecraft/item/ItemStack;)Z");
					target4=new FieldInsnNode(Opcodes.GETSTATIC,"carpentersblocks/util/handler/FeatureHandler","enableDyeColors","Z");
				}
				int[] targetsFound=new int[]{0,0,0,0};
				logMethodNodes(m);
				ModLogger.log(Level.INFO, "target1: Type("+target1.getType()+") Opcode("+target1.getOpcode()+") "+target1.owner+" "+target1.name+" "+target1.desc);
				ModLogger.log(Level.INFO, "target2: Type("+target2.getType()+") Opcode("+target2.getOpcode()+") "+target2.owner+" "+target2.name+" "+target2.desc);
				ModLogger.log(Level.INFO, "target3: Type("+target3.getType()+") Opcode("+target3.getOpcode()+") "+target3.owner+" "+target3.name+" "+target3.desc);
				ModLogger.log(Level.INFO, "target4: Type("+target4.getType()+") Opcode("+target4.getOpcode()+") "+target4.owner+" "+target4.name+" "+target4.desc);
				while (iter.hasNext())
				{
					index++;
					currentNode = iter.next();
//					this.logNode(currentNode);
					
					if (targetsFound[0] != 1 && this.CompareNodes(currentNode, target1))
					{
						targetsFound[0]=targetsFound[0]+1;
						ModLogger.log(Level.INFO, "target1 bytecode instruction found");
						((MethodInsnNode)currentNode).owner="tfc_carpentersblocks_adapter/coremod/util/ReplacementFunctions";
						((MethodInsnNode)currentNode).name="isCover";
						if(needDeobf){
							//TODO obfuscated desc
							((MethodInsnNode)currentNode).desc="(Lnet/minecraft/item/ItemStack;)Z";
						} else {
							((MethodInsnNode)currentNode).desc="(Lnet/minecraft/item/ItemStack;)Z";
						}
					} else if(targetsFound[1] != 2 && this.CompareNodes(currentNode, target2)){
						ModLogger.log(Level.INFO, "target2 bytecode instruction found");
						targetsFound[1]=targetsFound[1]+1;
						((MethodInsnNode)currentNode).owner="tfc_carpentersblocks_adapter/coremod/util/ReplacementFunctions";
						((MethodInsnNode)currentNode).name="setCover";
						if(needDeobf){
							//TODO obfuscated desc
							((MethodInsnNode)currentNode).desc="(Lcarpentersblocks/tileentity/TECarpentersBlock;ILnet/minecraft/item/ItemStack;)Z";
						} else {
							((MethodInsnNode)currentNode).desc="(Lcarpentersblocks/tileentity/TECarpentersBlock;ILnet/minecraft/item/ItemStack;)Z";
						}
					} else if(targetsFound[2] != 1 && this.CompareNodes(currentNode, target3)){
						ModLogger.log(Level.INFO, "target3 bytecode instruction found");
						targetsFound[2]=targetsFound[2]+1;
						((MethodInsnNode)currentNode).owner="tfc_carpentersblocks_adapter/coremod/util/ReplacementFunctions";
						((MethodInsnNode)currentNode).name="isOverlay";
						if(needDeobf){
							//TODO obfuscated desc
							((MethodInsnNode)currentNode).desc="(Lnet/minecraft/item/ItemStack;)Z";
						} else {
							((MethodInsnNode)currentNode).desc="(Lnet/minecraft/item/ItemStack;)Z";
						}
					} else if(targetsFound[3] != 1 && this.CompareNodes(currentNode, target4)){
						ModLogger.log(Level.INFO, "target4 bytecode instruction found");
						targetsFound[3]=targetsFound[3]+1;
						MethodInsnNode mnode=(MethodInsnNode)m.instructions.get(index+3);
						mnode.setOpcode(Opcodes.INVOKESTATIC);
						mnode.owner="tfc_carpentersblocks_adapter/coremod/util/ReplacementFunctions";
						mnode.name="isValidDye";
						JumpInsnNode jnode=(JumpInsnNode)m.instructions.get(index+5);
						jnode.setOpcode(Opcodes.IFEQ);
						m.instructions.remove(m.instructions.get(index+4));
						if(needDeobf){
							//TODO obf name
							mnode.desc="(Lnet/minecraft/item/ItemStack;)Z";
						} else {
							mnode.desc="(Lnet/minecraft/item/ItemStack;)Z";
						}
						
					}
					//if found all targets, stop looking
					if(targetsFound[0]==1 && targetsFound[1]==2 && targetsFound[2]==1 && targetsFound[3]==1){
						break;
					}
				}
				
				logMethodNodes(m);
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	private boolean CompareNodes(AbstractInsnNode a,AbstractInsnNode b){
//		ModLogger.log(Level.INFO, "Comparing nodes "+ a.toString() +" & "+ b.toString());
		if(a.getType() != b.getType())
			return false;
		if(a.getOpcode() != b.getOpcode())
			return false;
		switch(a.getType()){
		case AbstractInsnNode.FIELD_INSN:{
			FieldInsnNode na=(FieldInsnNode)deobf(a);
			FieldInsnNode nb=(FieldInsnNode)deobf(b);
			return na.owner.equals(nb.owner) && na.name.equals(nb.name) && na.desc.equals(nb.desc);
			}
		case AbstractInsnNode.METHOD_INSN:{
			MethodInsnNode na=(MethodInsnNode)deobf(a);
			MethodInsnNode nb=(MethodInsnNode)deobf(b);
			return na.owner.equals(nb.owner) && na.name.equals(nb.name) && na.desc.equals(nb.desc);
			}
		case AbstractInsnNode.TYPE_INSN:{
			TypeInsnNode na=(TypeInsnNode)deobf(a);
			TypeInsnNode nb=(TypeInsnNode)deobf(b);
			return na.desc.equals(nb.desc);
			}
		default:
			return false;
		}
	}
	private AbstractInsnNode deobf(AbstractInsnNode obf){
		boolean needDeobf=tfc_carpentersblocks_adapter.coremod.TFC_CarpBlock_IFMLLoadingPlugin.runtimeDeobf;
		if(!needDeobf){
			return obf;
		}
		FMLDeobfuscatingRemapper mapper=FMLDeobfuscatingRemapper.INSTANCE;
		String owner,name,desc;
		switch(obf.getType()){
		case AbstractInsnNode.FIELD_INSN:{
			FieldInsnNode n=(FieldInsnNode)obf;
			owner=mapper.map(n.owner);
			name=mapper.mapFieldName(n.owner, n.name, n.desc);
			desc=mapper.mapDesc(n.desc);
			return new FieldInsnNode (n.getOpcode(),owner,name,desc);
			}
		case AbstractInsnNode.METHOD_INSN:{
			MethodInsnNode n=(MethodInsnNode)obf;
			owner=mapper.map(n.owner);
			name=mapper.mapMethodName(n.owner, n.name, n.desc);
			desc=mapper.mapMethodDesc(n.desc);
			return new MethodInsnNode(n.getOpcode(),owner,name,desc);
			}
		case AbstractInsnNode.TYPE_INSN:{
			TypeInsnNode n=(TypeInsnNode)obf;
			desc=mapper.mapDesc(n.desc);
			return new TypeInsnNode(n.getOpcode(),desc);
			}
		default:
			return obf;
		}
	}
	private void logNode(AbstractInsnNode a) {

		switch(a.getType()){
		case AbstractInsnNode.FIELD_INSN:{
			FieldInsnNode n=(FieldInsnNode)deobf(a);
			ModLogger.log(Level.INFO, "Node Type("+n.getType()+") Opcode("+n.getOpcode()+") "+n.owner+" "+n.name+" "+n.desc);
			break;
			}
		case AbstractInsnNode.METHOD_INSN:{
			MethodInsnNode n=(MethodInsnNode)deobf(a);
			ModLogger.log(Level.INFO, "Node Type("+n.getType()+") Opcode("+n.getOpcode()+") "+n.owner+" "+n.name+" "+n.desc);
			break;
			}
		case AbstractInsnNode.TYPE_INSN:{
			TypeInsnNode n=(TypeInsnNode)deobf(a);
			ModLogger.log(Level.INFO, "Node Type("+n.getType()+") Opcode("+n.getOpcode()+") "+n.desc);
			break;
			}
		default:
			ModLogger.log(Level.INFO, "Node Type("+a.getType()+") Opcode("+a.getOpcode()+") "+a.toString());
			break;
		}
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
				ModLogger.log(Level.INFO, "LabelNode l"+n.getLabel().info);
				break;
			}
			case AbstractInsnNode.LINE: {
				LineNumberNode n = (LineNumberNode) currentNode;
				ModLogger.log(Level.INFO, "line "+n.line + " l"+ n.start.getLabel().info);
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
				ModLogger.log(Level.INFO, "JUMPINSN "+ getOpcodeName(n.getOpcode())+" l"+ n.label.getLabel().info);
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
		case Opcodes.IF_ACMPNE: return "IF_ACMPNE";
		default: return ""+code;
		}
	}
}
