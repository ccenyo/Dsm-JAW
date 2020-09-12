import Requests.DsmAuth;
import Requests.DsmSharedFolderRequest;
import Responses.DsmSharedFolderResponse;
import Responses.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DsmSharedFolderTest {
    DsmAuth auth;

    @Before
    public void initTest() {
        auth = DsmAuth.fromResource("env.properties");
    }

    @Test
    public void testGetSharedFoldersAndSuccess() {
        DsmClient client = DsmClient.login(auth);

       Response<DsmSharedFolderResponse> response = client.getAllSharedFolders().call();

       Assert.assertTrue(response.isSuccess());
       Assert.assertNotNull(response.getData().getOffset());
       Assert.assertNotNull(response.getData().getTotal());
       Assert.assertNotNull(response.getData().getShares());
       Assert.assertTrue(response.getData().getShares().size() !=0 );
        Assert.assertNull(response.getData().getShares().get(0).getAdditional());
    }

    @Test
    public void testGetSharedFoldersSort() {
        DsmClient client = DsmClient.login(auth);

        Response<DsmSharedFolderResponse> response = client.getAllSharedFolders()
                                                        .addSort(DsmSharedFolderRequest.Sort.atime)
                                                        .addSort(DsmSharedFolderRequest.Sort.name)
                                                        .setSortDirection(DsmSharedFolderRequest.SortDirection.desc)
                                                        .call();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getOffset());
        Assert.assertNotNull(response.getData().getTotal());
        Assert.assertNotNull(response.getData().getShares());
        Assert.assertTrue(response.getData().getShares().size() !=0 );
        Assert.assertNull(response.getData().getShares().get(0).getAdditional());
    }

    @Test
    public void testGetSharedFoldersAdditionalInformation() {
        DsmClient client = DsmClient.login(auth);

        Response<DsmSharedFolderResponse> response = client.getAllSharedFolders()
                .addAdditionalInfo(DsmSharedFolderRequest.Additional.real_path)
                .addAdditionalInfo(DsmSharedFolderRequest.Additional.owner)
                .addAdditionalInfo(DsmSharedFolderRequest.Additional.mount_point_type)
                .addAdditionalInfo(DsmSharedFolderRequest.Additional.perm)
                .addAdditionalInfo(DsmSharedFolderRequest.Additional.time)
                .addAdditionalInfo(DsmSharedFolderRequest.Additional.volume_status)
                .call();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getOffset());
        Assert.assertNotNull(response.getData().getTotal());
        Assert.assertNotNull(response.getData().getShares());
        Assert.assertTrue(response.getData().getShares().size() !=0 );
        Assert.assertTrue(response.getData().getShares().stream().noneMatch(r -> r.getAdditional().getReal_path() == null));
        Assert.assertTrue(response.getData().getShares().stream().noneMatch(r -> r.getAdditional().getOwner() == null));
        Assert.assertTrue(response.getData().getShares().stream().noneMatch(r -> r.getAdditional().getMount_point_type() == null));
        Assert.assertTrue(response.getData().getShares().stream().noneMatch(r -> r.getAdditional().getPerm() == null));
        Assert.assertTrue(response.getData().getShares().stream().noneMatch(r -> r.getAdditional().getTime() == null));
        Assert.assertTrue(response.getData().getShares().stream().noneMatch(r -> r.getAdditional().getVolume_status() == null));
    }

    @Test
    public void testGetSharedFoldersLimit() {
        DsmClient client = DsmClient.login(auth);

        Response<DsmSharedFolderResponse> response = client.getAllSharedFolders()
                .setLimit(2)
                .call();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getOffset());
        Assert.assertNotNull(response.getData().getTotal());
        Assert.assertNotNull(response.getData().getShares());
        Assert.assertEquals(2, response.getData().getShares().size());
    }

    @Test
    public void testGetSharedFoldersOnlyWritable() {
        DsmClient client = DsmClient.login(auth);

        Response<DsmSharedFolderResponse> response = client.getAllSharedFolders()
                .setOnlyWritable(true)
                .call();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getOffset());
        Assert.assertNotNull(response.getData().getTotal());
        Assert.assertNotNull(response.getData().getShares());
    }

}