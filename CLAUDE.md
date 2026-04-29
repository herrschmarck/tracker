# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

Open the project in **Android Studio** (File → Open → select this directory). Android Studio will download the Gradle wrapper automatically on first sync.

- **Build:** Build → Make Project (`Ctrl+F9`)
- **Run:** Run → Run 'app' (`Shift+F10`) — requires an emulator or connected Android device (API 26+)
- **Install APK directly:** `./gradlew installDebug`

There are no tests yet. Add them under `app/src/test/` (unit) or `app/src/androidTest/` (instrumented).

## Architecture

Standard Android MVVM with three layers:

```
UI (Compose)  →  ViewModel  →  Repository  →  Room DAO  →  SQLite
```

- **`data/`** — Room entity (`Entry`), DAO (`EntryDao`), database singleton (`TrackerDatabase`), and repository (`TrackerRepository`). Activities are stored as a comma-separated string in `Entry.activities`.
- **`ui/TrackerViewModel`** — `AndroidViewModel` that exposes a single `StateFlow<List<Entry>>` and write operations. Uses `Application` context to build the database singleton.
- **`ui/screens/`** — Two screens: `LogScreen` (form to log energy/mood/activities) and `HistoryScreen` (reverse-chronological card list).
- **`TrackerApp`** — Root composable with a `Scaffold` + bottom `NavigationBar`. Tab state drives which screen is shown; no NavHost.
- **`MainActivity`** — Single activity, calls `enableEdgeToEdge()` then renders `TrackerApp` inside `TrackerTheme`.

## Key conventions

- minSdk 26 — `java.time.*` APIs are available without desugaring.
- KSP (not kapt) is used for Room annotation processing.
- `FlowRow` (from `androidx.compose.foundation.layout`) is used for activity chips; the `@OptIn(ExperimentalLayoutApi::class)` annotation is required.
- The database version is **1**. When adding columns, bump the version and provide a `Migration` in `TrackerDatabase`.
