package io.syndesis.qe.test.atlas.utils;

import org.apache.http.client.fluent.Request;

import java.io.IOException;

/**
 * Created by mmelko on 16/11/2017.
 */
public class Utils {

	private static String JAVA_SERVICE = Constants.BACKEND_URL + "/v2/atlas/java/";

	public static String requestClass(String className) throws IOException {
		final String requestURL = JAVA_SERVICE + "class?className=" + className;
		System.out.println("[INFO] requesting "+ requestURL);
		String resp = Request.Get(requestURL).execute().returnContent().toString();
		return resp;
	}

	public static void startUi() {
		//p = Runtime.getRuntime().exec("npm --prefix " + Constants.UI_PATH + " start");
	}
}
