# OpenJev Ticket Triage — Spring Boot demo

A minimal Java demo of a **System One** decision model using Spring Boot, Spring AI TypeSafe, and **OpenJev hosted by Codiv**. Nothing AI-related runs on your Windows PC.

## Architecture

Browser -> Spring Boot -> Spring AI TypeSafe/JevChatModel -> Codiv `/v1/systemone` -> OpenJev -> typed Java result

The application asks three questions in one decision request:

- `category`: technical / billing / security / other
- `action`: auto-route / human-review / escalate
- `severity`: Low / Medium / High / Critical

## Requirements on Windows

- Java 21+
- Maven 3.9+
- A free Codiv API key

Codiv: https://codiv.ai/

## 1. Get the free key

Create a Codiv account and create an API key. Codiv currently documents a free allowance and no card requirement.

The key looks like `sk-codiv-...`.

## 2. Open PowerShell in this project

Check Java and Maven:

```powershell
java -version
mvn -version
```

## 3. Configure OpenJev for this PowerShell window

```powershell
$env:TYPESAFE_API_KEY="sk-codiv-YOUR-KEY"
$env:TYPESAFE_BASE_URL="https://api.codiv.ai"
```

Despite the `TYPESAFE_*` names, this configuration points the TypeSafe-compatible Java client at **Codiv/OpenJev**, not at TypeSafe's proprietary Jev service.

## 4. Run

```powershell
mvn spring-boot:run
```

Then open:

http://localhost:8080

No Docker, Python, CUDA, GPU, vLLM, or local model download is required.

## What to show in the video

1. Start the app from PowerShell.
2. Submit: `Production checkout is down for all customers since 14:05.`
3. Show the three decisions in the browser.
4. Open `JevConfig.java`: explain Choice + Choice + Score.
5. Open `TriageResult.java`: the output is a normal typed Java record.
6. Open `application.properties`: highlight `https://api.codiv.ai` + `openjev-latest`.
7. Explain that OpenJev returns probabilities/typed decisions rather than generating prose.
8. Change the ticket to a billing or security case and run it again.

## Jev vs OpenJev

- **Jev** is TypeSafe AI's model/service.
- **OpenJev** is an independent open System One model/server.
- Codiv exposes OpenJev through a Jev-compatible API.
- The compatibility is why the Spring AI TypeSafe/Jev integration can remain useful while the backend is OpenJev.

## Security

Do not commit your Codiv key. This project reads it from the `TYPESAFE_API_KEY` environment variable.
