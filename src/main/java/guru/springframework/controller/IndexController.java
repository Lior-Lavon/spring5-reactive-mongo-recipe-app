package guru.springframework.controller;

import guru.springframework.models.Recipe;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/"})
    public String getIndexPage(Model model){

        log.debug("Getting index page");

        model.addAttribute("recipes", recipeService.getRecipes().collectList().block());

        return "index";
    }


}
