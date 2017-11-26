package io.syndesis.qe.test.atlas.utils;

import org.junit.rules.ExternalResource;

/**
 * Created by mmelko on 23/11/2017.
 */
public class UIStarter extends ExternalResource {
    private static Process p;

    @Override
    protected void after() {
        p.destroy();
        super.after();

    }

    @Override
    protected void before() throws Throwable {
        super.before();
        p = Runtime.getRuntime().exec("npm --prefix " + Constants.UI_PATH + " start");
    }

    public void initUI() {
        //TODO
        // add custom json/xml/java source/targets
    }
}
