package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.ItemVenda;
import br.edu.ifto.pweb.ecommerce.model.entity.Produto;
import br.edu.ifto.pweb.ecommerce.model.entity.Venda;
import br.edu.ifto.pweb.ecommerce.model.repository.ProdutoRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.VendaRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Objects;

@Scope("request")
@Controller
@Transactional
@RequestMapping("carrinho")
public class CarrinhoController {
    private final VendaRepository repository;
    private final ProdutoRepository produtoRepository;
    private final Venda venda;

    @Autowired
    public CarrinhoController(VendaRepository repository,
                              ProdutoRepository produtoRepository,
                              Venda venda) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.venda = venda;
    }

    @GetMapping("/list")
    public ModelAndView form(ModelMap modelMap) {
        modelMap.addAttribute("venda", venda);
        return new ModelAndView("/carrinho");
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<?> addItem(@PathVariable("id") Long id) {
        Produto produto = produtoRepository.getReferenceById(id);
//        TODO: Pq?
        Hibernate.initialize(produto);
        ItemVenda item = new ItemVenda();

        item.setProduto(produto);
        item.setVenda(venda);
        venda.getItens().add(item);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ModelAndView purchase(@Valid Venda venda, BindingResult result) {
        for (ItemVenda itemVenda : venda.getItens()) {
            System.out.println(itemVenda);
        }

        this.venda.getItens().clear();
        this.venda.getItens().addAll(venda.getItens());

        return new ModelAndView("redirect:/carrinho/list");
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
