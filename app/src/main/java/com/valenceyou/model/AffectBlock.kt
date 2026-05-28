package app.valenceyou.shared.model

/**
 * A single affect block capturing a momentary emotional / physiological state.
 */
data class AffectBlock(
    val id: String,
    val timestamp: Long,
    val valence: Double,      // -1.0 .. 1.0
    val arousal: Double,      // 0.0 .. 1.0
    val label: String? = null,
    val source: String? = null
)
