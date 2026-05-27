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

### Store API (SQLDelight + Kotlin Flow)

```kotlin
interface AppState {
    // Current session
    val currentProfile: StateFlow<StateProfile?>
    
    // History
    val profileHistory: StateFlow<List<StateProfile>>
    
    // Preferences
    val language: StateFlow<String>  // zh-CN, zh-TW, en
    val accessibility: StateFlow<AccessibilitySettings>
    
    // Content
    val cachedPages: StateFlow<Map<String, StatePage>>
}

// Actions (suspend functions)
suspend fun setProfile(profile: StateProfile)
suspend fun addToHistory(profile: StateProfile)
suspend fun clearHistory()
suspend fun setLanguage(lang: String)
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
