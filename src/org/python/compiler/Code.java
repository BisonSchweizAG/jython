package org.python.compiler;

import java.util.BitSet;
import java.util.Vector;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Code
 */
public class Code extends MethodVisitor implements Opcodes {
    MethodVisitor mv;
    String sig;
    String locals[];
    int nlocals;
    int argcount;
    int returnLocal;
    BitSet finallyLocals = new java.util.BitSet();
    
    /**
     * Constructor<br>
     * XXX: I'd really like to get sig and access out of here since MethodVistitor<br>
     * should already have this information.
     * 
     * @param mv
     *            mv
     * @param sig
     *            sig
     * @param access
     *            access
     */
    public Code(MethodVisitor mv, String sig, int access) {
        super(ASM7);
        this.mv = mv;
        this.sig = sig;
        nlocals = -sigSize(sig, false);
        if ((access & ACC_STATIC) != ACC_STATIC) nlocals = nlocals+1;
        argcount = nlocals;
        locals = new String[nlocals+128];
    }
    
    /**
     * GetLocal
     * 
     * @param type
     *            type
     * @return i
     */
    public int getLocal(String type) {
        //Could optimize this to skip arguments?
        for(int l = argcount; l<nlocals; l++) {
            if (locals[l] == null) {
                locals[l] = type;
                return l;
            }
        }
        if (nlocals >= locals.length) {
            String[] new_locals = new String[locals.length*2];
            System.arraycopy(locals, 0, new_locals, 0, locals.length);
            locals = new_locals;
        }
        locals[nlocals] = type;
        nlocals += 1;
        return nlocals-1;
    }

    /**
     * Freelocal
     * 
     * @param l
     *            l
     */
    public void freeLocal(int l) {
        if (locals[l] == null) {
            System.out.println("Double free:" + l);
        }
        locals[l] = null;
    }


    /**
     * GetFinallyLocal
     * 
     * @param type
     *            type
     * @return i
     */
    public int getFinallyLocal(String type) {
        int l = getLocal(type);
        finallyLocals.set(l);
        return l;
    }

    /**
     * FreeFinallyLocal
     * 
     * @param l
     *            l
     */
    public void freeFinallyLocal(int l) {
        finallyLocals.clear(l);
        freeLocal(l);
    }

    /**
     * GetReturnLocal
     * 
     * @return i
     */
    public int getReturnLocal() {
        if (returnLocal == 0)
            returnLocal = getLocal("return");
        return returnLocal;
    }

    /**
     * GetActiveLocals
     * 
     * @return vector
     */
    public Vector<String> getActiveLocals() {
        Vector<String> ret = new Vector<String>();
        ret.setSize(nlocals);
        for (int l = argcount; l<nlocals; l++) {
            if (l == returnLocal || finallyLocals.get(l))
                continue;
            ret.setElementAt(locals[l], l);
        }
        return ret;
    }

