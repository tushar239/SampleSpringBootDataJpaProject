package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sample.domain.Person;
import sample.service.PersonService;

import java.util.List;

/**
 * @author Tushar Chokshi @ 7/19/17.
 */
@Controller
public class IndexController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/")
    public String showIndex(Model model) {
        List<Person> personList = personService.loadAll();

        model.addAttribute("personList", personList);

        return "index"; // return index.html Template
    }
}
