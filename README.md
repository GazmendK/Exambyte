# ExamByte

Ein webbasiertes Testsystem für die Klausurzulassung im Programmierpraktikum, entwickelt mit Java und Spring Framework. ExamByte löst Ilias als Testsystem ab und unterstützt die Testausführung, manuelle Korrektur von Freitextaufgaben sowie die Ergebnisdarstellung für Studierende, Korrektor:innen und Organisator:innen.


## Einleitung

ExamByte wurde entwickelt, um Ilias als Testsystem abzulösen. Die Anwendung unterstützt:

- Testausführung durch Studierende
- Manuelle Korrektur von Freitextaufgaben durch Korrektor:innen
- Ergebnisdarstellung für Studierende, Korrektor:innen und Organisator:innen


## Implementierung

Die Anwendung wird als Webanwendung in Java mit dem Spring Framework umgesetzt. Die Architektur folgt dem Onion-Architekturmuster, das durch ArchUnit-Tests und Gradle-Submodule abgesichert wird.

- **Build-Tool:** Gradle
- **Sicherheit:** Spring Security + OAuth2 (GitHub)
- **Templating:** Thymeleaf

## Funktionen

### Userverwaltung

- **Authentifizierung:** Über GitHub OAuth
- **Autorisierung:** Drei Rollen – Studierende, Korrektor:innen, Organisator:innen

Die Rollen werden aus einer Konfigurationsdatei (`application.yml` / `application.properties`) beim Start gelesen.

| Rolle | Berechtigungen |
|---|---|
| Studierende | Testdurchführung, eigene Ergebnisse einsehen, Zulassungsstatus einsehen |
| Korrektor:innen | Zugriff auf zugeteilte Freitextantworten, Punktevergabe und Feedback |
| Organisator:innen | Vollzugriff: Test-Erstellung, alle Antworten, Punkte-Änderungen, Test als „nicht bestanden" markieren, CSV-Export, Übersichten |

### Testtypen

**Multiple-Choice (MC)**

Fragestellung, Antwortmöglichkeiten, maximale Punktzahl, korrekte Antworten, Lösungserklärung. Korrekte Antwort und Erklärung werden nach dem Endzeitpunkt sichtbar.

**Freitextfragen**

Fragestellung, maximale Punktzahl, ein optionaler Lösungsvorschlag. Der Lösungsvorschlag wird nach dem Endzeitpunkt sichtbar. Manuelle Bewertung durch Korrektor:innen oder Organisator:innen. Nicht-leere Einreichungen mit weniger als der Maximalpunktzahl benötigen zwingend ein Feedback.

**Weitere Organisator:innen-Funktionen**

- Korrekturstandsübersicht: Welche Aufgaben noch nicht korrigiert wurden
- Ergebnisübersicht pro Test: Alle Studierenden-Ergebnisse, Navigation zu individuellen Ergebnissen
- Freitextantworten-Ansicht: Alle Antworten pro Freitextaufgabe, sortiert, um identische/ähnliche Abgaben zu erkennen
- CSV-Export der Testergebnisse

## Installation & Betrieb

### Voraussetzungen

- Java 17 oder höher
- Docker & Docker Compose (für Container-Betrieb)
- GitHub OAuth2 App (Client-ID und Secret)

### Build

```bash
./gradlew clean bootJar
```

### Start (lokal)

```bash
java -jar build/libs/exambyte-l9-0.0.1-SNAPSHOT.jar
```

### Start (Docker)

```bash
docker-compose up --build
```

### Konfiguration

Umgebungsvariablen setzen oder `application.properties` anpassen:

```properties
spring.security.oauth2.client.registration.github.client-id=${CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${CLIENT_SECRET}

app.rollen.organisatoren=github-username-orga1,github-username-orga2
app.rollen.korrektoren=github-username-korrektor1,github-username-korrektor2
```