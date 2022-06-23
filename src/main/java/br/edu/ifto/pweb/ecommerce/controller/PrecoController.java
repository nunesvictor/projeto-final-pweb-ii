package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.Preco;
import br.edu.ifto.pweb.ecommerce.model.entity.Produto;
import br.edu.ifto.pweb.ecommerce.model.repository.CategoriaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.PrecoRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.ProdutoRepository;
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
import java.util.Optional;

@Controller
@Transactional
@RequestMapping("precos")
public class PrecoController implements ModelController<Preco, Long> {
    private final PrecoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    public PrecoController(PrecoRepository repository,
                           CategoriaRepository categoriaRepository,
                           ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    @GetMapping("/form")
    public ModelAndView form(Preco preco, ModelMap modelMap) {
        modelMap.addAttribute("produtos", produtoRepository.findAll(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));
        return new ModelAndView("/precos/form", modelMap);
    }

    @Override
    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap) {
        modelMap.addAttribute("categorias", categoriaRepository.findAll());
        modelMap.addAttribute("precos", repository.findAll());
        return new ModelAndView("/precos/list", modelMap);
    }

    @GetMapping("/filter/categoria/{id}")
    public ModelAndView filterByCategoria(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("categoriaId", Long.valueOf(id));
        modelMap.addAttribute("categorias", categoriaRepository.findAll());
        modelMap.addAttribute("precos", repository.findAllByProduto_Categorias_Id(Long.valueOf(id)));

        return new ModelAndView("/precos/list", modelMap);
    }

    @Override
    @PostMapping("/create")
    public ModelAndView create(Preco preco, BindingResult result) {
        return save(preco, result);
    }

    @Override
    @GetMapping("/recover/{id}")
    public ModelAndView recover(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("preco", repository.findById(id).orElseThrow());
        return form(repository.findById(id).orElseThrow(), modelMap);
    }

    @Override
    @PostMapping("/update")
    public ModelAndView update(Preco preco, BindingResult result) {
        return save(preco, result);
    }

    private ModelAndView save(Preco preco, BindingResult result) {
        Optional<Preco> precoAnterior = repository.findFirstByActiveTrueAndProdutoOrderByUpdatedAtDesc(
                preco.getProduto());

        if (preco.isPromocao() && precoAnterior.isPresent() && preco.getValor() >= precoAnterior.get().getValor()) {
            result.rejectValue("valor", "error.valor",
                    "O preço informado como promocional deve ser menor que o preço anterior");
        }

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
