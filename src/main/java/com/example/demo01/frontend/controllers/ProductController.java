package com.example.demo01.frontend.controllers;

import com.example.demo01.backend.models.Product;
import com.example.demo01.backend.repositories.ProductRepository;
import com.example.demo01.backend.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String showProductList(HttpSession session, Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Product> candidatePage = productService.findPaginated(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("productPage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "ListProduct";
    }

    @GetMapping("/products/add")
    public String showAddProduct() {
        return "AddProduct";
    }



    @PostMapping("/products/save")
    public String add(@ModelAttribute Product model) {
        System.out.println(model);
        productService.save(model);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteCandidate(@PathVariable("id") long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "EditProduct";
    }

    @PostMapping("/products/update")
    public String update(@ModelAttribute Product model) {
        productService.update(model);
        return "redirect:/admin/products";
    }
}
