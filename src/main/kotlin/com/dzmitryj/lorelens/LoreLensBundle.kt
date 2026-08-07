package com.dzmitryj.lorelens

import com.intellij.DynamicBundle
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

private const val BUNDLE = "messages.LoreLensBundle"

internal object LoreLensBundle {
    private val instance = DynamicBundle(LoreLensBundle::class.java, BUNDLE)

    @JvmStatic
    fun message(key: @PropertyKey(resourceBundle = BUNDLE) String, vararg params: Any?): String {
        return instance.getMessage(key, *params)
    }

    @JvmStatic
    fun lazyMessage(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any?): Supplier<String> {
        return instance.getLazyMessage(key, *params)
    }
}
