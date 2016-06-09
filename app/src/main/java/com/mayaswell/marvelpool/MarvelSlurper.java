package com.mayaswell.marvelpool;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by dak on 4/26/2016.
 */
public class MarvelSlurper {
	private final String urlBase;
	private final String apiKey;
	private final String privateKey;
	private Listener listener;

	public interface Listener {
		void error(String msg);
		void characterListResult(int offset, ArrayList<Character> cl);
	}

	public MarvelSlurper(final String urlBase, final String apiKey, final String privateKey)
	{
		this.urlBase = urlBase;
		this.apiKey = apiKey;
		this.privateKey = privateKey;
		listener = null;
	}

	public void getCharacters() {
		getCharacters(0, -1, null);
	}

	public void getCharacters(int offset) {
		getCharacters(offset, -1, null);
	}

	public void getCharacters(int offset, int limit, String query) {
		JSONSlurper loader = new JSONSlurper() {
			@Override
			protected void onPostExecute(JSONObject result)
			{
				if (result == null) {
					notifyError(lastErrorMsg);
					return;
				}
				if (responseCode != HttpURLConnection.HTTP_OK) {
					final String em = getErrorMessage(result);
					notifyError(em != null? em: lastErrorMsg);
					return;
				}
				JSONObject resd = null;
				JSONArray resa = null;
				int resultOffset = 0;
				ArrayList<Character> characterList = new ArrayList<Character>();
				try {
					resd = result.getJSONObject("data");
					try { resultOffset = resd.getInt("offset"); } catch (JSONException e) {}
					resa = resd.getJSONArray("results");
					for (int i=0; i<resa.length(); i++) {
						JSONObject resc = resa.getJSONObject(i);
						Character c;
						if ((c=getCharacterData(resc)) == null) {
							return;
						}
						characterList.add(c);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					notifyError("JSON error processing results for character data");
					return;
				}
				if (listener != null) {
					listener.characterListResult(resultOffset, characterList);
				}
			}

			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();
			}

			@Override
			protected void onProgressUpdate(Integer ... progressValues)
			{
				super.onProgressUpdate(progressValues);
			}
		};
		String baseURL = urlBase+"characters";
		String targetURL = makeAuthorizedURL(baseURL);
		Log.d("Slurper", "Made URL "+targetURL);
		if (offset > 0) {
			targetURL += "&offset="+offset;
		}
		if (limit > 0) {
			targetURL += "&limit="+limit;
		}
		if (query != null && !query.equals("")) {
			targetURL += "&nameStartsWith="+query;
		}
		loader.execute(targetURL);

	}

