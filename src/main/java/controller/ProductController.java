package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.IManager;
import service.Manager;

import java.util.List;

@Controller
public class ProductController {
    private IManager manager = new Manager();

    @GetMapping("/")
    public String index(Model model) {
        List products = manager.getProductList();
        model.addAttribute("products", products);
        return "/index";
    }

    @GetMapping("/product/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("product", manager.findProductBySeries(id));
        return "/view";
    }

    @GetMapping("/product/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "/create";
    }

    @PostMapping("/product/save")
    public String save(Product product, RedirectAttributes redirectAttributes) {
        product.setSeries((int) (Math.random() * 10000));
        manager.creatNewProduct(product);
        redirectAttributes.addFlashAttribute("message", "New product added");
        return "redirect:/";
    }

    @GetMapping("/product/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("product", manager.findProductBySeries(id));
        return "/edit";
    }

    @PostMapping("/product/update")
    public String update(Product product, RedirectAttributes redirectAttributes) {
        manager.updateProduct(product.getSeries(), product);
        redirectAttributes.addFlashAttribute("message", "Modified product successfully!");
        return "redirect:/";
    }

    @GetMapping("/product/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("product", manager.findProductBySeries(id));
        return "/delete";
    }

    @PostMapping("/product/delete")
    public String delete(Product product, RedirectAttributes redirectAttributes) {
        manager.deleteProduct(product.getSeries());
        redirectAttributes.addFlashAttribute("message","Removed product successfully!" );
        return "redirect:/";
    }

    @GetMapping("product/find")
    public String find(@RequestParam String findingValue, Model model) {
        model.addAttribute("products", manager.findProductByName(findingValue));
        return "/index";
    }
}
