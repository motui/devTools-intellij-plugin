package cn.motui.devtools

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.CollectionBean
import java.util.*

/**
 * 应用数据
 */
@State(name = "AppStorage", storages = [(Storage(STORAGE_NAME))])
class AppStorage : PersistentStateComponent<AppStorage> {
    @CollectionBean
    private val favorites: MutableList<String> = ArrayList(DEFAULT_FAVORITE_SIZE)

    fun add(componentId: String) {
        favorites.add(componentId)
    }

    override fun getState(): AppStorage = this

    override fun loadState(state: AppStorage) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        private const val DEFAULT_FAVORITE_SIZE = 5

        /**
         * Get the instance of this service.
         *
         * @return the unique [AppStorage] instance.
         */
        val instance: AppStorage
            get() = ServiceManager.getService(AppStorage::class.java)
    }

}
