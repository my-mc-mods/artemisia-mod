import net.fabricmc.loom.util.ModPlatform
import org.gradle.api.Project

val Project.mod get() = ModInfo(this)

@Suppress("unused")
class ModInfo(private val project: Project) {
    val id: String = "artemisia"
    val group: String = "dev.aika.artemisia"
    val version: String = "0.1.0"
    val name: String = "Artemisia"
    val authors: List<String> = listOf(
        "Gizmo"
    )
    val contributors: List<String> = listOf()
    var license: String = "EPL-2.0" // https://spdx.org/licenses/
    val description: String = """
        Artemisia is a cross-platform modding library providing shared APIs and utilities.
    """.trimIndent()
    val contact: Map<String, String> = mapOf(
        "homepage" to "https://github.com/gizmo-ds/artemisia-mod",
        "sources" to "https://github.com/gizmo-ds/artemisia-mod",
        "issues" to "https://github.com/gizmo-ds/artemisia-mod/issues"
    )
    val platforms: List<String> = listOf(
        ModPlatform.NEOFORGE, ModPlatform.FABRIC
    ).map { it.id() }
    val javaVersion: Int = 25
}