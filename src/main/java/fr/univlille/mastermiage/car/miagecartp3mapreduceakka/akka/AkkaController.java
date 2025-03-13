package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/akka")
public class AkkaController {

    private AkkaService akkaService;

    @Autowired
    public AkkaController(AkkaService akkaService) {
        this.akkaService = akkaService;
    }


    @GetMapping("")
    private String home() {
        return "akka/home";
    }

    @GetMapping("init")
    private String init(RedirectAttributes redirectAttributes) {
        akkaService.init();

        if (akkaService.getActorSystem() != null) {
            redirectAttributes.addFlashAttribute("message", "Système Akka initialisé avec " +
                    akkaService.getMappers().length + " Mappers et " +
                    akkaService.getReducers().length + " Reducers.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de l'initialisation du système Akka");
        }
        return "redirect:/akka";
    }
}
