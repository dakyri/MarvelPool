package com.mayaswell.marvelpool;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Created by dak on 5/22/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MarvelSlurperInstrTest extends InstrumentationTestCase {

	public MarvelSlurperInstrTest() {
		super();
	}

	private MarvelSlurper marvelAPI;

	@Before
	public void setUp() throws Exception  {
		super.setUp();
		injectInstrumentation(InstrumentationRegistry.getInstrumentation());
		Instrumentation in = this.getInstrumentation();
		assertNotNull(in);
		Context c = in.getTargetContext();
		assertNotNull(c);
		String base = c.getString(R.string.marvel_url_base);
		String apiKey = c.getString(R.string.api_key);
		String privKey = c.getString(R.string.private_key);
		assertEquals(base, "http://gateway.marvel.com/v1/public/");
		assertEquals(apiKey, "d69ae1426b19ec1650e79780e2fac09c");
		assertEquals(privKey, "24e1aa65ba9828af4cd7415969bcff12d67cc696");

		marvelAPI = new MarvelSlurper(base, apiKey, privKey);
	}

	public void testMakeAuthorizedURL() throws Exception {
//		marvelAPI.makeAuthorizedURL()
	}
}