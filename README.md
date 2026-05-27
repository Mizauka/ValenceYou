# ValenceYou

A body-state–based harm-reduction system for Chinese trans communities.

> Not drug education. Not diagnosis.
>
> Objective: map subjective bodily experience → latent state space → risk + harm reduction.

---

## Core UX Model

Interaction starts from lived experience:

```
Experience → State Composition → Interpretation → Harm Reduction
```

No drug-first navigation.

---

## Core Thesis

Subjective bodily experience can be modeled as a compositional state space; harm reduction is achieved by restoring interpretability of this space.

---

## System Goals

| Goal | Description |
|------|-------------|
| G1 | De-mystify altered states |
| G2 | Reduce overdose / poly-drug risk |
| G3 | Restore bodily interpretability |
| G4 | Avoid pathological labeling |

---

## Tech Stack

**Phase 1 (MVP)**
- Compose Multiplatform (Kotlin)
- SQLDelight (SQLite)
- Local-first storage
- Skia rendering

**Phase 2 (Native)** — Only after UX stabilization
- CMP → Android / iOS / Desktop

---

## UI Development Policy

**Before prototype design is finalized by the project owner:**
- No UI implementation
- Focus on documentation, architecture, and content
- UI framework choice (Compose Multiplatform) is locked, but no components built yet

---

## Project Structure

```
project-root/
├── README.md
├── PLAN.md
├── REMEMBER.md
├── ROADMAP.md
├── TODO.md
├── API.md
├── ARCHITECTURE.md
├── TESTING.md
├── SECURITY.md
├── ONBOARDING.md
│
├── docs/
│   ├── concept/
│   ├── ux/
│   ├── lore/
│   ├── federation/
│   └── moderation/
│
├── frontend/
├── backend/
├── graph-engine/
├── infra/
├── scripts/
└── experiments/
```

---

## Setup

```bash
git clone https://github.com/Mizauka/ValenceYou.git
cd ValenceYou
```

## UI Development Policy

**Before prototype design is finalized by the project owner:**
- No UI implementation
- Focus on documentation, architecture, and content
- UI framework choice (Compose Multiplatform) is locked, but no components built yet

---

## License

TBD
