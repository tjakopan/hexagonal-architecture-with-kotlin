package framework.adapters.output.h2.data

import org.eclipse.persistence.mappings.DatabaseMapping
import org.eclipse.persistence.mappings.converters.Converter
import org.eclipse.persistence.sessions.Session
import java.sql.Types
import java.util.*

class UuidConverter : Converter {
  override fun convertObjectValueToDataValue(objectValue: Any?, session: Session): UUID? = objectValue as UUID?

  override fun convertDataValueToObjectValue(dataValue: Any?, session: Session): UUID? = dataValue as UUID?

  override fun initialize(mapping: DatabaseMapping, session: Session) {
    val field = mapping.field
    field.sqlType = Types.OTHER
    field.typeName = UUID::class.java.canonicalName
    field.columnDefinition = "UUID"
  }

  override fun isMutable(): Boolean = false
}