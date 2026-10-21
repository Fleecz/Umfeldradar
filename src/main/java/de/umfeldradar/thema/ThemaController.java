package de.umfeldradar.thema;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin/themen")
public class ThemaController {
    private final ThemaService themaService;
    public ThemaController(ThemaService themaService) {
        this.themaService = themaService;
    }
    @GetMapping
    public String liste(@RequestParam(required = false) Pestel pestel, @RequestParam(required = false) Herkunft herkunft, @RequestParam(required = false) ThemaStatus status, @RequestParam(required = false) String suchtext, Model model) {
        model.addAttribute("themen", themaService.filtern(pestel, herkunft, status, suchtext));
        model.addAttribute("gewaehlterStatus", status);
        return "themen/liste";
    }
    @GetMapping("/neu")
    public String neu() {
        return "themen/neu";
    }
    @PostMapping
    public String anlegen(@RequestParam String titel, @RequestParam String cluster, @RequestParam String beschreibung, @RequestParam String chancen, @RequestParam String risiken, @RequestParam Pestel pestel, @RequestParam Herkunft herkunft) {
        themaService.anlegen(titel, cluster, beschreibung, chancen, risiken, pestel, herkunft, ThemaStatus.AKTIV );
        return "redirect:/admin/themen";
    }
   @PostMapping("/{id}/archivieren")
    public String archivieren(@PathVariable UUID id) {
        themaService.archivieren(id);
        return "redirect:/admin/themen";
    }
}