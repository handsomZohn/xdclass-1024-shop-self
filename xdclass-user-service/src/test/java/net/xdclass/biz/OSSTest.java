package net.xdclass.biz;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.UserApplication;
import net.xdclass.config.OSSConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = UserApplication.class)
public class OSSTest {


    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketname}")
    private String bucketname;


    // 这个是官方demo里面给的key不知道干什么的，发现文件上传的oss之后，文件名字变成了这个，
    // 所以干脆把上传到oss文件的名字变为这个吧~~~聪明如我~~~~
    private static String key = "1230666666.txt";

//    傻了 上面的代码不是在OSSConfig里面写过了么

//    @Autowired
//    private OSSConfig ossConfig;


    @Test
    public void ossTest() throws IOException {
        log.info("endpoint:{}", endpoint);
        log.info("accessKeyId:{}", accessKeyId);
        log.info("accessKeySecret:{}", accessKeySecret);
        log.info("bucketname:{}", bucketname);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {

            /*
             * Determine whether the bucket exists
             */
            if (!ossClient.doesBucketExist(bucketname)) {
                /*
                 * Create a new OSS bucket
                 */
                System.out.println("Creating bucket " + bucketname + "\n");
                ossClient.createBucket(bucketname);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketname);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            /*
             * List the buckets in your account
             */
            System.out.println("Listing buckets");

            ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
            listBucketsRequest.setMaxKeys(500);

            for (Bucket bucket : ossClient.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();

            /*
             * Upload an object to your bucket
             */
            System.out.println("Uploading a new object to OSS from a file\n");
            ossClient.putObject(new PutObjectRequest(bucketname, key, createSampleFile()));

            /*
             * Determine whether an object residents in your bucket
             */
            boolean exists = ossClient.doesObjectExist(bucketname, key);
            System.out.println("Does object " + bucketname + " exist? " + exists + "\n");

            ossClient.setObjectAcl(bucketname, key, CannedAccessControlList.PublicRead);
            ossClient.setObjectAcl(bucketname, key, CannedAccessControlList.Default);

            ObjectAcl objectAcl = ossClient.getObjectAcl(bucketname, key);
            System.out.println("ACL:" + objectAcl.getPermission().toString());

            /*
             * Download an object from your bucket
             */
            System.out.println("Downloading an object");
            OSSObject object = ossClient.getObject(bucketname, key);
            System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());

            /*
             * List objects in your bucket by prefix
             */
            System.out.println("Listing objects");
            ObjectListing objectListing = ossClient.listObjects(bucketname, "My");
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                        "(size = " + objectSummary.getSize() + ")");
            }
            System.out.println();

            /*
             * Delete an object
             */
            System.out.println("Deleting an object\n");
            // ossClient.deleteObject(bucketname, key);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }
    }

    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz\n");
        writer.write("0123456789011234567890012345678901123456789001234567890112345678900123456789011234567890\n");
        writer.close();

        return file;
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();

        reader.close();
    }

}
