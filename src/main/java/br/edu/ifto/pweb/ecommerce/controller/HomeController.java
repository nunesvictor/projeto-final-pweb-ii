package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("/")
public class HomeController {
    private final ProdutoRepository repository;

    public HomeController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public ModelAndView home(ModelMap modelMap) {
        modelMap.addAttribute("produtos", repository.findAll());
        return new ModelAndView("/home", modelMap);
    }
}