    public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
        return mv.visitAnnotation(arg0, arg1);
    }

    public AnnotationVisitor visitAnnotationDefault() {
        return mv.visitAnnotationDefault();
    }

    public void visitAttribute(Attribute arg0) {
        mv.visitAttribute(arg0);
    }

    public void visitCode() {
        mv.visitCode();
    }

    public void visitEnd() {
        mv.visitEnd();
    }

    public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
        mv.visitFieldInsn(arg0, arg1, arg2, arg3);
    }

    public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3, Object[] arg4) {
        mv.visitFrame(arg0, arg1, arg2, arg3, arg4);
    }

    public void visitIincInsn(int arg0, int arg1) {
        mv.visitIincInsn(arg0, arg1);
    }

    public void visitInsn(int arg0) {
        mv.visitInsn(arg0);
    }

    public void visitIntInsn(int arg0, int arg1) {
        mv.visitIntInsn(arg0, arg1);
    }

    public void visitJumpInsn(int arg0, Label arg1) {
        mv.visitJumpInsn(arg0, arg1);
    }

    public void visitLabel(Label arg0) {
        mv.visitLabel(arg0);
    }

    public void visitLdcInsn(Object arg0) {
        mv.visitLdcInsn(arg0);
    }

    public void visitLineNumber(int arg0, Label arg1) {
        mv.visitLineNumber(arg0, arg1);
    }

    public void visitLocalVariable(String arg0, String arg1, String arg2, Label arg3, Label arg4, int arg5) {
        mv.visitLocalVariable(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
        mv.visitLookupSwitchInsn(arg0, arg1, arg2);
    }

    public void visitMaxs(int arg0, int arg1) {
        mv.visitMaxs(arg0, arg1);
    }

    public void visitMethodInsn(int arg0, String arg1, String arg2, String arg3, boolean itf) {
        mv.visitMethodInsn(arg0, arg1, arg2, arg3, itf);
    }

    public void visitMultiANewArrayInsn(String arg0, int arg1) {
        mv.visitMultiANewArrayInsn(arg0, arg1);
    }

    public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1, boolean arg2) {
        return mv.visitParameterAnnotation(arg0, arg1, arg2);
    }

    public void visitTableSwitchInsn(int arg0, int arg1, Label arg2, Label... arg3) {
        mv.visitTableSwitchInsn(arg0, arg1, arg2, arg3);
    }


    public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2, String arg3) {
        mv.visitTryCatchBlock(arg0, arg1, arg2, arg3);
    }

    public void visitTypeInsn(int arg0, String arg1) {
        mv.visitTypeInsn(arg0, arg1);
    }

    public void visitVarInsn(int arg0, int arg1) {
        mv.visitVarInsn(arg0, arg1);
    }

    @SuppressWarnings("fallthrough")
    private int sigSize(String sig, boolean includeReturn) {
        int stack = 0;
        int i = 0;
        char[] c = sig.toCharArray();
        int n = c.length;
        boolean ret=false;
        boolean array=false;

        while (++i<n) {
            switch (c[i]) {
            case ')':
                if (!includeReturn)
                    return stack;
                ret=true;
                continue;
            case '[':
                array=true;
                continue;
            case 'V':
                continue;
            case 'D':
            case 'J':
                if (array) {
                    if (ret) stack += 1;
                    else stack -=1;
                    array = false;
                } else {
                    if (ret) stack += 2;
                    else stack -=2;
                }
                break;
            case 'L':
                while (c[++i] != ';') {}
            default:
                if (ret) stack++;
                else stack--;
                array = false;
            }
        }
        return stack;
    }

    /**
     * Aaload
     */
    public void aaload() {
        mv.visitInsn(AALOAD);
    }
    
    /**
     * Aastore
     */
    public void aastore() {
        mv.visitInsn(AASTORE);
    }

    /**
     * Aconst_null
     */
    public void aconst_null() {
        mv.visitInsn(ACONST_NULL);
    }

    /**
     * Aload
     * 
     * @param index
     *            index
     */
    public void aload(int index) {
        mv.visitVarInsn(ALOAD, index);
    }

    /**
     * Anewarray
     * 
     * @param index
     *            index
     */
    public void anewarray(String index) {
        mv.visitTypeInsn(ANEWARRAY, index);
    }

    /**
     * Areturn
     */
    public void areturn() {
        mv.visitInsn(ARETURN);
    }

    /**
     * Arraylength
     */
    public void arraylength() {
        mv.visitInsn(ARRAYLENGTH);
    }

    /**
     * Astore
     * 
     * @param index
     *            index
     */
    public void astore(int index) {
        mv.visitVarInsn(ASTORE, index);
    }

    /**
     * Athrow
     */
    public void athrow() {
        mv.visitInsn(ATHROW);
    }

    /**
     * Baload
     */
    public void baload() {
        mv.visitInsn(BALOAD);
    }

    /**
     * Bastore
     */
    public void bastore() {
        mv.visitInsn(BASTORE);
    }

    /**
     * Bipush
     * 
     * @param value
     *            valiue
     */
    public void bipush(int value) {
        mv.visitIntInsn(BIPUSH, value);
    }

    /**
     * Checkcast
     * 
     * @param type
     *            type
     */
    public void checkcast(String type) {
        mv.visitTypeInsn(CHECKCAST, type);
    }

    /**
     * Dconst_0
     */
    public void dconst_0() {
        mv.visitInsn(DCONST_0);
    }

    /**
     * Dload
     * 
     * @param index
     *            index
     */
    public void dload(int index) {
        mv.visitVarInsn(DLOAD, index);
    }

    /**
     * Dreturn
     */
    public void dreturn() {
        mv.visitInsn(DRETURN);
    }

    /**
     * Dup
     */
    public void dup() {
        mv.visitInsn(DUP);
    }

    /**
     * Dup2
     */
    public void dup2() {
        mv.visitInsn(DUP2);
    }
 
    /**
     * Dup_x1
     */
    public void dup_x1() {
        mv.visitInsn(DUP_X1);
    }

    /**
     * Dup_x2
     */
    public void dup_x2() {
        mv.visitInsn(DUP_X2);
    }

    /**
     * Dup2_x1
     */
    public void dup2_x1() {
        mv.visitInsn(DUP2_X1);
    }

    /**
     * Dup2_x2
     */
    public void dup2_x2() {
        mv.visitInsn(DUP2_X2);
    }

    /**
     * Fconst_0
     */
    public void fconst_0() {
        mv.visitInsn(FCONST_0);
    }
 
    /**
     * Fload
     * 
     * @param index
     *            index
     */
    public void fload(int index) {
        mv.visitVarInsn(FLOAD, index);
    }

    /**
     * Freturn
     */
    public void freturn() {
        mv.visitInsn(FRETURN);
    }

    /**
     * Getfield
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void getfield(String owner, String name, String type) {
        mv.visitFieldInsn(GETFIELD, owner, name, type);
    }

    /**
     * Getstatic
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void getstatic(String owner, String name, String type) {
        mv.visitFieldInsn(GETSTATIC, owner, name, type);
    }

    /**
     * Goto_
     * 
     * @param label
     *            label
     */
    public void goto_(Label label) {
        mv.visitJumpInsn(GOTO, label);
    }
  
    /**
     * Iconst
     * 
     * @param value
     *            value
     */
    public void iconst(int value) {
        if (value <= Byte.MAX_VALUE && value >= Byte.MIN_VALUE) {
            switch (value) {
            case -1:
                iconst_m1();
                break;
            case 0:
                iconst_0();
                break;
            case 1:
                iconst_1();
                break;
            case 2:
                iconst_2();
                break;
            case 3:
                iconst_3();
                break;
            case 4:
                iconst_4();
                break;
            case 5:
                iconst_5();
                break;
            default:
                bipush(value);
                break;
            }
        } else if (value <= Short.MAX_VALUE && value >= Short.MIN_VALUE) {
            sipush(value);
        } else {
            ldc(value);
        }
    }

    /**
     * Iconst_m1
     */
    public void iconst_m1() {
        mv.visitInsn(ICONST_M1);
    }
    
    /**
     * Iconst_0
     */
    public void iconst_0() {
        mv.visitInsn(ICONST_0);
    }
    
    /**
     * Iconst_1
     */
    public void iconst_1() {
        mv.visitInsn(ICONST_1);
    }
    
    /**
     * Iconst_2
     */
    public void iconst_2() {
        mv.visitInsn(ICONST_2);
    }
    
    /**
     * Iconst_3
     */
    public void iconst_3() {
        mv.visitInsn(ICONST_3);
    }
    
    /**
     * Iconst_4
     */
    public void iconst_4() {
        mv.visitInsn(ICONST_4);
    }
    
    /**
     * Iconst_5
     */
    public void iconst_5() {
        mv.visitInsn(ICONST_5);
    }
    
    /**
     * Ifeq
     * 
     * @param label
     *            label
     */
    public void ifeq(Label label) {
        mv.visitJumpInsn(IFEQ, label);
    }

    /**
     * Ifle
     * 
     * @param label
     *            label
     */
    public void ifle(Label label) {
        mv.visitJumpInsn(IFLE, label);
    }
     
    /**
     * Ifne
     * 
     * @param label
     *            label
     */
    public void ifne(Label label) {
        mv.visitJumpInsn(IFNE, label);
    }

    /**
     * Ifnull
     * 
     * @param label
     *            label
     */
    public void ifnull(Label label) {
        mv.visitJumpInsn(IFNULL, label);
    }

    /**
     * Ifnonnull
     * 
     * @param label
     *            label
     */
    public void ifnonnull(Label label) {
        mv.visitJumpInsn(IFNONNULL, label);
    }
     
    /**
     * If_acmpne
     * 
     * @param label
     *            label
     */
    public void if_acmpne(Label label) {
        mv.visitJumpInsn(IF_ACMPNE, label);
    }
    
    /**
     * If_acmpeq
     * 
     * @param label
     *            label
     */
    public void if_acmpeq(Label label) {
        mv.visitJumpInsn(IF_ACMPEQ, label);
    }
    
    /**
     * If_icmple
     * 
     * @param label
     *            label
     */
    public void if_icmple(Label label) {
        mv.visitJumpInsn(IF_ICMPLE, label);
    }
    
    /**
     * If_icmpgt
     * 
     * @param label
     *            label
     */
    public void if_icmpgt(Label label) {
        mv.visitJumpInsn(IF_ICMPGT, label);
    }
    
    /**
     * If_icmplt
     * 
     * @param label
     *            label
     */
    public void if_icmplt(Label label) {
        mv.visitJumpInsn(IF_ICMPLT, label);
    }
    
    /**
     * If_icmpne
     * 
     * @param label
     *            label
     */
    public void if_icmpne(Label label) {
        mv.visitJumpInsn(IF_ICMPNE, label);
    }
    
    /**
     * If_icmpeq
     * 
     * @param label
     *            label
     */
    public void if_icmpeq(Label label) {
        mv.visitJumpInsn(IF_ICMPEQ, label);
    }

    /**
     * Iadd
     */
    public void iadd() {
        mv.visitInsn(IADD);
    }

    /**
     * Iaload
     */
    public void iaload() {
        mv.visitInsn(IALOAD);
    }

    /**
     * Iinc
     */
    public void iinc() {
        mv.visitInsn(IINC);
    }

    /**
     * Iload
     * 
     * @param index
     *            index
     */
    public void iload(int index) {
        mv.visitVarInsn(ILOAD, index);
    }

    /**
     * instanceof
     * 
     * @param type
     *            type
     */
    public void instanceof_(String type) {
        mv.visitTypeInsn(INSTANCEOF, type);
    }

    /**
     * Invokeinterface
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void invokeinterface(String owner, String name, String type) {
        mv.visitMethodInsn(INVOKEINTERFACE, owner, name, type, false);
    }

    /**
     * Invokeinterface
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     * @param itf
     *            itf
     */
    public void invokeinterface(String owner, String name, String type, boolean itf) {
        mv.visitMethodInsn(INVOKEINTERFACE, owner, name, type, itf);
    }

    /**
     * Invokespecial
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void invokespecial(String owner, String name, String type) {
        mv.visitMethodInsn(INVOKESPECIAL, owner, name, type, false);
    }

    /**
     * Invokestatic
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void invokestatic(String owner, String name, String type) {
        mv.visitMethodInsn(INVOKESTATIC, owner, name, type, false);
    }
    
    /**
     * Invokevirtual
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void invokevirtual(String owner, String name, String type) {
        mv.visitMethodInsn(INVOKEVIRTUAL, owner, name, type, false);
    }
    
    /**
     * Ireturn
     */
    public void ireturn() {
        mv.visitInsn(IRETURN);
    }
 
    /**
     * Istore
     * 
     * @param index
     *            index
     */
    public void istore(int index) {
        mv.visitVarInsn(ISTORE, index);
    }

    /**
     * Isub
     */
    public void isub() {
        mv.visitInsn(ISUB);
    }

    /**
     * Label
     * 
     * @param label
     *            label
     */
    public void label(Label label) {
        mv.visitLabel(label);
    }

    /**
     * Lconst_0
     */
    public void lconst_0() {
        mv.visitInsn(LCONST_0);
    }

    /**
     * Ldc
     * 
     * @param cst
     *            cst
     */
    public void ldc(Object cst) {
        if (cst instanceof String) {
            String value = (String) cst;
            final int len = value.length();
            // 65535 / 4 (max utf-8 expansion for non BMP characters)
            final int maxlen = 16000; 

            if (len > maxlen) {
                new_("java/lang/StringBuilder");
                dup();
                iconst(len);
                invokespecial("java/lang/StringBuilder", "<init>", "(I)V");
                for (int i = 0; i < len; i += maxlen) {
                    int j = i + maxlen;
                    if (j > len) {
                        j = len;
                    }
                    mv.visitLdcInsn(value.substring(i, j));
                    invokevirtual("java/lang/StringBuilder", "append", "(Ljava/lang/String;)" + "Ljava/lang/StringBuilder;");
                }
                invokevirtual("java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
            } else {
                mv.visitLdcInsn(value);
            }
        } else {
            mv.visitLdcInsn(cst);
        }
    }

    /**
     * Lload
     * 
     * @param index
     *            index
     */
    public void lload(int index) {
        mv.visitVarInsn(LLOAD, index);
    }

    /**
     * Lreturn
     */
    public void lreturn() {
        mv.visitInsn(LRETURN);
    }

    /**
     * Newarray
     * 
     * @param atype
     *            atype
     */
    public void newarray(int atype) {
        mv.visitIntInsn(NEWARRAY, atype);
    }

    /**
     * new_
     * 
     * @param type
     *            type
     */
    public void new_(String type) {
        mv.visitTypeInsn(NEW, type);
    }

    /**
     * Nop
     */
    public void nop() {
        mv.visitInsn(NOP);
    }

    /**
     * Pop
     */
    public void pop() {
        mv.visitInsn(POP);
    }
    
    /**
     * Pop2
     */
    public void pop2() {
        mv.visitInsn(POP2);
    }

    /**
     * Putstatic
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void putstatic(String owner, String name, String type) {
        mv.visitFieldInsn(PUTSTATIC, owner, name, type);
    }
    
    /**
     * Putfield
     * 
     * @param owner
     *            owner
     * @param name
     *            name
     * @param type
     *            type
     */
    public void putfield(String owner, String name, String type) {
        mv.visitFieldInsn(PUTFIELD, owner, name, type);
    }
 
    /**
     * ret
     * 
     * @param index
     *            index
     */
    public void ret(int index) {
        mv.visitVarInsn(RET, index);
    }

    /**
     * return_
     */
    public void return_() {
        mv.visitInsn(RETURN);
    }

    /**
     * Sipush
     * 
     * @param value
     *            value
     */
    public void sipush(int value) {
        mv.visitIntInsn(SIPUSH, value);
    }

    /**
     * Swap
     */
    public void swap() {
        mv.visitInsn(SWAP);
    }
 
    /**
     * Swap2
     */
    public void swap2() {
        dup2_x2();
        pop2();
    }

    /**
     * Tableswitch
     * 
     * @param arg0
     *            arg0
     * @param arg1
     *            arg1
     * @param arg2
     *            arg2
     * @param arg3
     *            arg3
     */
    public void tableswitch(int arg0, int arg1, Label arg2, Label[] arg3) {
        mv.visitTableSwitchInsn(arg0, arg1, arg2, arg3);
    }

    /**
     * Trycatch
     * 
     * @param start
     *            start
     * @param end
     *            end
     * @param handlerStart
     *            handlerStart
     * @param type
     *            type
     */
    public void trycatch(Label start, Label end, Label handlerStart, String type) {
        mv.visitTryCatchBlock(start, end, handlerStart, type);
    }
    
    /**
     * Setline
     * 
     * @param line
     *            line
     */
    public void setline(int line) {
        mv.visitLineNumber(line, new Label());
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bsmHandle, 
                                       Object... bmsArgs) {
        mv.visitInvokeDynamicInsn(name, descriptor, bsmHandle, bmsArgs);
    }
    
}
