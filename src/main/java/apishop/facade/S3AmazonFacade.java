//package edu.poly.duantotnghiep.facade;
//
//import edu.poly.duantotnghiep.exception.common.InvalidParamException;
//import edu.poly.duantotnghiep.exception.core.ArchitectureException;
////import edu.poly.duantotnghiep.service.S3AmazonService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//@RequiredArgsConstructor
//public class S3AmazonFacade {
//
////    private final S3AmazonService s3AmazonService;
//
//    public String uploadFile(MultipartFile multipartFile, String folder) throws ArchitectureException {
//        if (multipartFile.isEmpty())
//            throw new InvalidParamException();
//        return s3AmazonService.uploadFile(multipartFile, folder);
//    }
//}
