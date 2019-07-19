package michaelkatan.models

interface RequestListener
{
    fun <T> onComplete (t : T)
    fun onError(message : String)
}