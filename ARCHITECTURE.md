# ARCHITECTURE.md — ValenceYou

## Overview

ValenceYou is a client-side harm-reduction application with no backend in Phase 1.

```
┌─────────────────────────────────────┐
│     Compose Multiplatform (Kotlin)  │
│  ┌─────────┐  ┌─────────┐  ┌──────┐ │
│  │  State  │  │  Risk   │  │  HR  │ │
│  │  Puzzle │  │  Layer  │  │ Flow │ │
│  └─────────┘  └─────────┘  └──────┘ │
│  ┌─────────┐  ┌─────────┐  ┌──────┐ │
│  │  Pages  │  │  Store  │  │  UI  │ │
│  │ (State) │  │(SQLDeli)│  │ Kit  │ │
│  └─────────┘  └─────────┘  └──────┘ │
└─────────────────────────────────────┘
              │
              ▼
┌─────────────────────────────────────┐
│        SQLite (SQLDelight)          │
│        - State history              │
│        - User preferences           │
│        - Cached content             │
└─────────────────────────────────────┘
```

---

## Frontend Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| Framework | Compose Multiplatform (Kotlin) | Cross-platform UI, single codebase |
| Language | Kotlin | Type safety, coroutines |
| Rendering | Skia | High-performance 2D graphics |
| State | SQLDelight + Kotlin Flow | Local-first, reactive |
| Storage | SQLite (SQLDelight) | Structured local data |
| Animation | Compose Animation API | Built-in, declarative |

---

## Core Modules

### State Puzzle (`/src/puzzle/`)

- **Block definitions** — affect block taxonomy
- **Composition engine** — combine blocks → continuous profile
- **Renderer** — Skia/Compose Canvas animated blocks
- **Interaction handlers** — drag, resize, combine (Compose gesture API)

### State Pages (`/src/pages/`)

- **Template** — standardized 5-section layout
- **Content** — phenomenology, mechanisms, substances, risk, HR
- **Navigation** — experience-first IA

### Risk Layer (`/src/risk/`)

- **Signal detection** — pattern matching on state profiles
- **Severity grading** — implicit, not scored
- **Response routing** — harm reduction vs emergency

### Store (`/src/store/`)

- **Profile history** — temporal state snapshots (SQLite)
- **Preferences** — accessibility, language (SQLite)
- **Content cache** — offline availability (SQLite)
- **Reactive updates** — Kotlin Flow

---

## Data Flow

```
User Interaction
      │
      ▼
┌─────────────┐
│ State Puzzle │ ──► Compositional Profile
└─────────────┘
      │
      ▼
┌─────────────┐
│  Risk Layer  │ ──► Signal Detection
└─────────────┘
      │
      ▼
┌─────────────┐
│  Response     │ ──► HR Content / Emergency
│  Router       │
└─────────────┘
      │
      ▼
┌─────────────┐
│   Store      │ ──► Persist to IndexedDB
└─────────────┘
```

---

## Future Backend (Phase 3+)

**Supabase** — sync only

- Anonymous profiles
- No identity system
- No social graph
- Optional opt-in logging

---

## Graph Engine (`/graph-engine/`)

Separate package for state space computation.

- Latent space operations
- HiTOP mapping functions
- Profile similarity / trajectory
- Exported as WASM or JS module

---

## Build & Deploy

```bash
# Development
./gradlew :composeApp:run

# Production build
./gradlew :composeApp:package

# Android
./gradlew :composeApp:assembleDebug

# iOS (via Kotlin/Native)
./gradlew :composeApp:iosSimulatorArm64Binaries
```

No server required for Phase 1–2.
