package pet.shelter.adoption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {

    private Animals animals;
    private List<Animal> listOfAnimals;

    public AppController(Animals animals) {
        animals.addAnimal(new Animal("Mania", "Dog", "Biszkoptowy Labrador"));
        animals.addAnimal(new Animal("Muniek", "Cat", "Pospolity dachowiec"));
        animals.addAnimal(new Animal("Pinki", "Rat", "Szczur doswiadczalny"));
        listOfAnimals = animals.getAnimals();
        this.animals = animals;
    }

    // display home page
    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("animal", new Animal());
        model.addAttribute("listOfAnimals", listOfAnimals);
        return "index";
    }

    // add animal
    @PostMapping("/addAnimal")
    public String addAnimal(@ModelAttribute Animal animal, Model model) {
        model.addAttribute("animal", animal);
        animals.addAnimal(animal);
        return "redirect:/";
    }

    // display animal
    @GetMapping("/animal")
    public String animal(@RequestParam String name, Model model) {
        Animal animal = getAnimalByName(name);
        model.addAttribute("animal", animal);
        return "animal";
    }

    // filter animals by type
    @PostMapping("/filterByType")
    public String filterByType(@RequestParam String type, Model model) {
        if (type.equals("All Animals")) {
            listOfAnimals = animals.getAnimals();
        } else {
            listOfAnimals = filterByType(type);
        }
        return "redirect:/#animalsForAdoption";
    }

    // method to get animal by name
    public Animal getAnimalByName(String nameOfAnimal) {
        listOfAnimals = animals.getAnimals();
        Animal animal = new Animal();
        for (int i = 0; i < listOfAnimals.size(); i++) {
            if (listOfAnimals.get(i).getName().equals(nameOfAnimal)) {
                String name;
                String type;
                String description;

                name = listOfAnimals.get(i).getName();
                type = listOfAnimals.get(i).getType();
                description = listOfAnimals.get(i).getDescription();

                animal = new Animal(name, type, description);
            }
        }
        return animal;
    }

    // method to filter animal by type
    public List<Animal> filterByType(String type) {
        listOfAnimals = animals.getAnimals();
        List<Animal> listOfAnimalsByType = new ArrayList<>();
        for (Animal animal : listOfAnimals) {
            if (animal.getType().equals(type)) {
                listOfAnimalsByType.add(animal);
            }
        }
        return listOfAnimalsByType;
    }
}
