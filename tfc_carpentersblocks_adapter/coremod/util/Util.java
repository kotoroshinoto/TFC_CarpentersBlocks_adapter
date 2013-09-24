package tfc_carpentersblocks_adapter.coremod.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import scala.tools.asm.Opcodes;
import scala.tools.asm.tree.*;
import tfc_carpentersblocks_adapter.mod.util.ModLogger;

public class Util {
	public static boolean CompareNodes(AbstractInsnNode a,AbstractInsnNode b){
		ModLogger.log(Level.INFO, "Comparing nodes "+ a.toString() +" & "+ b.toString());
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
	public static AbstractInsnNode deobf(AbstractInsnNode obf){
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
	public static void logNode(AbstractInsnNode a) {

		switch(a.getType()){
		case AbstractInsnNode.FIELD_INSN:{
			FieldInsnNode n=(FieldInsnNode)deobf(a);
			ModLogger.log(Level.INFO, "Node Type("+n.getType()+") Opcode("+n.getOpcode()+") "+n.owner+" "+n.name+" "+n.desc);
			}
		case AbstractInsnNode.METHOD_INSN:{
			MethodInsnNode n=(MethodInsnNode)deobf(a);
			ModLogger.log(Level.INFO, "Node Type("+n.getType()+") Opcode("+n.getOpcode()+") "+n.owner+" "+n.name+" "+n.desc);
			}
		case AbstractInsnNode.TYPE_INSN:{
			TypeInsnNode n=(TypeInsnNode)deobf(a);
			ModLogger.log(Level.INFO, "Node Type("+n.getType()+") Opcode("+n.getOpcode()+") "+n.desc);
			}
		default:
			ModLogger.log(Level.INFO, "Node Type("+a.getType()+") Opcode("+a.getOpcode()+") "+a.toString());
		}
	}
	public static void logMethodNodes(MethodNode m) {
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
	public static String getOpcodeName(int code){
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
