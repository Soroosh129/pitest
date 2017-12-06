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
package org.pitest.mutationtest.engine.gregor.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.pitest.functional.F;
import org.pitest.functional.FCollection;
import org.pitest.functional.prelude.Prelude;
import org.pitest.help.Help;
import org.pitest.help.PitHelpError;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;

import org.pitest.mutationtest.engine.gregor.mutators.ArgumentPropagationMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConstructorCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InlineConstantMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.MathMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NonVoidMethodCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator.Choice;
import org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.NakedReceiverMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator;

import org.pitest.mutationtest.engine.gregor.mutators.ArithmetricMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ArithmetricMutator2;
import org.pitest.mutationtest.engine.gregor.mutators.ArithmetricMutator3;
import org.pitest.mutationtest.engine.gregor.mutators.ABSMutator;
import org.pitest.mutationtest.engine.gregor.mutators.AODMutator;
import org.pitest.mutationtest.engine.gregor.mutators.AODMutator2;
import org.pitest.mutationtest.engine.gregor.mutators.OBBNMutator;
import org.pitest.mutationtest.engine.gregor.mutators.UOIMutator;
import org.pitest.mutationtest.engine.gregor.mutators.UOIMutator2;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator2;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator3;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator4;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator5;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator6;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator7;
import org.pitest.mutationtest.engine.gregor.mutators.RORMutator8;

import org.pitest.mutationtest.engine.gregor.mutators.CRCRMutator;
import org.pitest.mutationtest.engine.gregor.mutators.CRCR2Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.CRCR3Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.CRCR4Mutator;
import org.pitest.mutationtest.engine.gregor.mutators.CRCR5Mutator;
public final class Mutator {

  private static final Map<String, Iterable<MethodMutatorFactory>> MUTATORS = new LinkedHashMap<String, Iterable<MethodMutatorFactory>>();

  static {

    /**
     * Default mutator that inverts the negation of integer and floating point
     * numbers.
     */
    add("INVERT_NEGS", InvertNegsMutator.INVERT_NEGS_MUTATOR);

    /**
     * Default mutator that mutates the return values of methods.
     */
    add("RETURN_VALS", ReturnValsMutator.RETURN_VALS_MUTATOR);

    /**
     * Optional mutator that mutates integer and floating point inline
     * constants.
     */
    add("INLINE_CONSTS", new InlineConstantMutator());

    /**
     * Default mutator that mutates binary arithmetic operations.
     */
    add("MATH", MathMutator.MATH_MUTATOR);

    /**
     * Default mutator that removes method calls to void methods.
     *
     */
    add("VOID_METHOD_CALLS", VoidMethodCallMutator.VOID_METHOD_CALL_MUTATOR);

    /**
     * Default mutator that negates conditionals.
     */
    add("NEGATE_CONDITIONALS",
        NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR);

    /**
     * Default mutator that replaces the relational operators with their
     * boundary counterpart.
     */
    add("CONDITIONALS_BOUNDARY",
        ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR);

    /**
     * Default mutator that mutates increments, decrements and assignment
     * increments and decrements of local variables.
     */
    add("INCREMENTS", IncrementsMutator.INCREMENTS_MUTATOR);

    /**
     * Optional mutator that removes local variable increments.
     */

    add("REMOVE_INCREMENTS", RemoveIncrementsMutator.REMOVE_INCREMENTS_MUTATOR);

    /**
     * Optional mutator that removes method calls to non void methods.
     */
    add("NON_VOID_METHOD_CALLS",
        NonVoidMethodCallMutator.NON_VOID_METHOD_CALL_MUTATOR);

    /**
     * Optional mutator that replaces constructor calls with null values.
     */
    add("CONSTRUCTOR_CALLS", ConstructorCallMutator.CONSTRUCTOR_CALL_MUTATOR);


    /**
     * User added mutators
     */
    add("AR_MUTATOR1", ArithmetricMutator.AR_MUTATOR);
    add("AR_MUTATOR2", ArithmetricMutator2.AR_MUTATOR);
    add("AR_MUTATOR3", ArithmetricMutator3.AR_MUTATOR);

    add("ABS_MUTATOR", ABSMutator.ABS_MUTATOR);
    add("AOD_MUTATOR", AODMutator.AOD_MUTATOR);
    add("AOD_MUTATOR2",AODMutator2.AOD_MUTATOR);
    add("OBBN_MUTATOR", OBBNMutator.OBBN_MUTATOR);

    add("UOI_MUTATOR", UOIMutator.UOI_MUTATOR);
    add("UOI_MUTATOR2", UOIMutator2.UOI_MUTATOR);

    add("ROR_MUTATOR", RORMutator.ROR_MUTATOR);
    add("ROR_MUTATOR2", RORMutator2.ROR_MUTATOR);
    add("ROR_MUTATOR3", RORMutator3.ROR_MUTATOR);
    add("ROR_MUTATOR4", RORMutator4.ROR_MUTATOR);
    add("ROR_MUTATOR5", RORMutator5.ROR_MUTATOR);
    add("ROR_MUTATOR6", RORMutator6.ROR_MUTATOR);
    add("ROR_MUTATOR7", RORMutator7.ROR_MUTATOR);
    add("ROR_MUTATOR8", RORMutator8.ROR_MUTATOR);

    add("CRCR_MUTATOR", new CRCRMutator());
    add("CRCR2_MUTATOR", new CRCR2Mutator());
    add("CRCR3_MUTATOR", new CRCR3Mutator());

    add("CRCR4_MUTATOR", new CRCR4Mutator());
    add("CRCR5_MUTATOR", new CRCR5Mutator());
   
    /**
     * Removes conditional statements so that guarded statements always execute
     * The EQUAL version ignores LT,LE,GT,GE, which is the default behavior,
     * ORDER version mutates only those.
     */

    add("REMOVE_CONDITIONALS_EQ_IF", new RemoveConditionalMutator(Choice.EQUAL,
        true));
    add("REMOVE_CONDITIONALS_EQ_ELSE", new RemoveConditionalMutator(
        Choice.EQUAL, false));
    add("REMOVE_CONDITIONALS_ORD_IF", new RemoveConditionalMutator(
        Choice.ORDER, true));
    add("REMOVE_CONDITIONALS_ORD_ELSE", new RemoveConditionalMutator(
        Choice.ORDER, false));
    addGroup("REMOVE_CONDITIONALS", RemoveConditionalMutator.makeMutators());

    /**
     * Experimental mutator that removed assignments to member variables.
     */
    add("EXPERIMENTAL_MEMBER_VARIABLE",
        new org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator());

    /**
     * Experimental mutator that swaps labels in switch statements
     */
    add("EXPERIMENTAL_SWITCH",
        new org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator());

    /**
     * Experimental mutator that replaces method call with one of its parameters
     * of matching type
     */
    add("EXPERIMENTAL_ARGUMENT_PROPAGATION",
        ArgumentPropagationMutator.ARGUMENT_PROPAGATION_MUTATOR);

    /**
     * Experimental mutator that replaces method call with this
     */
    add("EXPERIMENTAL_NAKED_RECEIVER", NakedReceiverMutator.NAKED_RECEIVER);

    addGroup("REMOVE_SWITCH", RemoveSwitchMutator.makeMutators());
    addGroup("DEFAULTS", defaults());
    addGroup("STRONGER", stronger());
    addGroup("ALL", all());
  }

