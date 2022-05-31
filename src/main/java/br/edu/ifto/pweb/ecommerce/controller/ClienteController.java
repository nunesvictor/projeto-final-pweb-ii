package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.ClientePessoaFisica;
import br.edu.ifto.pweb.ecommerce.model.repository.ClientePessoaFisicaRepository;
import org.hibernate.cfg.NotYetImplementedException;
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
import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("clientes")
public class ClienteController implements ModelController<ClientePessoaFisica, Long> {
    private final ClientePessoaFisicaRepository repository;

    @Autowired
    public ClienteController(ClientePessoaFisicaRepository repository) {
        this.repository = repository;
    }

    @Override
    @GetMapping("/form")
    public ModelAndView form(ClientePessoaFisica clientePessoaFisica, ModelMap modelMap) {
        return new ModelAndView("/clientes/form", modelMap);
    }

    @Override
    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap) {
        modelMap.addAttribute("clientes", repository.findAll());
        return new ModelAndView("/clientes/list", modelMap);
    }

    @Override
    public ModelAndView create(ClientePessoaFisica model, BindingResult result) {
        throw new NotYetImplementedException();
    }

    @Override
    @GetMapping("/recover/{id}")
    public ModelAndView recover(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("clientePessoaFisica", repository.getReferenceById(id));
        return form(repository.getReferenceById(id), modelMap);
    }

    @Override
    @PostMapping("/update")
    public ModelAndView update(@Valid ClientePessoaFisica clientePessoaFisica, BindingResult result) {
        if (result.hasErrors()) {
            return form(clientePessoaFisica, new ModelMap());
        }

        repository.save(clientePessoaFisica);
        return new ModelAndView("redirect:/clientes/list");
    }

    @Override
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        throw new NotYetImplementedException();
    }
}
