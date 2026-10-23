package de.umfeldradar.thema;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/themen")
public class ThemaController {
    private final ThemaService themaService;
    private final QuelleService quelleService;
    public ThemaController(ThemaService themaService, QuelleService quelleService) {
        this.themaService = themaService;
        this.quelleService = quelleService;
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
    @GetMapping("/{id}/bearbeiten")
    public String showEditForm(@PathVariable UUID id, Model model) {
        model.addAttribute("thema", themaService.findbyID(id));
    return("themen/bearbeiten");
    }
    @PostMapping("/{id}/bearbeiten")
    public String update(@PathVariable UUID id, @RequestParam String titel, @RequestParam String cluster, @RequestParam String beschreibung, @RequestParam String chancen, @RequestParam String risiken, @RequestParam Pestel pestel) {
        themaService.aktualisieren(id, titel, cluster, beschreibung, chancen, risiken, pestel);
        return "redirect:/admin/themen";
    }
    @GetMapping("/{id}/quellen")
    public String loadSources(Model model, @PathVariable UUID id){
        Thema thema = themaService.findbyID(id);
        List<Quelle> quellen = quelleService.findByTopic(thema);
        model.addAttribute("thema", thema);
        model.addAttribute("quellen", quellen);
        return "themen/quellen";
    }
    @GetMapping("/{id}/quellen/neu")
    public String acessSource(Model model, @PathVariable UUID id){
        Thema thema = themaService.findbyID(id);
        model.addAttribute("thema", thema);
        return "themen/quelle-neu";
    }
    @PostMapping("/{id}/quellen")
    public String quelleAnlegen(@PathVariable UUID id, @RequestParam String titel, @RequestParam String url, @RequestParam String herausgeber, @RequestParam LocalDate abrufdatum) {
        Thema  thema = themaService.findbyID(id);
        quelleService.anlegen(thema, url, titel, herausgeber, abrufdatum);
        return "redirect:/admin/themen/" + id + "/quellen";
    }
}