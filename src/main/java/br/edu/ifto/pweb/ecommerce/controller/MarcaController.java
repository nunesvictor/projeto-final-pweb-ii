package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.Marca;
import br.edu.ifto.pweb.ecommerce.model.repository.MarcaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("marcas")
public class MarcaController implements ModelController<Marca, Long> {
    private final MarcaRepository repository;

    public MarcaController(MarcaRepository repository) {
        this.repository = repository;
    }

    @Override
    @GetMapping("/form")
    public ModelAndView form(Marca marca, ModelMap modelMap) {
        return new ModelAndView("/marcas/form", modelMap);
    }

    @Override
    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap) {
        modelMap.addAttribute("marcas", repository.findAll(
                Sort.by(Sort.DEFAULT_DIRECTION, "nome")));
        return new ModelAndView("/marcas/list", modelMap);
    }

    @Override
    @PostMapping("/create")
    public ModelAndView create(Marca marca, BindingResult result) {
        if (result.hasErrors()) {
            return form(marca, new ModelMap());
        }

        repository.save(marca);
        return new ModelAndView("redirect:/marcas/list");
    }

    @Override
    @GetMapping("/recover/{id}")
    public ModelAndView recover(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("marca", repository.findById(id).orElseThrow());
        return new ModelAndView("/marcas/form", modelMap);
    }

    @Override
    @PostMapping("/update")
    public ModelAndView update(Marca marca, BindingResult result) {
        if (result.hasErrors()) {
            return form(marca, new ModelMap());
        }

        repository.save(marca);
        return new ModelAndView("redirect:/marcas/list");
    }

    @Override
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/marcas/list");
    }
}
