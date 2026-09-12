@file:Suppress("SpellCheckingInspection")

import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import org.gradle.api.Project
import tools.jackson.databind.ObjectMapper
import tools.jackson.databind.node.ArrayNode
import tools.jackson.databind.node.ObjectNode
import java.io.File

data class GenerationContext(
    val input: File,
    val output: File
)

data class PlatformMeta(
    val input: String,
    val output: String,
    val generator: (GenerationContext) -> Unit
)

fun Project.generateFabricMetadata(ctx: GenerationContext) {
    ctx.output.parentFile.mkdirs()

    val mapper = ObjectMapper()
    val json = mapper.readTree(ctx.input.reader()) as ObjectNode
    json.let {
        it.put("id", mod.id)
        it.put("name", mod.name)
        it.put("version", mod.version)
        it.put("description", mod.description)
        it.put("license", mod.license)
        it.put("icon", "${mod.id}_logo.png")
        it.set("authors", mapper.valueToTree<ArrayNode>(mod.authors))
        it.set("contact", mapper.valueToTree<ObjectNode>(mod.contact))
        if (mod.contributors.isNotEmpty())
            it.set("contributors", mapper.valueToTree<ArrayNode>(mod.contributors))
    }
    ctx.output.writer().use {
        mapper.writerWithDefaultPrettyPrinter().writeValue(it, json)
    }
}

fun Project.generateNeoForgeMetadata(ctx: GenerationContext) {
    ctx.output.parentFile.mkdirs()

    val toml = Toml().read(ctx.input).toMap()
    toml.let { root ->
        root["license"] = mod.license
        val modmenu = root.getPath<MutableMap<String, Any>>("modproperties", "modmenu") ?: mutableMapOf()
        @Suppress("UNCHECKED_CAST")
        (root["mods"] as ArrayList<HashMap<String, Any>>)[0].let { mods ->
            mods["modId"] = mod.id
            mods["version"] = mod.version
            mods["displayName"] = mod.name
            mods["description"] = mod.description
            mods["iconFile"] = "${mod.id}_logo.png"
            mods["logoFile"] = "${mod.id}_logo.png" // 一些启动器依然使用 logoFile 获取图标
            mods["authors"] = mod.authors.joinToString(", ")
            mod.contact.forEach { (key, value) ->
                when (key) {
                    "homepage" -> mods["displayURL"] = value
                    "issues" -> root["issueTrackerURL"] = value
                    "sources" -> modmenu["sources"] = value
                }
            }
            (root["dependencies"] as HashMap<String, Any?>).let {
                it.remove("generated-at-build-time")?.let { v -> it[mod.id] = v }
            }
        }
        if (mod.contributors.isNotEmpty()) modmenu["contributors"] = mod.contributors
        if (modmenu.isNotEmpty()) {
            if (!root.containsKey("modproperties")) root["modproperties"] = mutableMapOf<String, Any>()
            @Suppress("UNCHECKED_CAST")
            (root["modproperties"] as HashMap<String, Any>)["modmenu"] = modmenu
        }
    }
    ctx.output.writer().use { writer ->
        TomlWriter().write(toml, writer)
    }
}

inline fun <reified T> Map<String, *>.getPath(vararg path: String): T? {
    var current: Any? = this
    for (key in path) current = (current as? Map<*, *>)?.get(key) ?: return null
    return current as? T
}