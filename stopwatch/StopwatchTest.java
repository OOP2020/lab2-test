package stopwatch;
import org.junit.*;
import static org.junit.Assert.*;
// extra import in case student puts this test class in separate package
import stopwatch.Stopwatch;

/**
 * Some JUnit tests for the Stopwatch.
 * Requires JUnit JAR on your project classpath.
 * BlueJ, Eclipse, IntelliJ, and Netbeans include JUnit
 * and will recognize these tests automatically.
 * In BlueJ, right-click this file and choose "Test All",
 * or click the "Run Tests" button.
 *
 * For VS Code you must add JUnit to your project yourself.
 * Two solutions.
 * 
 * 1. Easy but inefficient solution:
 *    inside your project create directory named "lib"
 *    copy junit-4.12.jar to lib
 *    copy hamcrest-core-1.3.jar to lib
 * 2. Better solution but more work:
 *    a) choose a library directory for Java software.
 *       You can use the same base directory as your JDK
 *       or a separate directory. Mine is /opt/lib
 *    b) inside this library directory, create a directory for JUnit 4,
 *       e.g. /opt/lib/junit4
 *    c) copy junit-4.12.jar and hamcrest-core-1.3.jar
 *       to the junit4 directory.
 *    d) In your VSCode project, edit the file .vscode/settings.json
 *       and add a line to the variable java.project.referencedLibraries:
 *         "java.project.referencedLibraries": [
               "/home/your_workspace/lab2/** /*.jar",
               "/opt/lib/junit4/*.jar"  <-- add your junit jar files
           ]
 *    e) Close the project and open it again is VS Code.     
 */
public class StopwatchTest {
    /** A small tolerance for comparing floating point values. */
    public static final double TOL = 1.0E-5;
    /** A test fixture that is initialized before each test. */
    private Stopwatch sw;

    @Before
    public void setUp() {
        sw = new Stopwatch();
    }
    
    /** Test initial state of a Stopwatch */
    @Test
    public void testStopwatchInitialState() {
        assertFalse(sw.isRunning());
        assertEquals(0.0, sw.getElapsed(), TOL);
	}
	
	/** Test that start - stop work as required. */
	@Test
	public void testStartStop() {
		assertFalse(sw.isRunning());
		sw.start();
		assertTrue(sw.isRunning());
		double elapsed1 = sw.getElapsed();
		sleep(200); // accumulate some elapsed time
		double elapsed2 = sw.getElapsed();
		assertTrue("Should have elapsed time", elapsed2 > elapsed1);
		sw.stop();
		double elapsed3 = sw.getElapsed();
		assertTrue("Elapsed time should increase", elapsed3 >= elapsed2);
		assertFalse("Stopwatch should be stoppped", sw.isRunning());
		// calling stop again does nothing
		sw.stop();
		assertFalse(sw.isRunning());
	}
	
	/** Test that elapsed time is computed correctly. */
	@Test
	public void testElapsedTime() {
		assertEquals(0.0, sw.getElapsed(), TOL);
		sw.start();
		sleep(200);
		double elapsed1 = sw.getElapsed();
		assertTrue("Should have elapsed time", elapsed1 > 0.0);
		// calling start again should have no effect!
		sw.start();
		assertTrue(sw.isRunning());
		double elapsed2 = sw.getElapsed();
		assertTrue("Should not reset elapsed", elapsed2 >= elapsed1);
		sw.stop();
		double elapsed3 = sw.getElapsed();
		assertTrue("Elapsed time should increase", elapsed3 >= elapsed2);
		sleep(200);
		// this shold have no effect since stopwatch is already stopped
		sw.stop();
		// elapsed time should not change since stopwatch is stopped
		double elapsed4 = sw.getElapsed();
		assertEquals("Elapsed should not change when stopwatch is stopped",
			elapsed3, elapsed4, TOL);
	}

	/** Test that if we restart a stopped watch then the start time
	 *  is reset.
	 */
	@Test
	public void testRestartStopwatch() {
		sw.start();
		sleep(200);
		double elapsed1 = sw.getElapsed();
		sw.stop();
		sw.start();
		double elapsed2 = sw.getElapsed();
		assertTrue( "stop and restart should reset elapsed time", elapsed2 < elapsed1 );
	}

	/**
	 * Sleep for given number of milliseconds.
	 * @param millisec number of microseconds
	 */
	private static void sleep(long millisec) {
		try {
			Thread.sleep(millisec); 
		} catch(InterruptedException ie) {
			// go back to work
		}
	}
}
