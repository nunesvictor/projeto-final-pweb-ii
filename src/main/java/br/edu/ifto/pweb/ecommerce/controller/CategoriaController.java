package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.Categoria;
import br.edu.ifto.pweb.ecommerce.model.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("categorias")
public class CategoriaController implements ModelController<Categoria, Long> {
    final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    @GetMapping("/form")
    public ModelAndView form(Categoria categoria, ModelMap modelMap) {
        return new ModelAndView("/categorias/form");
    }

    @Override
    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap) {
        modelMap.addAttribute("categorias", repository.findAll());
        return new ModelAndView("/categorias/list", modelMap);
    }

    @Override
    @PostMapping("/create")
    public ModelAndView create(@Valid Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return form(categoria, new ModelMap());
        }

        repository.save(categoria);
        return new ModelAndView("redirect:/categorias/list");
    }

    @Override
    @GetMapping("/recover/{id}")
    public ModelAndView recover(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("categoria", repository.getReferenceById(id));
        return new ModelAndView("/categorias/form", modelMap);
    }

    @Override
    @PostMapping("/update")
    public ModelAndView update(@Valid Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return form(categoria, new ModelMap());
        }

        repository.save(categoria);
        return new ModelAndView("redirect:/categorias/list");
    }

    @Override
    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/produtos/list");
    }
}
