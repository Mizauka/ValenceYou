# ARCHITECTURE.md — ValenceYou

## Overview

ValenceYou is a client-side harm-reduction application with no backend in Phase 1.

```
┌─────────────────────────────────────┐
│           Frontend (React)          │
│  ┌─────────┐  ┌─────────┐  ┌──────┐ │
│  │  State  │  │  Risk   │  │  HR  │ │
│  │  Puzzle │  │  Layer  │  │ Flow │ │
│  └─────────┘  └─────────┘  └──────┘ │
│  ┌─────────┐  ┌─────────┐  ┌──────┐ │
│  │  Pages  │  │  Store  │  │  UI  │ │
│  │ (State) │  │(Zustand)│  │ Kit  │ │
│  └─────────┘  └─────────┘  └──────┘ │
└─────────────────────────────────────┘
              │
              ▼
┌─────────────────────────────────────┐
│        Local Storage (IndexedDB)    │
│        - State history              │
│        - User preferences           │
│        - Cached content             │
└─────────────────────────────────────┘
```

---

## Frontend Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| Framework | Vite + Vue 3 + TypeScript | Fast iteration, familiar stack |
| Language | TypeScript | Type safety |
| Animation | Framer Motion / GSAP | High-fidelity interactions |
| State | Pinia | Lightweight, local-first |
| Styling | CSS Modules / Tailwind | Scoped, maintainable |
| Storage | IndexedDB (via idb) | Structured local data |

---

## Core Modules

### State Puzzle (`/src/puzzle/`)

- **Block definitions** — affect block taxonomy
- **Composition engine** — combine blocks → continuous profile
- **Renderer** — Framer Motion animated blocks
- **Interaction handlers** — drag, resize, combine

### State Pages (`/src/pages/`)

- **Template** — standardized 5-section layout
- **Content** — phenomenology, mechanisms, substances, risk, HR
- **Navigation** — experience-first IA

### Risk Layer (`/src/risk/`)

- **Signal detection** — pattern matching on state profiles
- **Severity grading** — implicit, not scored
- **Response routing** — harm reduction vs emergency

### Store (`/src/store/`)

- **Profile history** — temporal state snapshots
- **Preferences** — accessibility, language
- **Content cache** — offline availability

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
pnpm dev

# Production build
pnpm build

# Static hosting
# (Vercel, Netlify, GitHub Pages)
```

No server required for Phase 1–2.
