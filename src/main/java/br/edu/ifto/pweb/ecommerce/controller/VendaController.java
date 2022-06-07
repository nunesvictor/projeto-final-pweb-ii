package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.Cliente;
import br.edu.ifto.pweb.ecommerce.model.entity.Venda;
import br.edu.ifto.pweb.ecommerce.model.repository.ClientePessoaFisicaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("vendas")
public class VendaController {
    private final VendaRepository repository;
    private final ClientePessoaFisicaRepository clientePessoaFisicaRepository;

    @Autowired
    public VendaController(VendaRepository repository, ClientePessoaFisicaRepository clientePessoaFisicaRepository) {
        this.repository = repository;
        this.clientePessoaFisicaRepository = clientePessoaFisicaRepository;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, ModelMap modelMap, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = clientePessoaFisicaRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Venda venda = repository.findById(id).orElseThrow();

        if (!venda.getCliente().getId().equals(cliente.getId())) {
            // HTTP 403 caso a venda não seja vinculada ao cliente
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        modelMap.addAttribute("venda", venda);
        return new ModelAndView("/vendas/detail", modelMap);
    }

    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = clientePessoaFisicaRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        modelMap.addAttribute("vendas", repository.findByCliente(cliente));
        return new ModelAndView("/vendas/list", modelMap);
    }
}
