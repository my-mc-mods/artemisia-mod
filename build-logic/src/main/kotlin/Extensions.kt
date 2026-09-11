import com.moandjiezana.toml.Toml
import org.gradle.api.Project

val Project.mod: ModInfo
    get() {
        val extra = extensions.extraProperties
        if (!extra.has("cachedModInfo"))
            extra.set("cachedModInfo", ModInfo(this))
        return extra.get("cachedModInfo") as ModInfo
    }

class ModInfo(project: Project) {
    private val tomlFile = project.rootProject.file("mod.toml")
    private val toml = Toml().read(tomlFile)

    val id = toml.getString("id")!!
    val group = toml.getString("group")!!
    val version = toml.getString("version")!!
    val name = toml.getString("name")!!
    val license = toml.getString("license")!!
    val authors = toml.getList<String>("authors")!!
    val contributors = toml.getList<String>("contributors")!!
    val description = toml.getString("description").trim()
    val contact: Map<String, String> = mapOf(
        "homepage" to toml.getString("homepage"),
        "sources" to toml.getString("sources"),
        "issues" to toml.getString("issues")
    )
}