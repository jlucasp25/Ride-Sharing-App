package rsa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	rsa.quad.AllTests.class, 
	rsa.service.AllTests.class, 
	rsa.shared.AllTests.class})
public class AllTests {

}
