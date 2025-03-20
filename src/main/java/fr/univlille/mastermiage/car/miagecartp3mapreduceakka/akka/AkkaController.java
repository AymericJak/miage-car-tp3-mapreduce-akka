package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

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

    @PostMapping("/process-file-mapping")
    public String processFile(@RequestParam("file-input") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("fileMessage", "Aucun fichier envoyé.");
            return "redirect:/akka";

        }
        File tempFile = File.createTempFile("temp", ".txt");
        file.transferTo(tempFile);
        akkaService.processFile(tempFile);
        redirectAttributes.addFlashAttribute("fileMessage", "Fichier traité");
        return "redirect:/akka";
    }

}
