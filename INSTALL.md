# Installation

## 1. Prerequisites

- **JDK 21** – verify with `java -version`. The build will download Gradle automatically via the wrapper.

## 2. Get the Code

Clone or download the project source code. Then navigate into the project root.

## 3. Install Dependencies and Compile

```bash
./gradlew build -x test
```

This command resolves all dependencies, compiles the Kotlin sources, and creates the application distribution (skipping tests).

## 4. Environment Variables

The server reads the `PORT` environment variable to determine the listening port (default `8080`). To customize it:

```bash
export PORT=9000
```

A sample file `.env.example` is provided. You may copy it to `.env` and adjust settings for your environment.

## 5. Database Setup

No manual database setup is required. The application uses an embedded SQLite database stored in the file `bookmarks.db`. The database and its tables are created automatically on first startup. Seed data (realistic example bookmarks) is loaded from `src/main/kotlin/db/SeedData.kt`.

## 6. Run the Application

Start the development server with:

```bash
./gradlew run
```

The server will bind to `0.0.0.0` on the configured port (default `8080`). Open `http://localhost:8080` in your browser.

## 7. Running Tests

Execute the test suite:

```bash
./gradlew test
```

## 8. Production Build

Build the distribution archive:

```bash
./gradlew build
```

The full build includes compilation, testing, and assembly. The application distribution (tar/zip) can be found in `build/distributions/`.

## 9. Troubleshooting

- **Wrong JDK version** – Ensure the active JDK is version 21. Run `java -version` to confirm. The build script is configured with `jvmToolchain(21)` and will attempt to auto-download it if needed.
- **Port conflict** – If port `8080` is already in use, set the `PORT` environment variable to another value (e.g., `8081`) before starting the app.
- **Build failures** – Try cleaning the project first: `./gradlew clean` followed by `./gradlew build -x test`.