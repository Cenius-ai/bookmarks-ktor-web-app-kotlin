package templates

import kotlinx.html.*

object AddFormTemplate {

    fun render(div: DIV, errorMessage: String? = null) {
        with(div) {
            h2 { +"Add a Bookmark" }

            div("form-card") {
                if (errorMessage != null) {
                    div(classes = "empty-state") {
                        p {
                            style = "color:oklch(0.45 0.15 25);"
                            +errorMessage
                        }
                    }
                }

                form(action = "/add", method = FormMethod.post) {
                    div("form-group") {
                        label { +"Title" }
                        input(type = InputType.text, name = "title") {
                            required = true
                            placeholder = "e.g. Ktor Documentation"
                            maxLength = "256"
                        }
                    }
                    div("form-group") {
                        label { +"URL" }
                        input(type = InputType.url, name = "url") {
                            required = true
                            placeholder = "https://ktor.io/docs"
                            maxLength = "2048"
                        }
                    }
                    div("form-group") {
                        label { +"Tags (comma-separated)" }
                        input(type = InputType.text, name = "tags") {
                            placeholder = "kotlin, framework, documentation"
                            maxLength = "1024"
                        }
                    }
                    div("form-actions") {
                        button(type = ButtonType.submit, classes = "btn btn-primary") {
                            +"Save Bookmark"
                        }
                        a("/", classes = "btn") {
                            +"Cancel"
                        }
                    }
                }
            }
        }
    }
}
