package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.*;
import br.edu.ifto.pweb.ecommerce.model.repository.ClientePessoaFisicaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.ProdutoRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.VendaRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

@Scope("request")
@Controller
@Transactional
@RequestMapping("carrinho")
public class CarrinhoController {
    private final VendaRepository repository;
    private final ClientePessoaFisicaRepository clientePessoaFisicaRepository;
    private final ProdutoRepository produtoRepository;
    private final Venda venda;

    public CarrinhoController(VendaRepository repository,
                              ClientePessoaFisicaRepository clientePessoaFisicaRepository,
                              ProdutoRepository produtoRepository, Venda venda) {
        this.repository = repository;
        this.clientePessoaFisicaRepository = clientePessoaFisicaRepository;
        this.produtoRepository = produtoRepository;
        this.venda = venda;
    }

    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ClientePessoaFisica cliente = clientePessoaFisicaRepository.findByUsuarioUsername(userDetails.getUsername()).orElseThrow();

        venda.setEndereco(cliente.getEnderecos().get(0));
        modelMap.addAttribute("venda", venda);
        modelMap.addAttribute("cliente", cliente);

        return new ModelAndView("/carrinho");
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<?> addItem(@PathVariable("id") Long id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        Produto produto = optionalProduto.orElseThrow();
        ItemVenda item = new ItemVenda();

        item.setProduto(produto);
        venda.getItens().add(item);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ModelAndView purchase(@Valid Venda venda, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error);
            }

            return new ModelAndView("redirect:/carrinho/list");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = clientePessoaFisicaRepository.findByUsuarioUsername(userDetails.getUsername()).orElseThrow();

        venda.setCliente(cliente);

        for (ItemVenda itemVenda : venda.getItens()) {
            itemVenda.setVenda(venda);
        }

        repository.save(venda);
        this.venda.getItens().clear();

        return new ModelAndView(MessageFormat.format(
                "redirect:/vendas/pagamento/{0}/selecionar", venda.getId()));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        venda.getItens().removeIf(itemVenda -> Objects.equals(itemVenda.getProduto().getId(), id));
        return new ModelAndView("redirect:/carrinho/list");
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<?> check(@PathVariable("id") Long id) {
        boolean inCart = venda.getItens().stream().anyMatch(
                itemVenda -> Objects.equals(itemVenda.getProduto().getId(), id));

        if (inCart) {
            return ResponseEntity.ok(venda.getItens().size());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/size")
    public ResponseEntity<?> size() {
        return ResponseEntity.ok(venda.getItens().size());
    }
}
