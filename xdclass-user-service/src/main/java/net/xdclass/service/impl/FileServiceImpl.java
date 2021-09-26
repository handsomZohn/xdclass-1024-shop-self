package net.xdclass.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.config.OSSConfig;
import net.xdclass.service.FileService;
import net.xdclass.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件上传 实现类
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private OSSConfig ossConfig;

    @Override
    public String uploadUserImg(MultipartFile file) {
        // 获取oss相关配置
        String bucketname = ossConfig.getBucketname();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        // 创建OSS对象 用完记得关闭连接
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 获取文件原名
        String originalFilename = file.getOriginalFilename();

        // JDK8的日期格式
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // 拼装路径
        String folder = dtf.format(ldt);
        String fileName = CommonUtil.generateUUID();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 在OSS上的bucket下创建 user 这个文件夹
        String newFileName = "user/" + folder + "/" + fileName + extension;

        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketname, newFileName, file.getInputStream());
            if (putObjectResult != null) {
                String imgUrl = "https://" + bucketname + "." + endpoint + "/" + newFileName;
                return imgUrl;
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}", e);
        } finally {
            // 关闭OSS服务
            ossClient.shutdown();
        }

        return null;
    }
}
