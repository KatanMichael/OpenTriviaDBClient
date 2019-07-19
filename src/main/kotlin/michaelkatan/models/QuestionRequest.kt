package michaelkatan.models

data class QuestionRequest(
    val response_code: Int,
    val results: List<Question>)