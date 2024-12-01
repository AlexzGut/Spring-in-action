package tacos.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

import tacos.models.Cake;
import tacos.models.CakeOrder;
import tacos.models.Ingredient.Type;
import tacos.models.Ingredient;

@Slf4j // This annotation generate a (Simple Logging Facade for Java) Logger static property in the class.
@Controller
@RequestMapping("/create")
@SessionAttributes("cakeOrder") // indicates that the CakeOrder object added to the model by the cakeOrder method should be maintained in session.
public class CreateCakeController {

    // The methods annotated with @ModelAttribute are executed before any handler methods like (GetMapping or PostMapping) are invoked
    // This functionality ensures attributes are added to the model and available to the views or other controller methods

    // The order of execution of the methods annotated with @MethodAttribute is from top to bottom in the order
    // they are declared in the controller. If a controller extend another controller the methods annotated with @MethodAttribute
    // in the parent controller are executed first.
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
          new Ingredient("PIAP", "Pineapple", Type.FLAVORS),
          new Ingredient("CHCH", "Chocolate Chips", Type.ADDITIONS)
        );

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    // this annotation is used in Spring MVC to indicate that
    // the CakeOrder object returned by the method should be added to the model
    // with the name specified by 'name' -> "cakeOrder"
    @ModelAttribute(name = "cakeOrder")
    public CakeOrder order() {
        return new CakeOrder();
    }

    @ModelAttribute(name = "cake")
    public Cake cake() {
        return new Cake();
    }

    @GetMapping // when an HTTP GET request is received for /create, Spring MVC will call this method.
    public String displayCreateCakeForm() {
        return "create_cake";
    }

    @PostMapping
    // CakeOrder object is explicitly annotated with @ModelAttribute because it is not the primary form object bound to the request
    // Note: For complex objects like Cake, Spring teats them as @ModelAttribute
    public String processCake(@Valid Cake cake, Errors errors, @ModelAttribute CakeOrder cakeOrder) {
        if (errors.hasErrors()) {
            log.error("Errors: {}", errors.getAllErrors());
            return "create_cake";
        }
        cakeOrder.addCake(cake);
        log.info("Processing cake: {}", cake);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(t -> t.getType().equals(type))
                .toList();
    }
}