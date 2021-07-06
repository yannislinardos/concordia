package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Queries;

/**
 * It checks the sanitization
 * @author Yannis
 *
 */
public class SanitizeTest {
	

	@Test
	public void sanitizeXSSTest() {
		String toSanitize = "<script>malicious code<script>";
		String sanitized = Queries.sanitize(toSanitize);
		assertEquals("",sanitized);
	}
	
}
