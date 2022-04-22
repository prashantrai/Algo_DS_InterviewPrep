package demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.demo.Application;

class ApplicationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {}
	
	@BeforeEach
	void setUp() throws Exception {}

	@Test
	void test() {
		//fail("Not yet implemented");
		Application app = new Application();
	    int a = 1234;
	    int b = 5678;
	    int actual = app.add(a, b);
	 
	    int expected = 6912;
	 
	    assertEquals(expected, actual);
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}

}