package com.mburakcakir.taketicket.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mburakcakir.taketicket.data.db.dao.EventDao
import com.mburakcakir.taketicket.data.db.dao.TicketDao
import com.mburakcakir.taketicket.data.db.dao.UserDao
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.db.entity.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [UserModel::class, EventModel::class, TicketModel::class],
    version = 4,
    exportSchema = false
)
abstract class TicketDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun ticketDao(): TicketDao

    private class TicketDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch {
                populateDatabase()
            }
        }

        suspend fun populateDatabase() {
            val eventDao = INSTANCE!!.eventDao()
            var eventModel = EventModel(
                2235,
                "Ahududu",
                "Harbiye Cemil Topuzlu Açıkhava Sahnesi ",
                "Tiyatro",
                240,
                "https://grpstat.com/DealImages/G-owctuutu/12782__5_600-318.jpg",
                "14 Ekim",
                "₺48"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2236,
                "Cihangir Kafika'dan size özel gösterim!",
                "Kaçırdığınız filmler kafesi, Kafika!",
                "Sinema",
                180,
                "https://grpstat.com/DealImages/K-wwrnxwoh/KAFIKA-KAPAK2082__3_600-318.jpg",
                "12.00 - 22.00",
                "₺70"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2237,
                "Mustafa Keser sahnesi",
                "Ataköy Marina Nossa Costa'da limitsiz yerli içecek ve fiks menü / Mustafa Keser sahnesi için geçerlidir.",
                "Müzik",
                300,
                "https://grpstat.com/DealImages/K-q13ydz2e/18524_600-318.jpg",
                "23 Ekim",
                "₺399"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2238,
                "Notre Dame’ın Kamburu",
                "Dünyaca ünlü \"Notre Dame’ın Kamburu\" isimli müzikal, izleyicisi ile buluşuyor!",
                "Müzikal",
                150,
                "https://grpstat.com/DealImages/K-p2fmaj3v/notre5663_600-318.jpg",
                "21 Ekim",
                "₺63"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2239,
                "Forest Kemerburgaz'a giriş biletleri",
                "Türkiye'nin en büyük açık alanına sahip macera & etkinlik parkı Forest Kemerburgaz sizi ve sevdiklerinizi bekliyor.",
                "Eğlence Merkezi",
                20,
                "https://grpstat.com/DealImages/G-g4y0jr53/17780__5_600-318.jpg",
                "10.00 - 20.00",
                "₺33"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2240,
                "Ahmet Aslan konseri",
                "Anadolu müziğini batı enstrümanlarıyla birlikte kullanan Ahmet Aslan, müzikseverlerle buluşuyor.",
                "Müzik",
                100,
                "https://grpstat.com/DealImages/K-hldymgnv/konserr6248_600-318.jpg",
                "1 Kasım",
                "₺56"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2241,
                "Müfit Can Saçıntı Güldürüsü",
                "Müfit Can Saçıntı'nın sahneledeği tek kişilik müzikli gösteri ''İtiraz Ediyorum\" seyircisiyle buluşuyor!",
                "Tiyatro",
                50,
                "https://grpstat.com/DealImages/K-vq05rmrp/gosteriii5709_600-318.jpg",
                "4 Aralık",
                "₺49"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2242,
                "Son Senfoni",
                "Kaan Erkam’ın kaleminden çıkan Levent Tayman’ın yönettiği, yağmurlu ve soğuk bir gecede opera tezi yazmaya çalışan adamın yaşadıkları.",
                "Tiyatro",
                85,
                "https://grpstat.com/DealImages/G-i4bb1xjp/15024_600-318.jpg",
                "6 - 13 Aralık",
                "₺20"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2243,
                "Emaar Akvaryum & Sualtı Hayvanat Bahçesi",
                "Okyanusların büyüleyici dünyasına dalışa geçin.",
                "Akvaryum",
                10,
                "https://grpstat.com/DealImages/G-q3l05po5/akvaryumgrsll6606__5_600-318.jpg",
                "12.00 - 18.00",
                "₺48"
            )

            eventDao.insertEvent(eventModel)

            eventModel = EventModel(
                2244,
                "Cihangir Kafika'dan size özel gösterim!",
                "İster evden DVD getirebilir ister mekanın geniş arşivinden seçim yapabilirsiniz.",
                "Sinema",
                2,
                "https://grpstat.com/DealImages/K-wwrnxwoh/KAFIKA-KAPAK2082__2_600-318.jpg",
                "18.00 - 20.00",
                "₺70"
            )

            eventDao.insertEvent(eventModel)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TicketDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TicketDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TicketDatabase::class.java,
                    "ticket_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(TicketDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

