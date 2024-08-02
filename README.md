# GitHub API Application

## Opis
Aplikacja Spring Boot pobierająca informacje o repozytoriach użytkownika GitHub, które nie są forkami. Aplikacja zwraca nazwę repozytorium, login właściciela oraz nazwę każdej gałęzi i ostatni commit SHA.

## Wymagania
- Java 21
- Maven lub Gradle
- Konto GitHub i token dostępu osobistego

## Instalacja

1. Sklonuj repozytorium:
    ```bash
    git clone https://github.com/twoj-repozytorium/githubapi.git
    cd githubapi
    ```

2. Utwórz token dostępu osobistego na GitHub:
    - Zaloguj się na swoje konto GitHub.
    - Przejdź do `Settings` -> `Developer settings` -> `Personal access tokens`.
    - Kliknij `Generate new token`.
    - Nadaj tokenowi odpowiednią nazwę i uprawnienia (np. `repo` do odczytu repozytoriów).
    - Skopiuj wygenerowany token.

3. Skonfiguruj aplikację, dodając swój token do pliku `application.properties`:
    ```properties
    # src/main/resources/application.properties
    github.api.url=https://api.github.com
    github.api.token=your_personal_access_token_here
    ```
   Zastąp `your_personal_access_token_here` swoim faktycznym tokenem dostępu.

## Uruchomienie

### Maven
1. Zbuduj projekt:
    ```bash
    mvn clean install
    ```

2. Uruchom aplikację:
    ```bash
    mvn spring-boot:run
    ```

### Gradle
1. Zbuduj projekt:
    ```bash
    ./gradlew build
    ```

2. Uruchom aplikację:
    ```bash
    ./gradlew bootRun
    ```

## Użycie

### Endpointy
- `GET /api/github/repositories/{username}` - zwraca repozytoria użytkownika (wymagany nagłówek `Accept: application/json`).

Przykładowe zapytanie:
```bash
curl -H "Accept: application/json" http://localhost:8080/api/github/repositories/{username}
