import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*


fun main() {
    embeddedServer(Netty, host = "localhost", port = 8081) {
        routing {
            get("/") {
                call.respondHtml {
                    head {
                        title { +"Ktor: Getting Started" }
                    }
                    body {
                        p {
                            +"Hello from Ktor Netty engine sample application"
                        }
                        form(
                            action = "/",
                            encType = FormEncType.multipartFormData,
                            method = FormMethod.post
                        ) {
                            h2 { +"Upload files" }
                            input(type = InputType.file, name = "files") {
                                multiple = true
                            }
                            input(type = InputType.submit)
                        }

                    }
                }
            }
            post("/") {
                val multipartData = call.receiveMultipart()
                multipartData.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        val filename = part.originalFileName!!
                        val fileBytes = part.streamProvider().readBytes()
                    }
                    part.dispose()
                }
                call.respondText("OK. File upload")

            }
        }
    }.start(wait = true)
}