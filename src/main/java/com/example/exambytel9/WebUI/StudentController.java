package com.example.exambytel9.WebUI;

import com.example.exambytel9.geschaeftslogik.*;
import com.example.exambytel9.pseudoDatenbank.AbgabeService;
import com.example.exambytel9.pseudoDatenbank.TestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/Student")
public class StudentController {

    private final TestService testService;
    private final AbgabeService abgabeService;

    public StudentController(TestService testService, AbgabeService abgabeService) {
        this.testService = testService;
        this.abgabeService = abgabeService;
    }

    private String getLogin(Authentication auth) {
        OAuth2User user = (OAuth2User) auth.getPrincipal();
        return user.getAttribute("login");
    }

    @GetMapping
    @Secured("ROLE_STUDENT")
    public String getStudentLanding(Authentication auth, Model model) {
        String login = getLogin(auth);
        model.addAttribute("name", login);
        model.addAttribute("zulassungsStatus", abgabeService.berechneZulassungsStatus(login));
        model.addAttribute("tests", testService.getTests());
        return "Student/studentlanding";
    }

    @GetMapping("/AlleTests")
    @Secured("ROLE_STUDENT")
    public String getAlleTests(Model model) {
        List<WochenTest> aktiveTests = testService.getTests().stream()
                .filter(WochenTest::istGestartet)
                .toList();
        model.addAttribute("tests", aktiveTests);
        return "Student/AlleTests";
    }

    @GetMapping("/test/{id}")
    @Secured("ROLE_STUDENT")
    public String getTestBearbeiten(@PathVariable int id, Authentication auth, Model model) {
        WochenTest test = testService.findById(id);
        String login = getLogin(auth);

        if (!test.istGestartet()) {
            model.addAttribute("fehler", "Dieser Test hat noch nicht begonnen.");
            return "Student/fehler";
        }

        Optional<TestAbgabe> abgabe = abgabeService.findAbgabe(login, id);
        model.addAttribute("test", test);
        model.addAttribute("abgabe", abgabe.orElse(null));
        model.addAttribute("beendet", test.istBeendet());
        return "Student/TestBearbeiten";
    }

    @PostMapping("/test/{id}/submit")
    @Secured("ROLE_STUDENT")
    public String submitTest(@PathVariable int id, Authentication auth,
                              HttpServletRequest request, Model model) {
        WochenTest test = testService.findById(id);
        String login = getLogin(auth);

        if (test.istBeendet()) {
            model.addAttribute("fehler", "Die Abgabefrist für diesen Test ist abgelaufen.");
            return "Student/fehler";
        }

        Map<Integer, String> freitextAntworten = new HashMap<>();
        Map<Integer, List<String>> mcAntworten = new HashMap<>();

        for (Aufgabe aufgabe : test.getAufgaben()) {
            int aufgabeId = aufgabe.getId();
            String paramName = "answer_" + aufgabeId;
            if (aufgabe instanceof Freitext) {
                String antwort = request.getParameter(paramName);
                if (antwort != null) {
                    freitextAntworten.put(aufgabeId, antwort);
                }
            } else if (aufgabe instanceof MultipleChoice) {
                String[] values = request.getParameterValues(paramName);
                if (values != null) {
                    mcAntworten.put(aufgabeId, Arrays.asList(values));
                }
            }
        }

        abgabeService.submitTest(login, id, freitextAntworten, mcAntworten);
        return "Student/submitted";
    }

    @GetMapping("/Ergebnisse")
    @Secured("ROLE_STUDENT")
    public String getErgebnisse(Authentication auth, Model model) {
        String login = getLogin(auth);
        List<TestAbgabe> abgaben = abgabeService.findAbgabenFuerStudent(login);

        List<Map<String, Object>> ergebnisse = new ArrayList<>();
        for (TestAbgabe abgabe : abgaben) {
            Optional<WochenTest> testOpt = testService.findByIdOptional(abgabe.getTestId());
            if (testOpt.isEmpty()) continue;
            WochenTest test = testOpt.get();
            if (!test.istVeroeffentlicht()) continue;

            Map<String, Object> eintrag = new HashMap<>();
            eintrag.put("test", test);
            eintrag.put("abgabe", abgabe);
            eintrag.put("gesamtPunkte", abgabe.getGesamtPunkte());
            eintrag.put("maxPunkte", test.getMaxPunkteTest());
            eintrag.put("bestanden", !abgabe.isManuellNichtBestanden()
                    && test.istBestanden(abgabe.getGesamtPunkte()));
            ergebnisse.add(eintrag);
        }

        model.addAttribute("ergebnisse", ergebnisse);
        model.addAttribute("zulassungsStatus", abgabeService.berechneZulassungsStatus(login));
        return "Student/korrigierteTests";
    }
}
