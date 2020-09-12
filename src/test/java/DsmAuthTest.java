import Exeptions.DsmLoginException;
import Requests.DsmAuth;
import org.junit.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DsmAuthTest extends BaseTest {

    @Test
    public void createAuthFromResourceSuccess() throws IOException {
        //Given
        String fileSuccess = "env-success.properties";

        List<String> properties = new ArrayList<>();
        properties.add("host=dummyHost");
        properties.add("port=2000");
        properties.add("username=dummyUsername");
        properties.add("password=dummyPassword");
        File file = makeFile(properties, fileSuccess);

        DsmAuth dsmAuth = DsmAuth.fromFile(file);

        Assert.assertNotNull(dsmAuth);
        Assert.assertEquals(dsmAuth.getHost(), "dummyHost");
        Assert.assertEquals(dsmAuth.getPort(), Integer.valueOf(2000));
        Assert.assertEquals(dsmAuth.getUserName(), "dummyUsername");
        Assert.assertEquals(dsmAuth.getPassword(), "dummyPassword");
    }

    @Test(expected = DsmLoginException.class)
    public void createAuthFromResourceEmptyLineInFile() throws IOException {
        //Given
        String fileSuccess = "env-wrong.properties";

        List<String> properties = new ArrayList<>();
        properties.add("host=dummyHost");
        properties.add("");
        properties.add("username=dummyUsername");
        properties.add("password=dummyPassword");
        File file = makeFile(properties, fileSuccess);
        DsmAuth.fromFile(file);
    }


    @Test(expected = DsmLoginException.class)
    public void createAuthFromResourceEmptyValue() throws IOException {
        //Given
        String fileSuccess = "env-wrong.properties";

        List<String> properties = new ArrayList<>();
        properties.add("host=dummyHost");
        properties.add("port=");
        properties.add("username=dummyUsername");
        properties.add("password=dummyPassword");
        File file = makeFile(properties, fileSuccess);
        DsmAuth.fromFile(file);
    }

    @Test(expected = DsmLoginException.class)
    public void createAuthFromResourceFileNotExist() throws IOException {
        //Given
        String fileSuccess = "env-wrong.properties";

        File file = new File(fileSuccess);
        DsmAuth.fromFile(file);
    }

    @Test(expected = DsmLoginException.class)
    public void createAuthFromResourceParameterMissing() throws IOException {
        //Given
        String fileSuccess = "env-wrong.properties";

        List<String> properties = new ArrayList<>();
        properties.add("host=dummyHost");

        properties.add("username=dummyUsername");
        properties.add("password=dummyPassword");
        File file = makeFile(properties, fileSuccess);
        DsmAuth.fromFile(file);
    }

    @Test
    public void createAuthOfParameters() {
        //Given
        String host = "http://host";
        Integer port = 5000;
        String username = "username";
        String password = "password";

        DsmAuth dsmAuth = DsmAuth.of(host, port, username, password);

        Assert.assertNotNull(dsmAuth);
        Assert.assertEquals(dsmAuth.getHost(), "http://host");
        Assert.assertEquals(dsmAuth.getPort(), Integer.valueOf(5000));
        Assert.assertEquals(dsmAuth.getUserName(), "username");
        Assert.assertEquals(dsmAuth.getPassword(), "password");
    }

    @Test(expected = DsmLoginException.class)
    public void createAuthOfHostIsNull() {
        //Given
        Integer port = 5000;
        String username = "username";
        String password = "password";

        DsmAuth.of(null, port, username, password);
    }

    @Test
    public void createAuthOfPortIsNull() {
        //Given
        String host = "http://host";
        String username = "username";
        String password = "password";

        DsmAuth.of(host, null, username, password);
    }

    @Test(expected = DsmLoginException.class)
    public void createAuthOfUserNameIsNull() {
        //Given
        String host = "http://host";
        Integer port = 5000;
        String password = "password";

        DsmAuth.of(host, port, null, password);
    }

    @Test(expected = DsmLoginException.class)
    public void createAuthOfPasswordIsNull() {
        //Given
        String host = "http://host";
        Integer port = 5000;
        String username = "username";

        DsmAuth.of(host, port, username, null);
    }
}