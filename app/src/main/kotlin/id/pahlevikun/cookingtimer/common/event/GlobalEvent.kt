package id.pahlevikun.cookingtimer.common.event

interface GlobalEvent {

    fun getName(): String

    fun getAttributes(): Map<String, String>
}