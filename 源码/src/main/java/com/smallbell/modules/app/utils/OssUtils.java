package com.smallbell.modules.app.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.smallbell.modules.app.constance.AliyunOSSConfigConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Component
public class OssUtils {
    private static String FILE_URL;
    private static final String bucketName = AliyunOSSConfigConstant.BUCKE_NAME;
    private static final String endpoint = AliyunOSSConfigConstant.END_POINT;
    private static final String accessKeyId = AliyunOSSConfigConstant.AccessKey_ID;
    private static final String accessKeySecret = AliyunOSSConfigConstant.AccessKey_Secret;
    private static final String fileHost = AliyunOSSConfigConstant.FILE_HOST;
    private static final String localFileName = AliyunOSSConfigConstant.LOCAL_FILE;

    /**
     * 上传图片
     * @param file
     * @return
     */

    public static String upLoad(MultipartFile file){
        String fileName = file.getOriginalFilename();

        String uploadUrl = null;
        try {
            if (file != null) {
                if (!"".equals(fileName.trim())) {
                    File newFile = new File(fileName);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS

                    // 判断文件
                    if(newFile == null){
                        return null;
                    }
                    // 默认值：true
                    boolean isImage = true;
                    // 判断所要上传的是否是图片，图片可以预览，其他不支持预览
                    try {
                        Image image = ImageIO.read(newFile);
                    } catch (IOException e) {
                        isImage = false;
                        e.printStackTrace();
                    }

                    System.out.println("------OSS文件上传开始--------" + file.getName());

                    // 创建OSSClient实例
                    OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

                    try {
                        // 判断容器是否存在，不存在创建
                        if (!ossClient.doesBucketExist(bucketName)) {
                            ossClient.createBucket(bucketName);
                            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                            ossClient.createBucket(createBucketRequest);
                        }

                        // 设置文件路径和名称
                        String fileUrl = fileHost + "/" + (UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
                        if (isImage) {
                            FILE_URL = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
                        } else {
                            FILE_URL = "非图片，不可预览，文件路径为：" + fileUrl;
                        }

                        // 上传图片
                        PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, newFile));

                        // 设置权限（公开读）
                        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
                        if (result != null) {
                            System.out.println("------OSS文件上传成功--------" + file.getName());
                        }
                    } catch (OSSException oe) {
                        System.out.println(oe.getMessage());
                    } catch (ClientException ce) {
                        System.out.println(ce.getErrorMessage());
                    } finally {
                        ossClient.shutdown();
                    }

                    uploadUrl = FILE_URL;

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return uploadUrl;
    }

    /**
     * 下载图片
     * @param objectName
     */
    public static void downloadFile(String objectName) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 下载OSS文件到本地，如果本地存在会被覆盖，不存在就创建
        String fielName = objectName.substring(objectName.indexOf("-")+1,objectName.length());
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFileName + fielName));

        // 关闭ossClient
        ossClient.shutdown();
    }

    /**
     * 浏览图片
     * @return
     */
    public static java.util.List<Map<String, Object>> listFile() {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        //列出文件
        ObjectListing listing = ossClient.listObjects(new ListObjectsRequest(bucketName).withPrefix("test/"));

        //遍历所有文件
        java.util.List<OSSObjectSummary> ossObjectSummaryList = listing.getObjectSummaries();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < ossObjectSummaryList.size(); i ++ ) {
            //去除第一个文件夹名称
            if (i >= 1){
                Map<String, Object> map = new HashMap<>();
                map.put("url", "https://" + bucketName + "." + endpoint + "/" + ossObjectSummaryList.get(i).getKey());
                map.put("fileName", ossObjectSummaryList.get(i).getKey());
                list.add(map);
            }
        }
        //关闭ossClient
        ossClient.shutdown();
        return list;
    }

    /**
     * 删除文件
     * @param objectName
     */
    public static void deleteFile(String objectName){
        //创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        //删除文件
        ossClient.deleteObject(bucketName, objectName);

        //关闭OSSClient
        ossClient.shutdown();
    }
}
