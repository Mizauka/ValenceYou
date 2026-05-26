# PLAN.md — ValenceYou

## Project Definition

A body-state–based harm-reduction system for Chinese trans communities.

Not drug education. Not diagnosis.

**Objective:** map subjective bodily experience → latent state space → risk + harm reduction.

---

## Interaction Model (HiTOP-based)

### Latent State Space

```
Detachment ↔ Engagement
Numbness ↔ Affective intensity
Disinhibition ↔ Control
```

### Compositional "Affect Blocks"

User constructs state via blocks:

```
{color, size} → {valence, intensity}
```

Output is a **continuous profile**, not a label.

---

## Core UI Primitive: State Puzzle

```
[ blocks = subjective sensations ]
[ size = intensity ]
[ position = interaction of states ]
```

### Example Blocks

- numbness
- overdrive
- dissociation
- attachment
- panic
- emptiness

---

## Information Architecture

```
Home (Experience Entry)
 ├─ Experience States
 ├─ Latent Systems (HiTOP mapping)
 ├─ Substances (mechanistic view only)
 ├─ Harm Reduction
 └─ Emergency Guidance
```

### Priority Ordering

**Experience > Risk > Mechanism > Substance**

---

## Page Template

Each state page contains:

1. **Phenomenology** — what it feels like
2. **Possible mechanisms** — neuro/physiology
3. **Associated substances** — non-instructional
4. **Risk signals**
5. **Immediate harm reduction**

### Constraints

- ≤ 3 min read
- No dosage content
- No procedural guidance
- No romanticization

---

## UX Constraints

### Hard Constraints

- No tutorialization
- No euphoria framing
- No "transcendence" narrative
- No optimization of drug use

### Soft Constraints

- Low cognitive load
- Non-technical language
- Non-judgmental tone

---

## Backend Strategy

**Phase 1:** No backend. Local-first storage only.

**Optional later:**
- Supabase (sync only)
- No identity system
- No social graph

---

## Evaluation Protocol

### UX Validity

- time-to-self-recognition ≤ 30s

### Safety

- No overdose romanticization
- No imitation induction
- No escalation bias

### Utility

- Improved state recognition
- Reduced unsafe repetition
- Increased help-seeking likelihood

---

## Implementation Phases

| Phase | Name | Deliverables |
|-------|------|-------------|
| 0 | Spec Lock | Define state space + UX rules. **No UI code before prototype design approved.** |
| 1 | MVP Prototype | 5–8 state pages, state puzzle UI (after design approval), risk + harm reduction layer |
| 2 | Structure Expansion | State taxonomy refinement, mapping stability tests, content scaling |
| 3 | Controlled Expansion | Optional anonymous logs, longitudinal state tracking, multi-language support |
| 4 | Native Migration | UX stable + safety validated + retention stable |

---

## Current Phase

**Phase 0 — Spec Lock**
