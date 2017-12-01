/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.mutators;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;


public enum UOIMutator2 implements MethodMutatorFactory {

  UOI_MUTATOR2;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new UOIMethodVisitor2(this,context,methodVisitor);
  }

  @Override
  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  @Override
  public String getName() {
    return name();
  }

}

class UOIMethodVisitor2 extends MethodVisitor {
  private final MutationContext context;
  private final MethodMutatorFactory factory;

  UOIMethodVisitor2(final MethodMutatorFactory factory, final MutationContext context,
       final MethodVisitor methodvisitor) {
    super(Opcodes.ASM6,methodvisitor);
    this.context = context;
    this.factory = factory;
}

  @Override
  public void visitVarInsn(final int opcode, final int var) {

      final MutationIdentifier mutationId = this.context.registerMutation(
          this.factory, "negate variable ");
      if (this.context.shouldMutate(mutationId)) {
      
      switch (opcode) {
      case Opcodes.ILOAD:this.mv.visitVarInsn(opcode,var);this.mv.visitIincInsn(var, 1);break;
      case Opcodes.LLOAD:this.mv.visitVarInsn(opcode,var);this.mv.visitIincInsn(var, 1);break;
      case Opcodes.FLOAD:this.mv.visitVarInsn(opcode,var);this.mv.visitIincInsn(var, 1);break;
      case Opcodes.DLOAD:this.mv.visitVarInsn(opcode,var);this.mv.visitIincInsn(var, 1);break;
      default:
      this.mv.visitVarInsn(opcode,var);
    }
    } else {
       this.mv.visitVarInsn(opcode,var);
}


}
}
