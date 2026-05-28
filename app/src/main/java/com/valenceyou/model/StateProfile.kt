package app.valenceyou.shared.model

/**
 * A persistent state profile that aggregates affect blocks over time.
 */
data class StateProfile(
    val id: String,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long,
    val baselineValence: Double = 0.0,
    val baselineArousal: Double = 0.5,
    val riskFlags: List<String> = emptyList()
)
