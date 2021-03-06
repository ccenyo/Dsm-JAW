import clients.DsmFileStationClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import requests.DsmAuth;
import requests.filestation.DsmRequestParameters;
import responses.Response;
import responses.filestation.lists.DsmListFolderResponse;

import java.io.IOException;

public class DsmListFolderTest extends DsmTest{
    DsmAuth auth;

    private static final String ROOT_HOME = "/homes";
    private static final String NOT_ROOT_HOME = "/NotExist";
    private static final String NOT_ROOT_HOME_ERROR_CODE = "408";

    @Before
    public void initTest() throws IOException {
        super.initTest();
        auth = DsmAuth.fromResource("env.properties");
    }

    @Test
    public void testDefaultFolderList() {
        DsmFileStationClient client = DsmFileStationClient.login(auth);

        Response<DsmListFolderResponse> response = client.ls(ROOT_HOME).call();

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNull(response.getError());
        Assert.assertNotNull(response.getData());
    }


    @Test
    public void testGetListFoldersSort() {
        DsmFileStationClient client = DsmFileStationClient.login(auth);

        Response<DsmListFolderResponse> response = client.ls(ROOT_HOME)
                .addSort(DsmRequestParameters.Sort.ATIME)
                .addSort(DsmRequestParameters.Sort.NAME)
                .setSortDirection(DsmRequestParameters.SortDirection.DESC)
                .call();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getOffset());
        Assert.assertNotNull(response.getData().getTotal());
        Assert.assertNotNull(response.getData().getFiles());
        Assert.assertTrue(response.getData().getFiles().size() !=0 );
        Assert.assertNull(response.getData().getFiles().get(0).getAdditional());
    }

    @Test
    public void testGetListFoldersAdditionalInformation() {
        DsmFileStationClient client = DsmFileStationClient.login(auth);

        Response<DsmListFolderResponse> response = client.ls(ROOT_HOME)
                .addAdditionalInfo(DsmRequestParameters.Additional.REAL_PATH)
                .addAdditionalInfo(DsmRequestParameters.Additional.OWNER)
                .addAdditionalInfo(DsmRequestParameters.Additional.MOUNT_POINT_TYPE)
                .addAdditionalInfo(DsmRequestParameters.Additional.PERM)
                .addAdditionalInfo(DsmRequestParameters.Additional.TIME)
                .addAdditionalInfo(DsmRequestParameters.Additional.TYPE)
                .call();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getOffset());
        Assert.assertNotNull(response.getData().getTotal());
        Assert.assertNotNull(response.getData().getFiles());
        Assert.assertTrue(response.getData().getFiles().size() !=0 );
        Assert.assertTrue(response.getData().getFiles().stream().noneMatch(r -> r.getAdditional().getReal_path() == null));
        Assert.assertTrue(response.getData().getFiles().stream().noneMatch(r -> r.getAdditional().getOwner() == null));
        Assert.assertTrue(response.getData().getFiles().stream().noneMatch(r -> r.getAdditional().getMount_point_type() == null));
        Assert.assertTrue(response.getData().getFiles().stream().noneMatch(r -> r.getAdditional().getPerm() == null));
        Assert.assertTrue(response.getData().getFiles().stream().noneMatch(r -> r.getAdditional().getTime() == null));
        Assert.assertTrue(response.getData().getFiles().stream().noneMatch(r -> r.getAdditional().getType() == null));
    }

    @Test
    public void testGetListFoldersLimit() {
        DsmFileStationClient client = DsmFileStationClient.login(auth);

        Response<DsmListFolderResponse> response = client.ls(ROOT_HOME)
                .setLimit(2)
                .call();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getOffset());
        Assert.assertNotNull(response.getData().getTotal());
        Assert.assertNotNull(response.getData().getFiles());
        Assert.assertEquals(2, response.getData().getFiles().size());
    }

    @Test
    public void testGetListFolderRootNotExist() {
        DsmFileStationClient client = DsmFileStationClient.login(auth);

        Response<DsmListFolderResponse> response = client.ls(NOT_ROOT_HOME).call();

        Assert.assertNotNull(response);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getData());
        Assert.assertNotNull(response.getError());
        Assert.assertEquals(NOT_ROOT_HOME_ERROR_CODE, response.getError().getCode());

    }


}
