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
      READ Operation: http://localhost:8080/demo/allLog
      For read user details
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
     * DELETE Operation: http://localhost:8080/demo/deleteByPizzaId?id=2
     */
    @GetMapping(path="/deleteByPizzaId")
    public @ResponseBody List<PizzaDetails> deletePizzaById(@RequestParam Integer id) {
        return pizzaRepository.deleteByPizzaId(id);
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
}
