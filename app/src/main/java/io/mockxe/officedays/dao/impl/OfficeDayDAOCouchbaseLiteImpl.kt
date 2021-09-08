package io.mockxe.officedays.dao.impl

import android.content.Context
import android.util.Log
import com.couchbase.lite.CouchbaseLite
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import com.couchbase.lite.MutableDocument
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import io.mockxe.officedays.dao.OfficeDayDAO
import io.mockxe.officedays.gson.LocalDateDeserializer
import io.mockxe.officedays.gson.LocalDateSerializer
import io.mockxe.officedays.model.OfficeDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class OfficeDayDAOCouchbaseLiteImpl @Inject constructor(@ApplicationContext context: Context): OfficeDayDAO {

    companion object {
        private const val TAG = "OfficeDayDAOCouchbaseLiteImpl"
        private const val COUCHBASE_OFFICE_DAY_DB = "office_day_db"
        private const val COUCHBASE_OFFICE_DAY_DOC = "office_day_doc"
    }

    private val databaseConfig: DatabaseConfiguration
    private val database: Database

    private val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
            .create()

    init {
        CouchbaseLite.init(context)
        databaseConfig = DatabaseConfiguration()
        database = Database(COUCHBASE_OFFICE_DAY_DB, databaseConfig)
    }

    override fun read(): Map<LocalDate, OfficeDay> {
        Log.d(TAG, "reading from couchbase...")
        val mutableDocument = database.getDocument(COUCHBASE_OFFICE_DAY_DOC)?.toMutable()
            ?: MutableDocument(COUCHBASE_OFFICE_DAY_DOC)

        return mutableDocument
            .toMap()
            .map { (key, value ) ->
                LocalDate.parse(key, DateTimeFormatter.ISO_LOCAL_DATE) to
                gson.fromJson(value as String, OfficeDay::class.java)
            }
            .onEach { Log.d(TAG, "read '${it.first}': '${it.second}'") }
            .toMap()

    }

    override fun save(officeDayMap: Map<LocalDate, OfficeDay>) {
        Log.d(TAG, "saving to couchbase...")
        val mutableDocument = MutableDocument(COUCHBASE_OFFICE_DAY_DOC)

        officeDayMap
            .map {
                val key: String = it.key.format(DateTimeFormatter.ISO_LOCAL_DATE)
                val value: String = gson.toJson(it.value)

                mutableDocument.setString(key, value)

                Log.d(TAG, "saved '$key': '$value'")
            }

        database.save(mutableDocument)

    }

}