	public void getThumbnailResourceAtURI(final NamedResource r, final ImageView v) {
		final JSONSlurper loader = new JSONSlurper() {
			@Override
			protected void onPostExecute(JSONObject result) {
				if (result == null) {
					notifyError(lastErrorMsg);
					return;
				}
				if (responseCode != HttpURLConnection.HTTP_OK) {
					final String em = getErrorMessage(result);
					notifyError(em != null? em: lastErrorMsg);
					return;
				}
				try {
					JSONObject resd = result.getJSONObject("data");
					JSONArray resa = resd.getJSONArray("results");
					if (resa.length() == 0) {
						notifyError("result error retrieving resource at "+r.resourceURI);
						return;
					}
					r.thumbnailURI = getImageURL(resa.getJSONObject(0), "thumbnail");
				} catch (JSONException e) {
					notifyError("JSON error processing results for thumbnail resource data");
					return;
				}
				if (v != null) {
					r.setThumbnailBitmap(v);
				}
			}

			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();
			}

			@Override
			protected void onProgressUpdate(Integer ... progressValues)
			{
				super.onProgressUpdate(progressValues);
			}
		};
		String targetURL = makeAuthorizedURL(r.resourceURI);
		loader.execute(targetURL);
	}

	protected Character getCharacterData(final JSONObject res) {
		if (res == null) {
			notifyError("unexpected null character");
			return null;
		}
		try {
			final String name = res.getString("name");
			final String description = res.getString("description");
			final int id = res.getInt("id");
			final String thumbnailURL = getImageURL(res, "thumbnail");
//			Log.d("Slurper", "got "+name + " thumb "+thumbnailURL);
			Character c = new Character(id, name, description, thumbnailURL);
			try {
				final JSONArray urlobj = res.getJSONArray("urls");
				c.addURLList(getURLData(urlobj));
			} catch (JSONException e) {}
			try {
				final JSONObject collectionObj = res.getJSONObject("comics");
				final JSONArray collectionArray = collectionObj.getJSONArray("items");
				c.addComicList(getComicInfo(collectionArray));
			} catch (JSONException e) {}
			try {
				final JSONObject collectionObj = res.getJSONObject("stories");
				final JSONArray collectionArray = collectionObj.getJSONArray("items");
				c.addStoryList(getStoryInfo(collectionArray));
			} catch (JSONException e) {}
			try {
				final JSONObject collectionObj = res.getJSONObject("events");
				final JSONArray collectionArray = collectionObj.getJSONArray("items");
				c.addEventList(getEventInfo(collectionArray));
			} catch (JSONException e) {}
			try {
				final JSONObject collectionObj = res.getJSONObject("series");
				final JSONArray collectionArray = collectionObj.getJSONArray("items");
				c.addSeriesList(getSeriesInfo(collectionArray));
			} catch (JSONException e) {}
			return c;
		} catch (JSONException e) {
			notifyError("Expected field in json object in response for character");
			return null;
		}
	}

	@NonNull
	protected String getImageURL(JSONObject res, String imageName) throws JSONException {
		final JSONObject resth = res.getJSONObject(imageName);
		final String thPath = resth.getString("path");
		final String thExt = resth.getString("extension");
		return thPath + "." + thExt;
	}

	protected ArrayList<InfoURL> getURLData(JSONArray res) {
		ArrayList<InfoURL> ua = new ArrayList<InfoURL>();
		if (res != null) {
			for (int i=0; i<res.length(); i++) {
				String type = "";
				String url = "";
				JSONObject reso = null;
				try {
					reso = res.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (reso != null) {
					try {
						type = reso.getString("type");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						url = reso.getString("url");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ua.add(new InfoURL(type, url));
				}
			}
		}
		return ua;
	}

	protected ArrayList<Comic> getComicInfo(JSONArray res) {
		ArrayList<Comic> ua = new ArrayList<Comic>();
		if (res != null) {
			for (int i=0; i<res.length(); i++) {
				String resourceURI = "";
				String name = "";
				JSONObject reso = null;
				try {
					reso = res.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (reso != null) {
					try {
						resourceURI = reso.getString("resourceURI");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						name = reso.getString("name");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ua.add(new Comic(resourceURI, name));
				}
			}
		}
		return ua;
	}

	protected ArrayList<Story> getStoryInfo(JSONArray res) {
		ArrayList<Story> ua = new ArrayList<Story>();
		if (res != null) {
			for (int i=0; i<res.length(); i++) {
				String resourceURI = "";
				String name = "";
				String type = "";
				JSONObject reso = null;
				try {
					reso = res.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (reso != null) {
					try {
						resourceURI = reso.getString("resourceURI");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						name = reso.getString("name");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						type = reso.getString("type");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ua.add(new Story(resourceURI, name, type));
				}
			}
		}
		return ua;
	}

	protected ArrayList<Event> getEventInfo(JSONArray res) {
		ArrayList<Event> ua = new ArrayList<Event>();
		if (res != null) {
			for (int i=0; i<res.length(); i++) {
				String resourceURI = "";
				String name = "";
				JSONObject reso = null;
				try {
					reso = res.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (reso != null) {
					try {
						resourceURI = reso.getString("resourceURI");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						name = reso.getString("name");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ua.add(new Event(resourceURI, name));
				}
			}
		}
		return ua;
	}


	protected ArrayList<Series> getSeriesInfo(JSONArray res) {
		ArrayList<Series> ua = new ArrayList<Series>();
		if (res != null) {
			for (int i=0; i<res.length(); i++) {
				String resourceURI = "";
				String name = "";
				JSONObject reso = null;
				try {
					reso = res.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (reso != null) {
					try {
						resourceURI = reso.getString("resourceURI");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						name = reso.getString("name");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ua.add(new Series(resourceURI, name));
				}
			}
		}
		return ua;
	}

	private String getErrorMessage(JSONObject result) {
		try {
			String code = result.getString("code");
			String message = result.getString("message");
			return code + ", " + message;
		} catch (JSONException e) {
			return null;
		}
	}

	protected String makeAuthorizedURL(final String url) {
		final Long tsLong = System.currentTimeMillis()/1000;
		final String ts = tsLong.toString();
		final String hash = md5(ts + privateKey + apiKey);
		return url + "?" + "apikey="+apiKey + "&" + "ts=" + ts + "&" + "hash=" + hash;
	}

	public static final String md5(final String s) {
		if (s == null) {
			return "";
		}
		final String MD5 = "MD5";
		try {
			MessageDigest digest = MessageDigest.getInstance(MD5);
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				String h = Integer.toHexString(0xFF & aMessageDigest);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * calls our listener if there's an error
	 * @param msg
	 */
	protected void notifyError(String msg) {
		if (listener != null) {
			listener.error(msg);
		}
	}

	/**
	 * sets up our listener
	 * @param l
	 */
	public void setListener(Listener l )
	{
		listener  = l;
	}

}
