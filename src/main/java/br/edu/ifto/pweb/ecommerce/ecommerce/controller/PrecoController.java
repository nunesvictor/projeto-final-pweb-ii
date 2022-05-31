package br.edu.ifto.pweb.ecommerce.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.ecommerce.model.entity.Preco;
import br.edu.ifto.pweb.ecommerce.ecommerce.model.repository.PrecoRepository;
import br.edu.ifto.pweb.ecommerce.ecommerce.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("precos")
public class PrecoController implements ModelController<Preco, Long> {
    private final PrecoRepository repository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public PrecoController(PrecoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    @GetMapping("/form")
    public ModelAndView form(Preco preco, ModelMap modelMap) {
        modelMap.addAttribute("produtos", produtoRepository.findAll());
        return new ModelAndView("/precos/form", modelMap);
    }

    @Override
    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap) {
        modelMap.addAttribute("precos", repository.findAll());
        return new ModelAndView("/precos/list", modelMap);
    }

    @Override
    @PostMapping("/create")
    public ModelAndView create(Preco preco, BindingResult result) {
        if (result.hasErrors()) {
            return form(preco, new ModelMap());
        }

        repository.save(preco);
        return new ModelAndView("redirect:/precos/list");
    }

    @Override
    @GetMapping("/recover/{id}")
    public ModelAndView recover(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("preco", repository.getReferenceById(id));
        return form(repository.getReferenceById(id), modelMap);
    }

    @Override
    @PostMapping("/update")
    public ModelAndView update(Preco preco, BindingResult result) {
        if (result.hasErrors()) {
            return form(preco, new ModelMap());
        }

        repository.save(preco);
        return new ModelAndView("redirect:/precos/list");
    }

    @Override
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/precos/list");
    }
}
