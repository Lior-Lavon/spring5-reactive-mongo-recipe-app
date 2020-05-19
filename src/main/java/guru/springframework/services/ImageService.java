package guru.springframework.services;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Void> saveImageFile(String id, MultipartFile file);
}
