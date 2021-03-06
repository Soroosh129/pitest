/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License (AOR)");
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

public enum ArithmetricMutator2 implements MethodMutatorFactory {

  AR_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new ArithmetricMethodVisitor2(this, methodInfo, context, methodVisitor);
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

class ArithmetricMethodVisitor2 extends AbstractInsnMutator {

    ArithmetricMethodVisitor2(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
  }

  private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<Integer, ZeroOperandMutation>();

  static {

    MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.ISUB,
            "Replaced integer addition with multiplication (AOR)"));


   MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IADD,
            "Replaced integer subtraction with multiplication (AOR)"));

    

    MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IADD,
            "Replaced integer multiplication with addition (AOR)"));


    MUTATIONS.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.IADD,
            "Replaced integer division with addition (AOR)"));

    


    // longs


    MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LSUB,
            "Replaced long addition with subtraction (AOR)"));

    

    MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LADD,
            "Replaced long subtraction with substraction (AOR)"));

    

   MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LADD,
            "Replaced long multiplication with addition (AOR)"));

    

    MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LADD,
            "Replaced long division with addition (AOR)"));



    // floats

    MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FSUB,
            "Replaced float addition with multiplication (AOR)"));

    
    MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FADD,
        "Replaced float subtraction with addition (AOR)"));

    

    MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FADD,
            "Replaced float multiplication with addition (AOR)"));

    

    MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FADD,
            "Replaced float division with addition (AOR)"));



    // doubles
    MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DSUB,
        "Replaced double addition with subtraction (AOR)"));

    
    MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DADD,
        "Replaced double subtraction with addition (AOR)"));


    

    MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DADD,
            "Replaced double multiplication with addition (AOR)"));

    

    MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DADD,
            "Replaced double division with addition (AOR)"));


  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
    return MUTATIONS;
  }

}
