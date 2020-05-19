package guru.springframework.services;

import guru.springframework.models.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String id, MultipartFile file) {

        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                        try {
                            byteObjects = new Byte[file.getBytes().length];

                            int i = 0;

                            for (byte b : file.getBytes()) {
                                byteObjects[i++] = b;
                            }

                            recipe.setImage(byteObjects);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    return recipe;
                });

        recipeReactiveRepository.save(recipeMono.block()).block();

        return Mono.empty();

        // save the file image to the DB
//        Recipe recipe = recipeReactiveRepository.findById(id).block();
//        if(recipe!=null){
//            Byte[] byteObjects = new Byte[0];
//            try {
//                byteObjects = new Byte[file.getBytes().length];
//
//                int i=0;
//
//                for( byte b : file.getBytes()){
//                    byteObjects[i++] = b;
//                }
//
//                recipe.setImage(byteObjects);
//
//                recipeReactiveRepository.save(recipe).block();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return Mono.empty();
    }
}
