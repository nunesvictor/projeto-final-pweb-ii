package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.Cliente;
import br.edu.ifto.pweb.ecommerce.model.entity.ClientePessoaFisica;
import br.edu.ifto.pweb.ecommerce.model.entity.Endereco;
import br.edu.ifto.pweb.ecommerce.model.repository.ClientePessoaFisicaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@Transactional
@RequestMapping("clientes")
public class ClienteController implements ModelController<ClientePessoaFisica, Long> {
    private final ClientePessoaFisicaRepository repository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public ClienteController(ClientePessoaFisicaRepository repository, EnderecoRepository enderecoRepository) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    @GetMapping("/form")
    public ModelAndView form(ClientePessoaFisica clientePessoaFisica, ModelMap modelMap) {
        return new ModelAndView("/clientes/form", modelMap);
    }

    @GetMapping("/enderecos/form")
    public ModelAndView formEndereco(Endereco endereco, ModelMap modelMap) {
        return new ModelAndView("/clientes/enderecos/form", modelMap);
    }

    @Override
    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap) {
        modelMap.addAttribute("clientes", repository.findAll());
        return new ModelAndView("/clientes/list", modelMap);
    }

    @GetMapping("/enderecos/list")
    public ModelAndView listEnderecos(ModelMap modelMap, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = repository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Endereco> enderecos = cliente.getEnderecos();

        modelMap.addAttribute("enderecos", enderecos);
        return new ModelAndView("/clientes/enderecos/list", modelMap);
    }

    @Override
    public ModelAndView create(ClientePessoaFisica clientePessoaFisica, BindingResult result) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/enderecos/create")
    public ModelAndView createEndereco(Endereco endereco, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return formEndereco(endereco, new ModelMap());
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = repository.findByUsername(userDetails.getUsername()).orElseThrow();
        cliente.getEnderecos().add(endereco);

        return new ModelAndView("redirect:/clientes/enderecos/list");
    }

    @Override
    @GetMapping("/recover/{id}")
    public ModelAndView recover(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("clientePessoaFisica", repository.findById(id).orElseThrow());
        return form(repository.findById(id).orElseThrow(), modelMap);
    }

    @GetMapping("/enderecos/recover/{id}")
    public ModelAndView recoverEndereco(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("endereco", enderecoRepository.findById(id).orElseThrow());
        return formEndereco(enderecoRepository.findById(id).orElseThrow(), modelMap);
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

    @PostMapping("/enderecos/update")
    public ModelAndView update(Endereco endereco, BindingResult result) {
        if (result.hasErrors()) {
            return formEndereco(endereco, new ModelMap());
        }

        return new ModelAndView("redirect:/clientes/enderecos/list");
    }

    @Override
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/enderecos/delete/{id}")
    public ModelAndView deleteEndereco(@PathVariable("id") Long id) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
