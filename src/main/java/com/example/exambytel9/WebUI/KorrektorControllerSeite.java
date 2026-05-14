package com.example.exambytel9.WebUI;

import com.example.exambytel9.pseudoDatenbank.AbgabeService;
import com.example.exambytel9.pseudoDatenbank.TestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/Korrektor")
public class KorrektorControllerSeite {

    private final AbgabeService abgabeService;
    private final TestService testService;

    public KorrektorControllerSeite(AbgabeService abgabeService, TestService testService) {
        this.abgabeService = abgabeService;
        this.testService = testService;
    }

    @GetMapping
    @Secured("ROLE_CORRECTOR")
    public String getKorrektormain(Model model) {
        List<AbgabeService.FreitextKorrekturInfo> offen = abgabeService.getUnkorrigierteFreitexte();
        model.addAttribute("anzahlOffen", offen.size());
        return "Korrektor/korrektormain";
    }

    @GetMapping("/Aufgaben")
    @Secured("ROLE_CORRECTOR")
    public String getAufgabenListe(Model model) {
        List<AbgabeService.FreitextKorrekturInfo> offeneKorrekturen = abgabeService.getUnkorrigierteFreitexte();
        model.addAttribute("korrekturen", offeneKorrekturen);
        return "Korrektor/AufgabenlisteKorrek";
    }

    @GetMapping("/Aufgaben/{testId}/{aufgabeId}/{studentLogin}")
    @Secured("ROLE_CORRECTOR")
    public String getKorrekturFormular(@PathVariable int testId,
                                       @PathVariable int aufgabeId,
                                       @PathVariable String studentLogin,
                                       Model model) {
        var abgabe = abgabeService.findAbgabe(studentLogin, testId)
                .orElseThrow(() -> new IllegalArgumentException("Abgabe nicht gefunden"));
        var test = testService.findById(testId);
        var aufgabe = test.getAufgaben().stream()
                .filter(a -> a.getId() == aufgabeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));

        model.addAttribute("abgabe", abgabe);
        model.addAttribute("test", test);
        model.addAttribute("aufgabe", aufgabe);
        model.addAttribute("antwort", abgabe.getFreitextAntworten().get(aufgabeId));
        model.addAttribute("studentLogin", studentLogin);
        return "Korrektor/korrekturFormular";
    }

    @PostMapping("/Aufgaben/{testId}/{aufgabeId}/{studentLogin}")
    @Secured("ROLE_CORRECTOR")
    public String submitKorrektur(@PathVariable int testId,
                                  @PathVariable int aufgabeId,
                                  @PathVariable String studentLogin,
                                  @RequestParam double punkte,
                                  @RequestParam(required = false) String feedback,
                                  RedirectAttributes redirectAttributes) {
        try {
            abgabeService.korrigiereFreitext(studentLogin, testId, aufgabeId, punkte, feedback);
            redirectAttributes.addFlashAttribute("erfolg", "Korrektur gespeichert.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("fehler", e.getMessage());
            return "redirect:/Korrektor/Aufgaben/" + testId + "/" + aufgabeId + "/" + studentLogin;
        }
        return "redirect:/Korrektor/Aufgaben";
    }
}
