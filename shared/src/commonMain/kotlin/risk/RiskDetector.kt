package app.valenceyou.shared.risk

import app.valenceyou.shared.model.AffectBlock
import app.valenceyou.shared.model.StateProfile

/**
 * Detects risk flags from a stream of affect blocks and a state profile.
 */
class RiskDetector {

    data class RiskReport(
        val profileId: String,
        val flags: List<String>,
        val severity: Severity
    ) {
        enum class Severity { NONE, LOW, MEDIUM, HIGH, CRITICAL }
    }

    fun analyze(profile: StateProfile, recentBlocks: List<AffectBlock>): RiskReport {
        val flags = mutableListOf<String>()
        var severity = RiskReport.Severity.NONE

        if (recentBlocks.isEmpty()) {
            return RiskReport(profile.id, flags, severity)
        }

        val avgValence = recentBlocks.map { it.valence }.average()
        val avgArousal = recentBlocks.map { it.arousal }.average()
        val minValence = recentBlocks.minOf { it.valence }

        // Sustained low valence
        if (avgValence < -0.5) {
            flags.add("SUSTAINED_LOW_VALENCE")
            severity = maxOf(severity, RiskReport.Severity.HIGH)
        } else if (avgValence < -0.2) {
            flags.add("ELEVATED_NEGATIVE_VALENCE")
            severity = maxOf(severity, RiskReport.Severity.MEDIUM)
        }

        // Extreme arousal
        if (avgArousal > 0.85) {
            flags.add("EXTREME_AROUSAL")
            severity = maxOf(severity, RiskReport.Severity.HIGH)
        } else if (avgArousal < 0.15) {
            flags.add("FLAT_AROUSAL")
            severity = maxOf(severity, RiskReport.Severity.LOW)
        }

        // Critical dip
        if (minValence < -0.8) {
            flags.add("CRITICAL_VALENCE_DIP")
            severity = RiskReport.Severity.CRITICAL
        }

        return RiskReport(profile.id, flags, severity)
    }
}
