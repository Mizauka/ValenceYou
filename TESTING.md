# TESTING.md — ValenceYou

## Philosophy

Testing serves **safety** and **validity**, not coverage metrics.

---

## Test Categories

### 1. UX Validity Tests

| Test | Method | Pass Criteria |
|------|--------|---------------|
| Time-to-self-recognition | User testing | ≤ 30 seconds |
| State comprehension | Interview | User describes state in own words |
| Navigation clarity | Task-based | Find harm reduction from experience entry |

### 2. Safety Tests

| Test | Method | Pass Criteria |
|------|--------|---------------|
| No overdose romanticization | Content audit | Zero euphoria framing in risk content |
| No imitation induction | User testing | No user reports "want to try" after use |
| No escalation bias | Longitudinal | No increased polydrug patterns |

### 3. Functional Tests

- State puzzle composition accuracy
- Risk signal detection correctness
- Emergency routing logic
- Offline functionality
- Accessibility (screen reader)

---

## Test Environments

1. **Local dev** — unit + integration
2. **Staging** — UX testing with community members
3. **Controlled release** — small cohort, monitored

---

## Safety Review Protocol

Every release requires:

- [ ] Content audit by harm-reduction advisor
- [ ] UX validity test with 3+ community members
- [ ] Safety checklist (see REMEMBER.md)
- [ ] No-blocking-issues sign-off

---

## Metrics We Do NOT Track

- Time on app
- Session frequency
- "Engagement" scores
- Social sharing rates

## Metrics We DO Track

- Help-seeking behavior (self-reported)
- State recognition accuracy
- Unsafe repetition reduction (self-reported)
- Emergency guidance usage
