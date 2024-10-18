package app.junsu.junjanote

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
