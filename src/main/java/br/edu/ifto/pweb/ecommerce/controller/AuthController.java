package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.ClientePessoaFisica;
import br.edu.ifto.pweb.ecommerce.model.repository.ClientePessoaFisicaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Transactional
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ClientePessoaFisicaRepository clientePessoaFisicaRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(ClientePessoaFisicaRepository clientePessoaFisicaRepository,
                          RoleRepository roleRepository) {
        this.clientePessoaFisicaRepository = clientePessoaFisicaRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/sign-in")
    public String signInForm() {
        return "/auth/sign-in";
    }

    @GetMapping("/sign-on")
    public ModelAndView signOnStep1Form(ClientePessoaFisica clientePessoaFisica) {
        return new ModelAndView("/auth/sign-on");
    }

    @PostMapping("/sign-on")
    public ModelAndView signOnStep1(@Valid ClientePessoaFisica clientePessoaFisica, BindingResult result) {
        if (result.hasErrors()) {
            return signOnStep1Form(clientePessoaFisica);
        }

        clientePessoaFisica.getUsuario().setRoles(roleRepository.findAllByAuthority("ROLE_USER"));
        clientePessoaFisica.getUsuario().setPassword(
                new BCryptPasswordEncoder().encode(
                        clientePessoaFisica.getUsuario().getPassword())
        );

        clientePessoaFisicaRepository.save(clientePessoaFisica);

        return new ModelAndView("redirect:/auth/sign-in");
    }
}