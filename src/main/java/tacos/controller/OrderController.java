package tacos.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.model.CakeOrder;

import java.util.ArrayList;
import java.util.List;

@Slf4j // Simple Logging Facade for Java
@Controller
@RequestMapping("/orders")
@SessionAttributes("cakeOrder")
public class OrderController {

    @GetMapping("/current")
    public String orderForm() {
        return "order_form";
    }

    @PostMapping
    public String processOrder(@Valid CakeOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()){
            List<String> orderErrors = new ArrayList<>();
            for (FieldError fe : errors.getFieldErrors()) {
                orderErrors.add(fe.getDefaultMessage());

            }
            log.error("Cake Order validation errors: {}", orderErrors);

            return "order_form";
        }

        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
