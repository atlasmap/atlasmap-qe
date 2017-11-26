package io.syndesis.qe.test.atlas.e2e;

import io.syndesis.qe.test.MappingTestClass;
import io.syndesis.qe.test.MappingValidator;
import io.syndesis.qe.test.atlas.AtlasRuntime;
import io.syndesis.qe.test.atlas.utils.Constants;
import io.syndesis.qe.test.atlas.utils.UIStarter;
import io.syndesis.qe.test.atlas.utils.Utils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by mmelko on 02/11/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasRuntime.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class PoCTest {

    private static Process p;

    @Rule
    public UIStarter ui = new UIStarter();

    @Before
    public void beforeMethod() {
        System.setProperty("webdriver.chrome.driver", "/Users/mmelko/Downloads/chromedriver");
        System.setProperty("selenide.browser", "Chrome");
        open(Constants.UI_URL);
    }

    @Test
    public void testTest() throws Exception {

        Thread.sleep(40000);
        final String className = "io.atlasmap.java.test.TargetTestClass";
        String resp = Utils.requestClass(className);
        Assert.assertTrue(resp.contains(className));

        MappingValidator mv = new MappingValidator("combine.xml");
        MappingTestClass test = mv.processMapping(new MappingTestClass());
        Assert.assertEquals(test.getCombineString(), "5 5 5.0");

    }
}
