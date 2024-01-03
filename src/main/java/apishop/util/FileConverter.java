package apishop.util;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileConverter {
    public static Binary convertMultipartFileToBinary(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        return new Binary(fileBytes);
    }
}
