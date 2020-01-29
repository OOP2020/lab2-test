package util;
import org.junit.*;  // for @Test, @Before, Assert
import static org.junit.Assert.*;
/**
 * Some unit tests of the ArrayMath methods,
 * using JUnit.
 */
public class ArrayMathTest {
	// a small tolerance for comparing floating point values.
	// assertEquals requires this when comparing floating point.
	public static final double TOL = 1.0E-8;

	/** Average of an empty array should be 0. */
	@Test
	public void testAverageEmptyArray() {
		double[] x = {}; // empty array
		assertEquals(0.0, ArrayMath.average(x), TOL);
	}

	/** Test average of some typical values */
	@Test
	public void testAverage() {
		double[] x = {2.0}; // array of one element
		assertEquals(2.0, ArrayMath.average(x), TOL);
		x = makeConstantArray(50, 2.0);
		assertEquals(2.0, ArrayMath.average(x), TOL);
		// all elements 0 except one
		x = makeArrayOf(0.0, 0.0, 0.0, 0.0);
		x[3] = 20;
		assertEquals(20.0/4, ArrayMath.average(x), TOL);
		x = makeArrayOf(0.0, 0.0, 0.0, 0.0);
		x[0] = 20;
		assertEquals(20.0/4, ArrayMath.average(x), TOL);
		// some nice series
		x = makeSeries(0.0, 16.0, 1.0);
		assertEquals( 8.0, ArrayMath.average(x), TOL);
		x = makeSeries(4.0, 0.0, -0.5); // [4.0, 3.5, 3.0, 2.5, ..., 0.0]
		assertEquals( 2.0, ArrayMath.average(x), TOL);
	}

	/** Test dotProduct when both arrays are empty. */
	@Test
	public void testDotProductEmptyArrays() {
		double[] x = {};
		double[] y = {};
		assertEquals(0.0, ArrayMath.dotProduct(x,y), TOL);
	}

	/** dotProduct of arrays where one array is empty. */
	@Test
	public void testDotProductOneEmptyArray() {
		double[] x = {1.0, 2.0, 3.0, 4.0};
		double[] y = {};
		assertEquals(0.0, ArrayMath.dotProduct(x,y), TOL);
		assertEquals(0.0, ArrayMath.dotProduct(y,x), TOL);
	}

	/** dotProduct of arrays of various non-zero sizes. */
	@Test
	public void testDotProduct() {
		double[] x = makeArrayOf(1.0, 2.0, 3.0, 4.0, 5.0);
		double[] y = makeArrayOf(2.0, 4.0, 10.0);
		double sumx2 = 1.0*1.0 + 2.0*2.0 + 3.0*3.0 + 4.0*4.0 + 5.0*5.0;
		assertEquals(sumx2, ArrayMath.dotProduct(x,x), TOL);
		double sumxy = 1.0*2.0 + 2.0*4.0 + 3.0*10.0;
		assertEquals(sumxy, ArrayMath.dotProduct(x,y), TOL);
		assertEquals(sumxy, ArrayMath.dotProduct(y,x), TOL);
		// series sum is n*(n+1)/2 where n = last value
		x = makeSeries(0.0, 10.0, 1.0);
		y = makeConstantArray(20, 2.0); // not same size as x
		double sumseries = 10.0*11.0;  // series sum formula: n*(n+1)/2
		assertEquals(sumseries, ArrayMath.dotProduct(x,y), TOL);
		assertEquals(sumseries, ArrayMath.dotProduct(y,x), TOL);
	}

	/** norm of an empty array. */
	@Test
	public void testNormEmptyArray() {
		double[] x = {};
		assertEquals(0.0, ArrayMath.norm(x), TOL);
	}

	/** norm of array of various non-zero sizes. */
	@Test
	public void testNorm() {
		// easy! array of length 1
		double[] single = {5.0};
		assertEquals(5.0, ArrayMath.norm(single), TOL);
		// array of all zeros
		double[] x = makeConstantArray(10, 0.0);
		assertEquals(0.0, ArrayMath.norm(x), TOL);
		// change one element
		x[0] = 6.0;
		assertEquals(6.0, ArrayMath.norm(x), TOL);
		x[9] = 8.0;
		assertEquals(10.0, ArrayMath.norm(x), TOL);
		x[5] = 1.0;
		assertEquals(Math.sqrt(6*6+8*8+1*1), ArrayMath.norm(x), TOL);
		// sum of squares of series 1,2,...,n is n(n+1)(2n+1)/6
		double n = 10.0;
		x = makeSeries(0.0, n, 1.0);  // makeSeries(start, ending, increment)
		double sumsquares = n*(n+1)*(2*n+1)/6.0;
		assertEquals(Math.sqrt(sumsquares), ArrayMath.norm(x), TOL);
		// same series with all negative values
		x = makeSeries(-n, 0.0, 1.0); // makeSeries(start, ending, increment)
		assertEquals(Math.sqrt(sumsquares), ArrayMath.norm(x), TOL);
		// series with some negative and positive values
		n = 5.0;
		x = makeSeries(-n, n, 1.0);
		sumsquares = 2*n*(n+1)*(2*n+1)/6.0;
		assertEquals(Math.sqrt(sumsquares), ArrayMath.norm(x), TOL);
	}

