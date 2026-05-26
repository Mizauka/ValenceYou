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
- Vite + React + TypeScript
- Framer Motion
- Zustand
- Local-first storage

**Phase 2 (Native)** — Only after UX stabilization
- Jetpack Compose (Android)
- SwiftUI (iOS)

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

## Quick Start

```bash
# Clone
git clone https://github.com/Mizauka/ValenceYou.git
cd ValenceYou

# Install dependencies
pnpm install

# Start dev server
pnpm dev
```

---

## License

TBD
