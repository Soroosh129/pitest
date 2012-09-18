package org.pitest.mutationtest.incremental;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pitest.classinfo.ClassIdentifier;
import org.pitest.classinfo.ClassName;
import org.pitest.classinfo.HierarchicalClassId;
import org.pitest.coverage.CoverageDatabase;
import org.pitest.functional.Option;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.execute.MutationStatusTestPair;
import org.pitest.mutationtest.report.MutationTestResultMother;
import org.pitest.mutationtest.results.DetectionStatus;
import org.pitest.mutationtest.results.MutationResult;

public class XStreamHistoryStoreTest {

  private final static String COV = BigInteger.TEN.toString(16);
  
  private XStreamHistoryStore testee;
  
  @Mock
  private CoverageDatabase coverage;

  private final Writer        output = new StringWriter();

  private final WriterFactory writerFactory = new WriterFactory() {

    public PrintWriter create() {
      return new PrintWriter(output);
    }

    public void close() {
      
    }
    
  };
  
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(coverage.getCoverageIdForClass(any(ClassName.class))).thenReturn( BigInteger.TEN);
  }
  
  @Test
  public void shouldRecordAndRetrieveClassPath() {
    final ClassHistory foo = new ClassHistory(new HierarchicalClassId(new ClassIdentifier(0,
        ClassName.fromString("foo")),""), COV);
    final ClassHistory bar = new ClassHistory(new HierarchicalClassId(new ClassIdentifier(0,
        ClassName.fromString("bar")),""), COV);
    recordClassPathWithTestee(foo.getId(), bar.getId());

    final Reader reader = new StringReader(this.output.toString());
    this.testee = new XStreamHistoryStore(writerFactory, Option.some(reader));
    this.testee.initialize();

    final Map<ClassName, ClassHistory> expected = new HashMap<ClassName, ClassHistory>();
    expected.put(foo.getName(), foo);
    expected.put(bar.getName(), bar);
    assertEquals(expected, this.testee.getHistoricClassPath());
  }

  @Test
  public void shouldRecordAndRetrieveResults() {
    final HierarchicalClassId foo = new HierarchicalClassId(new ClassIdentifier(0,
        ClassName.fromString("foo")),"");
    recordClassPathWithTestee(foo);

    final MutationResult mr = new MutationResult(
        MutationTestResultMother.createDetails("foo"),
        new MutationStatusTestPair(1, DetectionStatus.KILLED, "testName"));

    this.testee.recordResult(mr);

    final Reader reader = new StringReader(this.output.toString());
    this.testee = new XStreamHistoryStore(writerFactory, Option.some(reader));
    this.testee.initialize();
    final Map<MutationIdentifier, MutationStatusTestPair> expected = new HashMap<MutationIdentifier, MutationStatusTestPair>();
    expected.put(mr.getDetails().getId(), mr.getStatusTestPair());
    assertEquals(expected, this.testee.getHistoricResults());
  }

  @Test
  public void shouldNotAttemptToWriteToFileWhenNoneSupplied() {
    try {
      this.testee = new XStreamHistoryStore(writerFactory, Option.<Reader> none());
      this.testee.initialize();
    } catch (final Exception ex) {
      fail(ex.getMessage());
    }
  }
  
  private void recordClassPathWithTestee(
      final HierarchicalClassId... classIdentifiers) {
    this.testee = new XStreamHistoryStore(writerFactory, Option.<Reader> none());
    final Collection<HierarchicalClassId> ids = Arrays.asList(classIdentifiers);
    this.testee.recordClassPath(ids, coverage);
  }

}