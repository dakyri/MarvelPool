package com.mayaswell.marvelpool;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dak on 4/28/2016.
 *
 * http://gateway.marvel.com/v1/public/characters?apikey=d69ae1426b19ec1650e79780e2fac09c&ts=1461877439&hash=20a096cf92345cc330169569b674ea4b
 */
public class MarvelSlurperTest extends TestCase {

	private MarvelSlurper marvelSlurper;

	@Before
	public void setUp() throws Exception {
		marvelSlurper = new MarvelSlurper(
				"http://gateway.marvel.com/v1/public/",
				"d69ae1426b19ec1650e79780e2fac09c",
				"24e1aa65ba9828af4cd7415969bcff12d67cc696");
	}

	/**
	 * some issues with mocking of JSON ... probably the most pertinent thing to unit test
	 * @throws Exception
	 */
	@Test
	public void testJSONMocking() throws Exception {
		JSONObject test1 = new JSONObject("{\"testString\":\"Ok\"}");
		assertNotNull(test1);
		assertEquals("Ok", test1.getString("testString"));
	}

	@Test
	public void testGetCharacterData() throws Exception {
		String datapool =
				"{\"id\":1009268,\"name\":\"Deadpool\",\"description\":\"\",\"modified\":\"2013-10-18T17:33:26-0400\",\"thumbnail\":{\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/9/90/5261a86cacb99\",\"extension\":\"jpg\"},\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009268\""+"}";
//				"{\"code\":200,\"status\":\"Ok\",\"copyright\":\"© 2016 MARVEL\",\"attributionText\":\"Data provided by Marvel. © 2016 MARVEL\",\"attributionHTML\":\"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2016 MARVEL</a>\",\"etag\":\"0bbbcf62cfec8be99cc3994e7cfd4c541ffc5677\",\"data\":{\"offset\":0,\"limit\":20,\"total\":1,\"count\":1,\"results\":[{\"id\":1009268,\"name\":\"Deadpool\",\"description\":\"\",\"modified\":\"2013-10-18T17:33:26-0400\",\"thumbnail\":{\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/9/90/5261a86cacb99\",\"extension\":\"jpg\"},\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009268\""+"]}]}}";
//				"\",comics\":{\"available\":402,\"collectionURI\":\"http://gateway.marvel.com/v1/public/characters/1009268/comics\",\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/41112\",\"name\":\"5 Ronin (Hardcover)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/36160\",\"name\":\"5 Ronin (2010) #5\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/38751\",\"name\":\"5 Ronin (2010) #5 (MCGUINNESS COVER)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/394\",\"name\":\"Agent X (2002) #15\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/24418\",\"name\":\"Amazing Spider-Man (1999) #611\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/31556\",\"name\":\"Amazing Spider-Man (1999) #620 (DEADPOOL VARIANT)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/31560\",\"name\":\"Avengers: The Initiative (2007) #33 (DEADPOOL VARIANT)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/41349\",\"name\":\"Battle Scars (2011) #3\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/41348\",\"name\":\"Battle Scars (2011) #4\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/23971\",\"name\":\"Cable (2008) #13\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/23972\",\"name\":\"Cable (2008) #13 (OLIVETTI (MW, 50/50 COVER))\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/24171\",\"name\":\"Cable (2008) #14\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/24172\",\"name\":\"Cable (2008) #14 (OLIVETTI MW, 50/50 COVER)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/24631\",\"name\":\"Cable (2008) #15\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/24632\",\"name\":\"Cable (2008) #15 (OLIVETTI (MW, 50/50 COVER))\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/430\",\"name\":\"Cable & Deadpool (2004) #1\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/479\",\"name\":\"Cable & Deadpool (2004) #2\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/644\",\"name\":\"Cable & Deadpool (2004) #3\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/706\",\"name\":\"Cable & Deadpool (2004) #4\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/548\",\"name\":\"Cable & Deadpool (2004) #5\"}],\"returned\":20}"+
//				"\",series\":{\"available\":99,\"collectionURI\":\"http://gateway.marvel.com/v1/public/characters/1009268/series\",\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/15276\",\"name\":\"5 Ronin (2011)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/12429\",\"name\":\"5 Ronin (2010)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/459\",\"name\":\"Agent X (2002 - 2004)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/454\",\"name\":\"Amazing Spider-Man (1999 - 2013)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1945\",\"name\":\"Avengers: The Initiative (2007 - 2010)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/15370\",\"name\":\"Battle Scars (2011 - 2012)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/4002\",\"name\":\"Cable (2008 - 2010)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/693\",\"name\":\"Cable & Deadpool (2004 - 2008)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/13886\",\"name\":\"Cable & Deadpool MGC (2011)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1209\",\"name\":\"Cable & Deadpool Vol. 1: If Looks Could Kill (2007)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1338\",\"name\":\"Cable & Deadpool Vol. 2: The Burnt Offering (2007)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1488\",\"name\":\"Cable & Deadpool Vol. 3: The Human Race (2005)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1578\",\"name\":\"Cable & Deadpool Vol. 4: Bosom Buddies (2006)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1676\",\"name\":\"Cable & Deadpool Vol. 5: Living Legends (2006)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1960\",\"name\":\"Cable & Deadpool Vol. 6: Paved with Good Intentions (2007)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/2710\",\"name\":\"Cable & Deadpool Vol. 7: Separation Anxiety (2007)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1487\",\"name\":\"Cable/Deadpool Vol. 3: The Human Race (2005)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1577\",\"name\":\"Cable/Deadpool Vol. 4: Bosom Buddies (2006)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/10105\",\"name\":\"Civil War: X-Men (2011)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/1962\",\"name\":\"Civil War: X-Men Universe (2007)\"}],\"returned\":20}"+
//				"\",stories\":{\"available\":570,\"collectionURI\":\"http://gateway.marvel.com/v1/public/characters/1009268/stories\",\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/1135\",\"name\":\"Cover #1135\",\"type\":\"cover\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/1619\",\"name\":\"Interior #1619\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2464\",\"name\":\"3 of 6 - The Passion of the Cable\",\"type\":\"cover\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2465\",\"name\":\"3 of 6 - The Passion of the Cable\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2467\",\"name\":\"Interior #2467\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2469\",\"name\":\"Interior #2469\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2471\",\"name\":\"Interior #2471\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2473\",\"name\":\"Interior #2473\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2475\",\"name\":\"Interior #2475\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2477\",\"name\":\"Interior #2477\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2479\",\"name\":\"Interior #2479\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2481\",\"name\":\"4 of 6 - The Passion of the Cable\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2483\",\"name\":\"5 of 6 - The Passion of the Cable\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2484\",\"name\":\"2 of 2 - Thirty Pieces\",\"type\":\"cover\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2485\",\"name\":\"2 of 2 - Thirty Pieces\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2487\",\"name\":\"1 of 2 - A Murder in Paradise\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2488\",\"name\":\"2 of 2 - A Murder in Paradise\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2489\",\"name\":\"1 of 4 - Enema of the State\",\"type\":\"cover\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2490\",\"name\":\"1 of 4 - Enema of the State\",\"type\":\"interiorStory\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2492\",\"name\":\"2 of 4 - Enema of the State\",\"type\":\"interiorStory\"}],\"returned\":20}"+
//				"\",events\":{\"available\":9,\"collectionURI\":\"http://gateway.marvel.com/v1/public/characters/1009268/events\",\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/227\",\"name\":\"Age of Apocalypse\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/238\",\"name\":\"Civil War\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/318\",\"name\":\"Dark Reign\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/251\",\"name\":\"House of M\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/298\",\"name\":\"Messiah War\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/269\",\"name\":\"Secret Invasion\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/309\",\"name\":\"Shattered Heroes\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/308\",\"name\":\"X-Men: Regenesis\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/events/306\",\"name\":\"X-Men: Schism\"}],\"returned\":9}"+
//				"\",urls\":[{\"type\":\"detail\",\"url\":\"http://marvel.com/characters/12/deadpool?utm_campaign=apiRef&utm_source=d69ae1426b19ec1650e79780e2fac09c\"},{\"type\":\"wiki\",\"url\":\"http://marvel.com/universe/Deadpool_(Wade_Wilson)?utm_campaign=apiRef&utm_source=d69ae1426b19ec1650e79780e2fac09c\"},{\"type\":\"comiclink\",\"url\":\"http://marvel.com/comics/characters/1009268/deadpool?utm_campaign=apiRef&utm_source=d69ae1426b19ec1650e79780e2fac09c\"}"+
//				"]}]}}";
		JSONObject jsonpool = new JSONObject(datapool);
		Character c = marvelSlurper.getCharacterData(jsonpool);
		assertNotNull(c);
		assertEquals(c.id, 1009268);
		assertEquals(c.name, "Deadpool");
		assertEquals(c.resourceURI, null);
		assertEquals(c.thumbnailURI, "http://i.annihil.us/u/prod/marvel/i/mg/9/90/5261a86cacb99.jpg");
	}

	@Test
	public void testGetImageURL() throws Exception {
		String datapool = "{\"thumbnail\":{\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/9/90/5261a86cacb99\",\"extension\":\"jpg\"}}";
		JSONObject jsonpool = new JSONObject(datapool);
		String url = marvelSlurper.getImageURL(jsonpool, "thumbnail");
		assertNotNull(url);
		assertEquals(url, "http://i.annihil.us/u/prod/marvel/i/mg/9/90/5261a86cacb99.jpg");
	}

	@Test
	public void testMd5() throws Exception {
		assertEquals("", MarvelSlurper.md5(null));
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", MarvelSlurper.md5(""));
		assertEquals("104b424f0f95fbb95e943b8035566fb9", MarvelSlurper.md5("d69ae1426b19ec1650e79780e2fac09c"));
		assertEquals("d7f92fc5a9e66556adbd10cdd5da15e4", MarvelSlurper.md5("24e1aa65ba9828af4cd7415969bcff12d67cc696"));
	}
}