package id.pahlevikun.cookingtimer.data.config.base.firebase

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.util.regex.Pattern

internal object FirebaseRemoteConfigHelper {
    private val TRUE_PATTERN: Pattern =
        Pattern.compile("^(1|true|t|yes|y|on)$", Pattern.CASE_INSENSITIVE)
    private val FALSE_PATTERN: Pattern =
        Pattern.compile("^(0|false|f|no|n|off|)$", Pattern.CASE_INSENSITIVE)

    fun asBoolean(text: String): Boolean {
        return if (text.isEmpty()) {
            false
        } else {
            when {
                TRUE_PATTERN.matcher(text.trim()).matches() -> true
                FALSE_PATTERN.matcher(text.trim()).matches() -> false
                else -> false
            }
        }
    }

    fun isBoolean(text: String): Boolean {
        return if (text.isEmpty()) {
            false
        } else {
            TRUE_PATTERN.matcher(text.trim()).matches() || FALSE_PATTERN.matcher(text.trim())
                .matches()
        }
    }

    fun asMapCollection(value: String): HashMap<String, Any>? {
        val type = object : TypeToken<HashMap<String, Any>>() {}.type

        return if (value.isEmpty()) {
            return null
        } else {
            try {
                Gson().fromJson(value, type) as? HashMap<String, Any> ?: hashMapOf()
            } catch (e: JSONException) {
                return null
            } catch (e: JsonSyntaxException) {
                return null
            }
        }
    }
}