package seckill.cc.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * description: UploadUtil <br>
 * date: 2022/7/5 18:56 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Component
@Slf4j
public class UploadUtil {
    //源文件名
    private String originalFilename;

    //源文件后缀名
    private String suffix;

    //存入数据库里的tomcat虚拟路径
    private String dbPath;

    //文件大小
    private long size;

    //实际存储路径
    private String realPath;

    private final String ossAccessKeyID;

    private final String ossAccessKeySecret;

    private final String ossEndPoint;

    private final String ossBucketName;

    private final String ossFileSaveBaseFolder;


//    public UploadUtil() {
//        this.ossAccessKeyID = "";
//        this.ossAccessKeySecret = "";
//    }


    public UploadUtil(@Value("${oss.access.key.id}") String ossAccessKeyID,
                      @Value("${oss.access.key.secret}") String ossAccessKeySecret,
                      @Value("${oss.file.endpoint}") String ossEndPoint,
                      @Value("${oss.file.bucketName}") String ossBucketName,
                      @Value("${oss.file.folder}") String ossFileSaveBaseFolder) {
        this.ossAccessKeyID = ossAccessKeyID;
        this.ossAccessKeySecret = ossAccessKeySecret;
        this.ossEndPoint = ossEndPoint;
        this.ossBucketName = ossBucketName;
        this.ossFileSaveBaseFolder = ossFileSaveBaseFolder;
    }

    /**
     * 文件上传工具类
     *
     * @param attach
     * @param request
     * @param uploader 文件上传者
     * @return
     */
    public boolean doUpload(MultipartFile attach, HttpServletRequest request, String uploader) {

        if (!attach.isEmpty()) {
            originalFilename = attach.getOriginalFilename();
            System.out.println("==>上传的文件名：" + originalFilename);

            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            System.out.println("==>上传的文件后缀名：" + suffix);

            size = attach.getSize();
            System.out.println("==>上传文件的大小：" + size);

            String currentFilename = System.currentTimeMillis() + UUID.randomUUID().toString() + suffix;
            System.out.println("==>存储的上传文件名：" + currentFilename);

            realPath = "D:/image/" + uploader;
            System.out.println("==>上传文件保存的真实路径：" + realPath);

            File targetFile = new File(realPath, currentFilename);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            try {
                attach.transferTo(targetFile);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            realPath = realPath + "/" + currentFilename;
//            dbPath =  request.getContextPath() + "/" + uploader + "/" + new SimpleDateFormat("yyyy-MM-dd").format
//            (new Date()) + "/" + currentFilename;
            dbPath = "/" + uploader + "/" + currentFilename;
            return true;
        } else {
            return false;
        }
    }

    /**
     * description: doUploadAliOSS <br>
     * version: 1.0 <br>
     * date: 2022/7/7 13:39 <br>
     * author: objcat <br>
     *
     * @param file 文件对象
     * @return String
     */
    public String doUploadAliOSS(MultipartFile file) {
        return doUploadAliOSS(file, ossAccessKeyID, ossAccessKeySecret, null);
    }

    /**
     * description: doUploadAliOSS <br>
     * version: 1.0 <br>
     * date: 2022/7/7 13:37 <br>
     * author: objcat <br>
     *
     * @param file            文件对象
     * @param accessKeyId     阿里云accessKeyId
     * @param accessKeySecret 阿里云accessKeySecret
     * @param pathOverride    重写存储路径
     * @return java.lang.String
     */
    public String doUploadAliOSS(MultipartFile file, String accessKeyId, String accessKeySecret, String pathOverride) {
        OSS ossClient = new OSSClientBuilder().build(ossEndPoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = file.getInputStream();
            String originalFileName;
            if (pathOverride == null) {
                originalFileName = ossFileSaveBaseFolder + "/" + file.getOriginalFilename();
            } else {
                originalFileName = pathOverride;
            }
            ossClient.putObject(ossBucketName, originalFileName, inputStream);
            return originalFileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public String getUploadFile() {
        return dbPath;
    }
}