	/** dotProduct of arrays of various non-zero sizes. */
	@Test
	public void testMax() {
		double[] x = {};
		assertEquals(Double.NEGATIVE_INFINITY, ArrayMath.max(x), TOL);
		x = new double[]{-1.0}; // single value
		assertEquals(-1.0, ArrayMath.max(x), TOL);
		x = new double[]{0.0}; // single value
		assertEquals(0.0, ArrayMath.max(x), TOL);
		x = new double[]{1000.0, 0.0, 0.0, 0.0, -1001.0};
		assertEquals(1000.0, ArrayMath.max(x), TOL);
		x[1] = 1001.0;
		assertEquals(1001.0, ArrayMath.max(x), TOL);
		x[4] = 1002.0;
		assertEquals(1002.0, ArrayMath.max(x), TOL);
		x[4] = -1.0;
		assertEquals(1001.0, ArrayMath.max(x), TOL);
	}

	/** polynomial of size 0 (a constant). */
	@Test
	public void testConstantPolynomial() {
		double[] a = {1.0};
		double x = 99.0;
		assertEquals(1.0, ArrayMath.polyval(x, a), TOL);
		a = new double[] {0.5};
		x = 0.0;
		assertEquals(0.5, ArrayMath.polyval(x, a), TOL);
	}

	/** polynomials of various sizes */
	@Test
	public void testPolyval() {
		double[] a = {99.0, -1.0};
		double x = 0.0;
		assertEquals(99.0, ArrayMath.polyval(x, a), TOL);
		x = 99.0;
		assertEquals(0.0, ArrayMath.polyval(x, a), TOL);
		x = 10.0;
		assertEquals(89.0, ArrayMath.polyval(x, a), TOL);
		a = new double[]{0.5, 0.0, 2.0};
		x = 10.0;
		assertEquals(0.5+2*x*x, ArrayMath.polyval(x, a), TOL);
		a = new double[]{0.5, 0.0, 2.0, 4.0};
		x = 10.0;
		assertEquals(0.5+2*x*x+4*x*x*x, ArrayMath.polyval(x, a), TOL);
		// an extreme case to test if someone is using Math.power(x,n)
		// they should not
		double endvalue = 999999.0; // odd number
		a = makeSeries(1.0, endvalue, 1.0);
		// p(x=1.0) is just sum of coefficients
		double sum = endvalue*(endvalue+1)/2.0;
		assertEquals(sum, ArrayMath.polyval(1.0, a), TOL);
		// p(x=-1.0) is alternating sum of coefficients 1 -2 +3 -4 +5 ... + endvalue
		// each pair (x^n + x^(n+1)) sums to 1
		sum = (1+endvalue)/2.0;
		assertEquals(sum, ArrayMath.polyval(-1.0, a), TOL);
		a = makeConstantArray(1_000_000, 1.0);
		// geometric series: 1 + 1/2 + (1/2)^2 + ... sums to 2.0
		assertEquals(2.0, ArrayMath.polyval(0.5, a), TOL);
	}
	
	/**
	 * Create an array of constant values.
	 * @param size is the size of the array to create
	 * @param value is a value to put in all array elements
	 */
	private double[] makeConstantArray(int size, double value) {
		double[] x = new double[size];
		java.util.Arrays.fill(x, value);
		return x;
	}

	/**
	 * Make an array containing a series of values.
	 * 
	 * @param start the starting value
	 * @param stop  the final value
	 * @param increment value increment, may be negative
	 * 
	 */
	private double[] makeSeries(double start, double stop, double increment) {
		int size = (int)Math.ceil(1 + (stop - start)/increment - TOL);
		if (size <= 0) return new double[0];
		double[] x = new double[size];
		for(int k=0; k<size; k++) x[k] = start + k*increment;
		return x;
	}

	/**
	 * Make an array containing the specified values.
	 * @param args values to insert into the array
	 * @return array containing the args
	 */
	public double[] makeArrayOf(double ... args) {
		// copy args to a new array to ensure they don't go away :-)
		double[] x = new double[args.length];
		System.arraycopy(args, 0, x, 0, args.length);
		return x;
	}
}