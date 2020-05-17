package guru.springframework.services;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(String id, MultipartFile file);
}
