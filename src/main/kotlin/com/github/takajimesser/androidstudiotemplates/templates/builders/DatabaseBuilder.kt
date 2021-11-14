package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.templates.*

class DatabaseBuilder {
    companion object {
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
    
            import androidx.room.*
            
            @Dao
            abstract class ${entityName}Dao : BaseDao<$entityName> {
                @Query("SELECT * FROM $entityName")
                abstract suspend fun getAll(): List<$entityName>
            
                @Query("SELECT * FROM $entityName")
                abstract fun liveData(): LiveData<List<$entityName>>
            
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
