package com.example.demo.web;

import com.example.demo.models.binding.UserRegisterBindingModel;
import com.example.demo.models.service.UserServiceModel;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String register(Model model){
        return "register";
    }
    @PostMapping("/register")
    public String confirmRegister(UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            redirectAttributes
                                .addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel)
                                .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel"
                                        ,bindingResult);
            return "redirect:register";
        }
        userService.registerUser(
                modelMapper
                            .map(userRegisterBindingModel,
                                UserServiceModel.class));
       return "redirect:login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}