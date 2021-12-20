package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFileType

class DatabaseBuilder {
    companion object {
        fun getDBFile(packageName: String, dbName: String, entityNames: List<String>) = TemplateFile(dbName, "databases", TemplateFileType.KOTLIN, """
        
            package $packageName.databases

            import androidx.room.Database
            import androidx.room.RoomDatabase
            import $packageName.models.database.Entity1
            import $packageName.models.database.Entity2

            @Database(
                entities = [Entity1::class, Entity2::class],
                version = 1
            )
            abstract class $dbName : RoomDatabase() {
                abstract fun entity1Dao(): Entity1Dao
                abstract fun entity2Dao(): Entity2Dao
            }

        """.trimIndent())

        fun getBaseDaoFile(packageName: String) = TemplateFile("BaseDao", "databases", TemplateFileType.KOTLIN, """
            package $packageName.databases
    
            import androidx.room.*
            
            @Dao
            interface BaseDao<T> {
                @Insert(onConflict = OnConflictStrategy.ABORT)
                suspend fun insert(entity: T)
            
                @Insert(onConflict = OnConflictStrategy.ABORT)
                suspend fun insert(vararg entity: T)
            
                @Insert(onConflict = OnConflictStrategy.ABORT)
                suspend fun insert(entities: List<T>)
            
                @Insert(onConflict = OnConflictStrategy.REPLACE)
                suspend fun replace(entity: T)
            
                @Insert(onConflict = OnConflictStrategy.REPLACE)
                suspend fun replace(vararg entity: T)
            
                @Insert(onConflict = OnConflictStrategy.REPLACE)
                suspend fun replace(entities: List<T>)
            
                @Update
                suspend fun update(entity: T)
            
                @Update
                suspend fun update(vararg entity: T)
            
                @Update
                suspend fun update(entities: List<T>)
            
                @Delete
                suspend fun delete(entity: T)
            
                @Delete
                suspend fun delete(vararg entity: T)
            
                @Delete
                suspend fun delete(entities: List<T>)
            }
        
        """.trimIndent())

        fun getDaoFile(packageName: String, entityName: String) = TemplateFile("${entityName}Dao", "databases", TemplateFileType.KOTLIN, """
            package $packageName.databases
    
            import androidx.lifecycle.LiveData
            import androidx.room.Dao
            import androidx.room.Query
            import androidx.room.Transaction
            import $packageName.models.database.$entityName
            import kotlinx.coroutines.flow.Flow
            
            @Dao
            abstract class ${entityName}Dao : BaseDao<$entityName> {
                @Query("SELECT * FROM $entityName")
                abstract suspend fun getAll(): List<$entityName>
            
                @Query("SELECT * FROM $entityName")
                abstract fun asLiveData(): LiveData<List<$entityName>>
                
                @Query("SELECT * FROM $entityName")
                abstract fun asLiveData(): Flow<List<$entityName>>
            
                @Query("SELECT * FROM $entityName WHERE id = :id")
                abstract suspend fun get(id: String) : $entityName?
                
                @Transaction
                open suspend fun replaceAll(entities: List<$entityName>) {
                    deleteAll()
                    insert(entities)
                }
                
                @Query("DELETE FROM $entityName WHERE id = :id")
                abstract suspend fun delete(id: String)
            
                @Query("DELETE FROM $entityName")
                abstract suspend fun deleteAll()
            }
        
        """.trimIndent())
    }


}
