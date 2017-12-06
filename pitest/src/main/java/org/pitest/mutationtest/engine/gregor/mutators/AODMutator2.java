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

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractInsnMutator;
import org.pitest.mutationtest.engine.gregor.InsnSubstitution;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;
import org.pitest.mutationtest.engine.gregor.ZeroOperandMutation;

public enum AODMutator2 implements MethodMutatorFactory {

  AOD_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new AODMutatorVisitor2(this, methodInfo, context, methodVisitor);
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

class AODMutatorVisitor2 extends AbstractInsnMutator {

  private static final String                            MESSAGE   = "replace expression with second oprator";
  private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<Integer, ZeroOperandMutation>();

  static {
    MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.NOP, MESSAGE));

    MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.NOP, MESSAGE));

    MUTATIONS.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.NOP, MESSAGE));

    MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.NOP, MESSAGE));
  }

  AODMutatorVisitor2(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
    return MUTATIONS;
  }

}
