package pizzaloop;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private CartRepository cartRepository;
    private static final String SUCCESS= "Saved";
    /*
      READ Operation: http://localhost:8080/demo/all
      For read pizza details
     */
    @GetMapping(path="/all")
    public @ResponseBody Iterable<PizzaDetails> getPizzaDetails() {
        return pizzaRepository.findAll();
    }
    /*
      READ Operation: http://localhost:8080/demo/allCart
     */
    @GetMapping(path="/allCart")
    public @ResponseBody Iterable<Cart> getCart() {
        return cartRepository.findAll();
    }

    /*
      READ Operation: http://localhost:8080/demo/allLog
     */
    @GetMapping(path="/allLog")
    public @ResponseBody Iterable<Login> getLogin() {
        return loginRepository.findAll();
    }

    /*
     * READ byID: http://localhost:8080/demo/findByPizzaId?id=2
     * For read pizza details
     */
    @GetMapping(path="/findByPizzaId")
    public @ResponseBody List<PizzaDetails> getPizzaById(@RequestParam Integer id) {
        return pizzaRepository.findByPizzaId(id);
    }

    /*
     * READ byID: http://localhost:8080/demo/findByPname?pname=Pizza
     * For read pizza details
     */
    @GetMapping(path="/findByPname")
    public @ResponseBody List<Cart> getPizzaByName(@RequestParam String pname) {
        return cartRepository.findByPname(pname);
    }
    /*
     * READ byID: http://localhost:8080/demo/findByUsername?username=admin
     * For read user details
     */
    @GetMapping(path="/findByUserid")
    public @ResponseBody List<Login> findByUserid(@RequestParam Integer userid) {
        return loginRepository.findByUserid(userid);
    }

    /*
     * CREATE Operation: http://localhost:8080/demo/add?name=VegiPizza&description=VegiSupreme&price=2500.75&imageUrl=http://192.168.8.179/New/logonew.png
     * add pizza details
     */
    @GetMapping(path="/add")
    public @ResponseBody String addNewPizza(@RequestParam String name, @RequestParam String description, @RequestParam Double price, @RequestParam String imageUrl) {
        PizzaDetails pizzaDetails = new PizzaDetails();
        pizzaDetails.setName(name);
        pizzaDetails.setDescription(description);
        pizzaDetails.setPrice(price);
        pizzaDetails.setImageUrl(imageUrl);
        pizzaRepository.save(pizzaDetails);
        return SUCCESS;
    }
    /*
     * CREATE Operation: http://localhost:8080/demo/addLog?username=samantha&password=abcd
     * add user details
     */
    @GetMapping(path="/addLog")
    public @ResponseBody String addNewUser(@RequestParam String username, @RequestParam String password) {
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);
        loginRepository.save(login);
        return SUCCESS;
    }

    /*
     * CREATE Operation: http://localhost:8080/demo/addCart?pname=Pizza 0&quantity=1&nprice=250.50
     * add cart details
     */
    @GetMapping(path="/addCart")
    public @ResponseBody String addNewCartItem(@RequestParam String pname, @RequestParam int quantity,@RequestParam double nprice) {
        Cart cart = new Cart();
        cart.setPname(pname);
        cart.setQuantity(quantity);
        cart.setNprice(nprice);
        cartRepository.save(cart);
        return SUCCESS;
    }

    /*
     * DELETE Operation: http://localhost:8080/demo/deleteByPizzaId?id=2
     */
    @GetMapping(path="/deleteByPizzaId")
    public @ResponseBody List<PizzaDetails> deletePizzaById(@RequestParam Integer id) {
        return pizzaRepository.deleteByPizzaId(id);
    }

    /*
     * DELETE Operation: http://localhost:8080/demo/deleteByPname?pname=Pizza
     */
    @GetMapping(path="/deleteByPname")
    public @ResponseBody List<Cart> deleteByPname(@RequestParam String pname) {
        return cartRepository.deleteByPname(pname);
    }

    /*
     * UPDATE Operation: http://localhost:8080/demo/update?id=1&name=updatedname&description=updated&price=1234.56
     */
    @GetMapping(path="/update")
    public @ResponseBody List<PizzaDetails> updatePizzaDetails(@RequestParam Integer id, @RequestParam String name, @RequestParam String description, @RequestParam Double price) {

        List<PizzaDetails> pizzaDetailsList = pizzaRepository.findByPizzaId(id);
        if(!pizzaDetailsList.isEmpty()) {
            for(PizzaDetails pizzaDetails: pizzaDetailsList) {
                pizzaDetails.setName(name);
                pizzaDetails.setDescription(description);
                pizzaDetails.setPrice(price);
                pizzaRepository.save(pizzaDetails);
            }
        }
        return pizzaRepository.findByPizzaId(id);
    }

    /*
     * UPDATE Operation: http://localhost:8080/demo/updateCart?pname=Pizza&quantity=5&nprice=1234.56
     */
    @GetMapping(path="/updateCart")
    public @ResponseBody List<Cart> updateCartDetails(@RequestParam String pname, @RequestParam int quantity,@RequestParam double nprice) {

        List<Cart> cartDetailsList = cartRepository.findByPname(pname);
        if(!cartDetailsList.isEmpty()) {
            for(Cart cartDetails: cartDetailsList) {
                cartDetails.setPname(pname);
                cartDetails.setQuantity(quantity);
                cartDetails.setNprice(nprice);
                cartRepository.save(cartDetails);
            }
        }
        return cartRepository.findByPname(pname);
    }
}
