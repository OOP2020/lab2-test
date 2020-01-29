package stopwatch;
import org.junit.*;
import static org.junit.Assert.*;
import stopwatch.Stopwatch;

/**
 * Some JUnit tests for the Stopwatch.
 * Requires JUnit JAR on your project classpath.
 * BlueJ, Eclipse, IntelliJ, and Netbeans include JUnit
 * and will recognize these tests automatically.
 * In BlueJ, right-click this file and choose "Test All",
 * or click the "Run Tests" button.
 *
 * For VS Code you *may* need add JUnit to the project yourself.
 * Two solutions.
 * 
 * 1. Easy but inefficient solution:
 *    inside your project create directory named "lib"
 *    copy junit-4.12.jar to lib
 *    copy hamcrest-core-1.3.jar to lib
 * 2. Better solution but more work:
 *    a) choose a library directory for Java add-on software.
 *       You can use the same base directory as your JDK
 *       or a separate directory. My directory is is /opt/lib
 *    b) inside this library directory, create a new directory for JUnit 4,
 *       e.g. /opt/lib/junit4 or /java/junit4
 *    c) copy junit-4.12.jar and hamcrest-core-1.3.jar
 *       to the junit4 directory.
 *    d) In your VSCode project, edit the file .vscode/settings.json
 *       and add a line to the variable java.project.referencedLibraries:
 *         "java.project.referencedLibraries": [
               "/home/your_workspace/lab2/** /*.jar",
               "/opt/lib/junit4/*.jar"   <-- add your junit jar files
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
		// if we call start() while stopwatch is already
		// running, it should have no effect!
		sw.start();
		assertTrue(sw.isRunning());
		double elapsed2 = sw.getElapsed();
		assertTrue("Duplicate start() should have no effect", elapsed2 >= elapsed1);
		sw.stop();
		double elapsed3 = sw.getElapsed();
		assertTrue("Elapsed time should increase", elapsed3 >= elapsed2);
		sleep(200);
		// If we call stop() when the stopwatch is already
		// stopped, it should have no effect!
		sw.stop();
		// elapsed time should not change since stopwatch is stopped
		double elapsed4 = sw.getElapsed();
		assertEquals("Elapsed should not change when stopwatch is stopped",
			elapsed3, elapsed4, TOL);
	}

	/** 
	 * Test that if we restart a stopped watch then the start time
	 *  is reset.  This is in the specification.
	 */
	@Test
	public void testRestartStopwatch() {
		sw.start();
		sleep(300);
		double elapsed1 = sw.getElapsed();
		sw.stop();
		// start and measure elapsed immediately,
		// so it is sure to be less than elapsed1
		sw.start();
		double elapsed2 = sw.getElapsed();
		assertTrue("stop and restart should reset elapsed time", elapsed2 < elapsed1 );
		// do it again to be sure we detect misbehavior
		sw.stop();
		sw.start();
		elapsed2 = sw.getElapsed();
		assertTrue("stop and restart should reset elapsed time", elapsed2 < elapsed1 );
		
	}

	/**
	 * Sleep for a given number of milliseconds.
	 * @param millisec number of microseconds to sleep
	 */
	private static void sleep(long millisec) {
		try {
			Thread.sleep(millisec); 
		} catch(InterruptedException ie) {
			// go back to work
		}
	}
}
