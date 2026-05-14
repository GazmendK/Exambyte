package com.example.exambytel9.WebUI;

import com.example.exambytel9.geschaeftslogik.*;
import com.example.exambytel9.pseudoDatenbank.AbgabeService;
import com.example.exambytel9.pseudoDatenbank.TestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/Organisator")
public class OrganisatorControllerSeite {

    private final TestService testService;
    private final AbgabeService abgabeService;

    public OrganisatorControllerSeite(TestService testService, AbgabeService abgabeService) {
        this.testService = testService;
        this.abgabeService = abgabeService;
    }

    @GetMapping
    @Secured("ROLE_ORGANISATOR")
    public String getOrganisatormain() {
        return "Organisator/organisatormain";
    }

    // ---- Test erstellen ----

    @GetMapping("/CreateTest")
    @Secured("ROLE_ORGANISATOR")
    public String getCreateTest(Model model) {
        model.addAttribute("test", new WochenTest());
        return "Organisator/CreateTest";
    }

    @PostMapping("/CreateTest")
    @Secured("ROLE_ORGANISATOR")
    public String processTestForm(@ModelAttribute WochenTest test) {
        testService.saveTest(test);
        return "redirect:/Organisator/Aufgabenliste";
    }

    // ---- Aufgabenliste / Testvorschau ----

    @GetMapping("/Aufgabenliste")
    @Secured("ROLE_ORGANISATOR")
    public String getAufgabenliste(Model model) {
        model.addAttribute("tests", testService.getTests());
        return "Organisator/AufgabenlisteOrga";
    }

    @GetMapping("/AufgabenlisteOrga/{testid}")
    @Secured("ROLE_ORGANISATOR")
    public String getTestvorschau(@PathVariable int testid, Model model) {
        WochenTest test = testService.findById(testid);
        model.addAttribute("aufgaben", test.getAufgaben());
        model.addAttribute("wochenTest", test);
        return "Organisator/testVorschau";
    }

    // ---- MC-Aufgabe hinzufügen ----

    @GetMapping("/Aufgabenliste/Mc")
    @Secured("ROLE_ORGANISATOR")
    public String getAddTaskMc(@RequestParam int testId, Model model) {
        WochenTest test = testService.findById(testId);
        model.addAttribute("neueAufgabe", new MultipleChoiceFormDTO());
        model.addAttribute("test", test);
        return "Organisator/addTaskMc";
    }

    @PostMapping("/Aufgabenliste/Mc")
    @Secured("ROLE_ORGANISATOR")
    public String addTaskMc(@ModelAttribute MultipleChoiceFormDTO aufgabeForm,
                             @RequestParam int testId) {
        WochenTest test = testService.findById(testId);
        int neueId = test.getAufgaben().size() + 1;
        MultipleChoice aufgabe = aufgabeForm.toMultipleChoice(neueId);
        test.addAufgabe(aufgabe);
        return "redirect:/Organisator/Aufgabenliste";
    }

    // ---- Freitext-Aufgabe hinzufügen ----

    @GetMapping("/Aufgabenliste/Text")
    @Secured("ROLE_ORGANISATOR")
    public String getAddTaskText(@RequestParam int testId, Model model) {
        WochenTest test = testService.findById(testId);
        model.addAttribute("neueAufgabe", new Freitext());
        model.addAttribute("test", test);
        return "Organisator/addTaskText";
    }

    @PostMapping("/Aufgabenliste/Text")
    @Secured("ROLE_ORGANISATOR")
    public String addTaskText(@ModelAttribute Freitext aufgabe,
                               @RequestParam int testId) {
        WochenTest test = testService.findById(testId);
        aufgabe.setId(test.getAufgaben().size() + 1);
        aufgabe.setTestArt(false);
        test.addAufgabe(aufgabe);
        return "redirect:/Organisator/Aufgabenliste";
    }

    // ---- Ergebnisstand ----

    @GetMapping("/Ergebnisstandseite")
    @Secured("ROLE_ORGANISATOR")
    public String getErgebnisstand(Model model) {
        model.addAttribute("tests", testService.getTests());
        return "Organisator/Ergebnisstandseite";
    }

    @GetMapping("/Ergebnisstandseite/{testId}")
    @Secured("ROLE_ORGANISATOR")
    public String getErgebnisseProTest(@PathVariable int testId, Model model) {
        WochenTest test = testService.findById(testId);
        List<TestAbgabe> abgaben = abgabeService.findAbgabenFuerTest(testId);

        List<Map<String, Object>> ergebnisse = new ArrayList<>();
        for (TestAbgabe abgabe : abgaben) {
            Map<String, Object> eintrag = new HashMap<>();
            eintrag.put("abgabe", abgabe);
            eintrag.put("gesamtPunkte", abgabe.getGesamtPunkte());
            eintrag.put("bestanden", !abgabe.isManuellNichtBestanden()
                    && test.istBestanden(abgabe.getGesamtPunkte()));
            ergebnisse.add(eintrag);
        }

        model.addAttribute("test", test);
        model.addAttribute("ergebnisse", ergebnisse);
        return "Organisator/ErgebnisseProTest";
    }

    @PostMapping("/Ergebnisstandseite/{testId}/{studentLogin}/nichtbestanden")
    @Secured("ROLE_ORGANISATOR")
    public String markiereNichtBestanden(@PathVariable int testId,
                                          @PathVariable String studentLogin,
                                          @RequestParam String begruendung,
                                          RedirectAttributes redirectAttributes) {
        abgabeService.markiereNichtBestanden(studentLogin, testId, begruendung);
        redirectAttributes.addFlashAttribute("erfolg", "Test als nicht bestanden markiert.");
        return "redirect:/Organisator/Ergebnisstandseite/" + testId;
    }

    // ---- Korrekturstand ----

    @GetMapping("/Korrekturstandseite")
    @Secured("ROLE_ORGANISATOR")
    public String getKorrekturstand(Model model) {
        List<AbgabeService.FreitextKorrekturInfo> offen = abgabeService.getUnkorrigierteFreitexte();
        model.addAttribute("offeneKorrekturen", offen);
        model.addAttribute("tests", testService.getTests());
        return "Organisator/Korrekturstandseite";
    }

    // ---- Freitextantworten ----

    @GetMapping("/Freitextantworten")
    @Secured("ROLE_ORGANISATOR")
    public String getFreitextantworten(Model model) {
        List<AbgabeService.FreitextKorrekturInfo> alle = abgabeService.getUnkorrigierteFreitexte();
        alle.sort(Comparator.comparing(AbgabeService.FreitextKorrekturInfo::antwort));
        model.addAttribute("freitextAntworten", alle);
        model.addAttribute("tests", testService.getTests());
        return "Organisator/Freitextantwortenseite";
    }

    // ---- Korrektur durch Organisator ----

    @PostMapping("/Korrigieren/{testId}/{aufgabeId}/{studentLogin}")
    @Secured("ROLE_ORGANISATOR")
    public String korrigiereAlsOrganisator(@PathVariable int testId,
                                            @PathVariable int aufgabeId,
                                            @PathVariable String studentLogin,
                                            @RequestParam double punkte,
                                            @RequestParam(required = false) String feedback,
                                            RedirectAttributes redirectAttributes) {
        try {
            abgabeService.korrigiereFreitext(studentLogin, testId, aufgabeId, punkte, feedback);
            redirectAttributes.addFlashAttribute("erfolg", "Punkte gespeichert.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("fehler", e.getMessage());
        }
        return "redirect:/Organisator/Ergebnisstandseite/" + testId;
    }
}
