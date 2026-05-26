# SECURITY.md — ValenceYou

## Threat Model

### Primary Concerns

1. **User identity exposure** — Trans communities in China face unique risks
2. **Data seizure** — Local device access by third parties
3. **Content misuse** — Harm reduction info weaponized
4. **Federation abuse** — Bad actors in peer network

---

## Mitigations

### Identity

- No account system
- No email/phone collection
- No social graph
- Device-only data by default

### Data Storage

```
Local-only (Phase 1–2)
  └── IndexedDB, no encryption needed (device boundary)

Optional Sync (Phase 3+)
  └── Supabase with anonymous UUID
  └── End-to-end encryption for logs
```

### Content Safety

- Static content, no user-generated content in Phase 1–2
- Moderation tools in Phase 3+ federation
- Clear reporting mechanisms

### Emergency

- No automatic emergency service contact
- User-initiated only
- Clear consent for any external communication

---

## Incident Response

If user data is compromised:

1. Assess scope (local vs sync)
2. Notify affected users if sync involved
3. Document and review architecture
4. No law enforcement contact without explicit user consent

---

## Responsible Disclosure

Security issues:
- Email: security@valenceyou.org (when established)
- PGP key: TBD
- Response time: 72 hours acknowledgment

---

## No Backdoors

No law enforcement or government access mechanisms.
No remote content injection without signed updates.
