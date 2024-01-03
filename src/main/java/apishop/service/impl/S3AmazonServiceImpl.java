//package edu.poly.duantotnghiep.service.impl;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import edu.poly.duantotnghiep.service.S3AmazonService;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Date;
//
//@Service
//public class S3AmazonServiceImpl implements S3AmazonService {
//
//    private AmazonS3 s3client;
//
//
//    @Value("${amazonProperties.endpointUrl}")
//    private String endpointUrl;
//
//    @Value("${amazonProperties.bucketName}")
//    private String bucketName;
//
//    @Value("${amazonProperties.accessKey}")
//    private String accessKey;
//
//    @Value("${amazonProperties.secretKey}")
//    private String secretKey;
//
//    //    Đặt thông tin amazon cho ứng dụng
//    @PostConstruct
//    private void initializeAmazon() {
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        this.s3client = new AmazonS3Client(credentials);
//    }
//
//    //  convert MultiPart -> File
//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
//
//    //    Tạo tên duy nhất cho mỗi lần upload sử dụng timestamp
//    private String generateFileName(MultipartFile multiPart) {
//        return
//                new Date().getTime() + "-" +
//                        multiPart.getOriginalFilename().replace(" ", "_");
//    }
//
//    //    PutObjectRequest putObjectRequest = new PutObjectRequest()
////            .withBucketName(bucketName)
////            .withKey(folderName + "/" + fileName);
//    //  upload file to S3 Bucket
//    private void uploadFileTos3bucket(String fileName, File file) {
//        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
////                                              PublicRead ==> Bất cứ ai có link file đều có thể truy cập
//    }
//
//    //  Tạo 1 hàm gọi các phương thức bên trong dưới dạng public
//    @Override
//    public String uploadFile(MultipartFile multipartFile, String folder) {
//
//        String fileUrl = "";
//        try {
//            File file = convertMultiPartToFile(multipartFile);
//            String fileName = folder + "/" + generateFileName(multipartFile);
//            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
//            uploadFileTos3bucket(fileName, file);
//            file.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileUrl;
//    }
//
//    public void deleteFile(String url) {
//        s3client.deleteObject(bucketName, url);
//    }
//
//    @Override
//    public String copyFile(String sourceFolder, String destinationFolder, String fileName) {
//        String sourceKey = sourceFolder + "/" + fileName;
//        String destinationKey = destinationFolder + "/" + fileName;
//
//        s3client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
//        return endpointUrl + "/" + bucketName + "/" + destinationKey;
//    }
//
//    @Override
//    public String toDoUpload(String oldImage, MultipartFile newFile, String folder, boolean isUpdate) {
//        if (isUpdate) {
//            String image = oldImage
//                    .substring(oldImage.lastIndexOf(folder));
////            deleteFile(image);
//        }
//
//        return uploadFile(newFile, folder);
//    }
//}