  public static Collection<MethodMutatorFactory> all() {
    return fromStrings(MUTATORS.keySet());
  }

  private static Collection<MethodMutatorFactory> stronger() {
    return combine(
        defaults(),
        group(new RemoveConditionalMutator(Choice.EQUAL, false),
            new SwitchMutator()));
  }

  private static Collection<MethodMutatorFactory> combine(
      Collection<MethodMutatorFactory> a, Collection<MethodMutatorFactory> b) {
    List<MethodMutatorFactory> l = new ArrayList<MethodMutatorFactory>(a);
    l.addAll(b);
    return l;
  }

  /**
   * Default set of mutators - designed to provide balance between strength and
   * performance
   */
  public static Collection<MethodMutatorFactory> defaults() {
    return group(
//        MathMutator.MATH_MUTATOR,
        ArithmetricMutator.AR_MUTATOR,ArithmetricMutator2.AR_MUTATOR,ArithmetricMutator3.AR_MUTATOR,
        ABSMutator.ABS_MUTATOR,
        AODMutator.AOD_MUTATOR,AODMutator2.AOD_MUTATOR,
        new CRCRMutator(),new CRCR2Mutator(),new CRCR3Mutator(),new CRCR4Mutator(),new CRCR5Mutator(),
        RORMutator.ROR_MUTATOR,RORMutator2.ROR_MUTATOR, RORMutator3.ROR_MUTATOR, RORMutator4.ROR_MUTATOR, RORMutator5.ROR_MUTATOR,RORMutator6.ROR_MUTATOR,RORMutator7.ROR_MUTATOR,RORMutator8.ROR_MUTATOR,  
        UOIMutator.UOI_MUTATOR,UOIMutator2.UOI_MUTATOR,
        OBBNMutator.OBBN_MUTATOR);
  }

  private static Collection<MethodMutatorFactory> group(
      final MethodMutatorFactory... ms) {
    return Arrays.asList(ms);
  }

  public static Collection<MethodMutatorFactory> byName(final String name) {
    return FCollection.map(MUTATORS.get(name),
        Prelude.id(MethodMutatorFactory.class));
  }

  private static void add(final String key, final MethodMutatorFactory value) {
    MUTATORS.put(key, Collections.singleton(value));
  }

  private static void addGroup(final String key,
      final Iterable<MethodMutatorFactory> value) {
    MUTATORS.put(key, value);
  }

  public static Collection<MethodMutatorFactory> fromStrings(
      final Collection<String> names) {
    final Set<MethodMutatorFactory> unique = new TreeSet<MethodMutatorFactory>(
        compareId());

    FCollection.flatMapTo(names, fromString(), unique);
    return unique;
  }

  private static Comparator<? super MethodMutatorFactory> compareId() {
    return new Comparator<MethodMutatorFactory>() {
      @Override
      public int compare(final MethodMutatorFactory o1,
          final MethodMutatorFactory o2) {
        return o1.getGloballyUniqueId().compareTo(o2.getGloballyUniqueId());
      }
    };
  }

  private static F<String, Iterable<MethodMutatorFactory>> fromString() {
    return new F<String, Iterable<MethodMutatorFactory>>() {
      @Override
      public Iterable<MethodMutatorFactory> apply(final String a) {
        Iterable<MethodMutatorFactory> i = MUTATORS.get(a);
        if (i == null) {
          throw new PitHelpError(Help.UNKNOWN_MUTATOR, a);
        }
        return i;
      }
    };
  }

}
