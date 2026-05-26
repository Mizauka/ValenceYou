# API.md — ValenceYou

## Status

**Phase 1:** No external API. All data is static or local.

This document defines internal APIs and future external contracts.

---

## Internal APIs

### State Puzzle API

```typescript
interface AffectBlock {
  id: string;
  name: string;           // e.g., "numbness"
  valence: number;        // -1 to 1
  intensity: number;      // 0 to 1
  color: string;          // hex
  category: BlockCategory;
}

interface StateProfile {
  blocks: AffectBlock[];
  timestamp: number;
  // Continuous composition, not categorical
}

// Operations
compose(blocks: AffectBlock[]): StateProfile;
decompose(profile: StateProfile): AffectBlock[];
similarity(a: StateProfile, b: StateProfile): number;
```

---

### Risk Signal API

```typescript
interface RiskSignal {
  type: 'polydrug' | 'overdose' | 'distress' | 'isolation';
  severity: 'caution' | 'urgent' | 'emergency';
  triggers: string[];     // block IDs that triggered
  response: ResponseType;
}

interface ResponseType {
  kind: 'harm_reduction' | 'emergency_guidance' | 'peer_support';
  content: string;
  actions: Action[];
}

// Detection
detect(profile: StateProfile): RiskSignal[];
```

---

### Store API (Zustand)

```typescript
interface AppState {
  // Current session
  currentProfile: StateProfile | null;
  
  // History
  profileHistory: StateProfile[];
  
  // Preferences
  language: 'zh-CN' | 'zh-TW' | 'en';
  accessibility: AccessibilitySettings;
  
  // Content
  cachedPages: Record<string, StatePage>;
}

// Actions
setProfile(profile: StateProfile): void;
addToHistory(profile: StateProfile): void;
clearHistory(): void;
setLanguage(lang: string): void;
```

---

## Future External APIs (Phase 3+)

### Anonymous Log Sync

```
POST /api/v1/logs
Body: { profile: StateProfile, metadata: LogMeta }
Response: { id: string, status: 'accepted' }
```

### Content Updates

```
GET /api/v1/content/pages
Response: StatePage[]

GET /api/v1/content/blocks
Response: AffectBlock[]
```

### Federation (Phase 4)

```
POST /api/v1/federation/share
Body: { profile: StateProfile, recipient: string }
```

---

## No Identity

All APIs are anonymous. No auth tokens. No user IDs.

If sync is implemented, use device-generated UUIDs with no linkage to identity.
