package net.lrr.generadorrfc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform